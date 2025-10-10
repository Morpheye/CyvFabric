package net.cyvfabric.event.events;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.util.parkour.LandingBlock;
import net.cyvfabric.util.parkour.LandingBlockOffset;
import net.cyvfabric.util.parkour.LandingMode;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;

import java.text.DecimalFormat;

public class ParkourTickListener {
    public static int airtime = 0;
    public static PosTick lastTick = new PosTick(0, 0, 0, 0, new boolean[] {false, false, false, false, false, false, false});
    public static PosTick secondLastTick = new PosTick(0, 0, 0, 0, new boolean[] {false, false, false, false, false, false, false});
    public static PosTick thirdLastTick = new PosTick(0, 0, 0, 0, new boolean[] {false, false, false, false, false, false, false});

    public static int lastAirtime;
    public static double x = 0, y = 0, z = 0; //coords
    public static double vx = 0, vy = 0, vz = 0; //velocities

    public static float f = 0, p = 0; //yaw and pitch
    public static float vf = 0, vp = 0; //last turnings

    public static double lx = 0, ly = 0, lz = 0; //landings
    public static double hx = 0, hy = 0, hz = 0; //hits
    public static double jx = 0, jy = 0, jz = 0; //jump
    public static float hf = 0; //hit facing
    public static double hvx = 0, hvz = 0; //hit velocities

    public static float jf = 0, jp = 0; //jump angles
    public static float sf = 0, sp = 0; //second turn angles
    public static float pf = 0, pp = 0; //preturn angles

    //inertia
    public static double stored_v = 0;
    public static float stored_slip = 1;

    //landing block & other labels
    public static LandingBlock landingBlock = null;
    public static LandingBlock momentumBlock = null;

    public static String lastTiming = "";
    public static int blips = 0;
    public static double lastBlipHeight = 0;

    public static int grinds = 0;
    private static boolean grindStarted = false;

    public static float last45 = 0;
    public static double lastTurning = 0;

    public static int sidestep = 0; //0 = wad 1 = wdwa
    public static int sidestepTime = -1;

    //Timings
    private static int lastJumpTime = -1;
    private static int lastGroundMoveTime = -1;
    private static int lastMoveTime = -1;
    private static int lastSprintTime = -1;
    private static int lastSneakTime = -2;

    private static long earliestMoveTimestamp;
    private static boolean locked = false;
    private static boolean hasActed = false;
    private static boolean hasCollided = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(ParkourTickListener::onTick);
    }

    //end of tick
    private static void onTick(MinecraftClient mc) {
        ClientPlayerEntity mcPlayer = mc.player;
        GameOptions gameSettings = mc.options;

        if (mcPlayer == null) return;

        if (lastTick.hasCollidedHorizontally && !hasCollided) {
            hasCollided = true;
        }

        if (mc.world == null || mc.isPaused()) return;

        calculateLastTiming();

        if (lastTick == null) {
        } else {
            if ((!lastTick.onGround || !mcPlayer.isOnGround()) && !mcPlayer.getAbilities().flying) airtime++;

            x = mcPlayer.getX();
            y = mcPlayer.getY();
            z = mcPlayer.getZ();
            f = mcPlayer.getYaw(); //note: actual yaw and pitch are delayed by a tick
            p = mcPlayer.getPitch();

            vx = x - lastTick.x;
            vy = y - lastTick.y;
            vz = z - lastTick.z;
            vf = f - lastTick.f;
            vp = p - lastTick.p;

            checkInertia();

        }

        if (airtime == 1) { //jump tick
            if (mcPlayer.getVelocity().y > 0 && vy >= 0) {
                jx = x;
                jy = y;
                jz = z;

                jf = f;
                jp = f;

                if (lastTick != null && secondLastTick != null) {
                    pf = lastTick.f - secondLastTick.f;
                    pp = lastTick.p - secondLastTick.p;
                }

                //grinds
                if (y == lastTick.y && vy == 0) {
                    if (!grindStarted) {
                        grindStarted = true;
                        grinds = 0;
                    }
                    grinds++;

                } else {
                    if (!grindStarted) grinds = 0;
                }

                //blips
                if (lastTick.onGround && !secondLastTick.onGround && lastTick.vy == 0 && (lastTick.y % 0.015625 != 0) && lastTick.airtime > 1) {
                    blips++;

                    lastBlipHeight = lastTick.y;

                } else {
                    blips = 0;
                }
            }

        } else if (airtime == 2 && lastTick.vy > 0) {
            sf = f;
            sp = p;
        }

        //last 45
        if (lastTick.keys[0] && ((lastTick.keys[1] && lastTick.keys[3]) || (!lastTick.keys[1] && !lastTick.keys[3]))
                && mcPlayer.input.movementSideways != 0 && mcPlayer.input.movementForward != 0 && !mcPlayer.isOnGround()) {
            last45 = f - lastTick.f;
        }

        //last turning
        if (f != lastTick.f) lastTurning = f - lastTick.f;

        //hit tick
        if (lastTick != null && mcPlayer.isOnGround() && !lastTick.onGround && vy < 0) {
            lx = lastTick.x;
            ly = lastTick.y;
            lz = lastTick.z;

            hx = x;
            hy = y;
            hz = z;
            hf = f;
            hvx = vx;
            hvz = vz;

        }

        if (landingBlock != null) { //must be lower than the landing to check it
            LandingBlockOffset.refreshPb();

            for (int i=0; i<landingBlock.bb.length; i++) {
                if ((landingBlock.mode.equals(LandingMode.enter) && y <= landingBlock.bb[i].maxY && (y > landingBlock.bb[i].minY)) ||
                        !landingBlock.mode.equals(LandingMode.enter) && y <= landingBlock.bb[i].maxY && (lastTick.y > landingBlock.bb[i].maxY)) {
                    //check the previous ticks

                    if (vy < 0 && airtime > 1) {
                        if (landingBlock.mode.equals(LandingMode.hit) || landingBlock.mode.equals(LandingMode.enter)) {
                            LandingBlockOffset.check(x, y, z, lastTick.x,
                                    lastTick.y, lastTick.z, landingBlock, i);
                        } else {
                            LandingBlockOffset.check(lastTick.x, lastTick.y, lastTick.z, secondLastTick.x,
                                    secondLastTick.y, secondLastTick.z, landingBlock, i);
                        }
                    }
                }
            }

            LandingBlockOffset.finalizePb(landingBlock);

        }

        if (momentumBlock != null) { //must be lower than the landing to check it
            LandingBlockOffset.refreshPb();

            for (int i=0; i<momentumBlock.bb.length; i++) {
                if ((momentumBlock.mode.equals(LandingMode.enter) && y <= momentumBlock.bb[i].maxY && (y > momentumBlock.bb[i].minY)) ||
                        !momentumBlock.mode.equals(LandingMode.enter) && y <= momentumBlock.bb[i].maxY && (lastTick.y > momentumBlock.bb[i].maxY)) {
                    //check the previous ticks

                    if (vy < 0 && airtime > 1) {
                        if (momentumBlock.mode.equals(LandingMode.hit) || momentumBlock.mode.equals(LandingMode.enter)) {
                            LandingBlockOffset.check(x, y, z, lastTick.x,
                                    lastTick.y, lastTick.z, momentumBlock, i);
                        } else {
                            LandingBlockOffset.check(lastTick.x, lastTick.y, lastTick.z, secondLastTick.x,
                                    secondLastTick.y, secondLastTick.z, momentumBlock, i);
                        }
                    }
                }
            }

            LandingBlockOffset.finalizePb(momentumBlock);
        }

        boolean[] keys = new boolean[] {mc.options.forwardKey.isPressed(), mc.options.leftKey.isPressed(),
                mc.options.backKey.isPressed(), mc.options.rightKey.isPressed(),
                mc.options.jumpKey.isPressed(), mc.options.sprintKey.isPressed(),
                mc.options.sneakKey.isPressed()};

        thirdLastTick = secondLastTick;
        secondLastTick = lastTick;
        lastTick = new PosTick(mcPlayer, vx, vy, vz, airtime, keys);
        lastTick.true_vx = mcPlayer.getVelocity().getX();
        lastTick.true_vy = mcPlayer.getVelocity().getY();
        lastTick.true_vz = mcPlayer.getVelocity().getZ();
        lastTick.hasCollidedHorizontally = mcPlayer.horizontalCollision;
        if (lastTick.onGround) {
            if (airtime != 0) lastAirtime = airtime;
            airtime = 0;
        }
        else lastAirtime = airtime;

    }

    private static void checkInertia() {
        if (!CyvClientConfig.getBoolean("inertiaEnabled", false)) return;
        int inertiaTick = CyvClientConfig.getInt("inertiaTick", 4);
        char inertiaAxis = CyvClientConfig.getChar("inertiaAxis", 'x');
        String inertiaGroundType = CyvClientConfig.getString("inertiaGroundType", "normal");
        double inertiaMin = CyvClientConfig.getDouble("inertiaMin", -0.02);
        double inertiaMax = CyvClientConfig.getDouble("inertiaMax", 0.02);

        //check inertia
        if (airtime == inertiaTick) {
            if (inertiaAxis == 'x') stored_v=vx; else stored_v=vz;
            if (airtime > 1) stored_slip = 1f;
            else if (inertiaGroundType.equals("ice")) stored_slip = 0.98f;
            else if (inertiaGroundType.equals("slime")) stored_slip = 0.8f;
            else stored_slip = 0.6f;

        } else if (airtime == inertiaTick+1) {

            int tick = inertiaTick;
            double min = inertiaMin;
            double max = inertiaMax;

            int d = Integer.valueOf(CyvFabric.config.configFields.get("df").value.toString());
            DecimalFormat df = new DecimalFormat("#");
            df.setMaximumFractionDigits(d);

            if ((stored_v>=min && stored_v<=max) || (stored_v<=min && stored_v>=max)) {

                if (Math.abs(stored_v)*0.91F*stored_slip < 0.003) {
                    CyvFabric.sendChatMessage("Hit inertia at tick " + (airtime-1) + ", previous v = " + df.format(stored_v));
                } else {
                    CyvFabric.sendChatMessage("Missed inertia at tick " + (airtime-1) + ", previous v = " + df.format(stored_v));
                }

            }

        }//end checking inertia
    }

    private static void calculateLastTiming() {
        boolean showMS = /*ModManager.getMod(ModMPKMod.class).showMilliseconds;*/false;
        GameOptions gameSettings = MinecraftClient.getInstance().options;

        if (gameSettings.forwardKey.isPressed() || //ANYTHING IS PRESSED
                gameSettings.backKey.isPressed() ||
                gameSettings.leftKey.isPressed() ||
                gameSettings.rightKey.isPressed()) {
            lastMoveTime++;
            lastGroundMoveTime++;
            hasActed = true;

            /*
            if (lastMoveTime == 0) {
                earliestMoveTimestamp = 0;
                if (gameSettings.keyBindForward.isKeyDown()) earliestMoveTimestamp = gameSettings.keyBindForward.lastPressTime;
                if (gameSettings.keyBindBack.isKeyDown() && (gameSettings.keyBindBack.lastPressTime > earliestMoveTimestamp)) earliestMoveTimestamp = gameSettings.keyBindBack.lastPressTime;
                if (gameSettings.keyBindLeft.isKeyDown() && (gameSettings.keyBindLeft.lastPressTime > earliestMoveTimestamp)) earliestMoveTimestamp = gameSettings.keyBindLeft.lastPressTime;
                if (gameSettings.keyBindRight.isKeyDown() && (gameSettings.keyBindRight.lastPressTime > earliestMoveTimestamp)) earliestMoveTimestamp = gameSettings.keyBindRight.lastPressTime;

            }
            
             */

            //already jumped, started moving
            if (lastJumpTime > -1 && lastMoveTime == 0 && airtime != 0 && !(vy == 0 && lastTick.onGround)
                    && (lastTiming.contains("Pessi") || !locked)) {
                if ((lastJumpTime+1) == 1) lastTiming = "Max Pessi";
                else lastTiming = "Pessi " + (lastJumpTime+1) + " ticks";
                locked = true;

                /*
                if (showMS && Math.abs((earliestMoveTimestamp - gameSettings.keyBindJump.lastPressTime) / 1000000) < 10000)
                    lastTiming += " (" + ((gameSettings.keyBindJump.lastPressTime - earliestMoveTimestamp) / 1000000) + " ms)";
                */
            }

            if (lastTick.onGround && !secondLastTick.onGround) { //landed
                lastGroundMoveTime = 0;
            }

        } else { //nothing is pressed
            lastMoveTime = -1;
            lastGroundMoveTime = -1;
        }

        //jumping
        if (gameSettings.jumpKey.isPressed() && airtime == 0) {
            lastJumpTime = 0;
            hasActed = true;

            //already jumped, started moving
            if ((lastGroundMoveTime == 0 || lastMoveTime == 0) && !locked) {
                lastTiming = "Jam";
                /*
                if (((gameSettings.keyBindJump.lastPressTime - earliestMoveTimestamp) / 1000000) != 0 && showMS) {
                    lastTiming += " (" + ((gameSettings.keyBindJump.lastPressTime - earliestMoveTimestamp) / 1000000) + " ms)";
                }
                */
                if (gameSettings.sprintKey.isPressed() || !gameSettings.forwardKey.isPressed()) {
                    locked = true;
                }
                //already moved on ground
            } else if (lastGroundMoveTime > -1 && !locked && lastJumpTime == 0) {
                if (lastSneakTime == -1) lastTiming = "Burst " + (lastGroundMoveTime) + " ticks";
                else if (lastSneakTime > -1) lastTiming = "Burstjam " + (lastGroundMoveTime) + " ticks";
                else lastTiming = "HH " + (lastGroundMoveTime) + " ticks";

                /*
                if (showMS && Math.abs((gameSettings.keyBindJump.lastPressTime - earliestMoveTimestamp) / 1000000) < 10000)
                    lastTiming += " (" + ((gameSettings.keyBindJump.lastPressTime - earliestMoveTimestamp) / 1000000) + " ms)";
                */
                locked = true;
            }

            //midair after jumping
        } else if (!lastTick.onGround && lastJumpTime > -1) {
            lastJumpTime++;
            //not midair not jumping
        } else {
            lastJumpTime = -1;
        }

        //sneaking
        if (gameSettings.sneakKey.isPressed()) {
            if (lastSneakTime == -2) lastSneakTime = 0;
            else lastSneakTime++;
        }
        else {
            if (lastSneakTime == -1 || lastSneakTime == -2) lastSneakTime = -2;
            else lastSneakTime = -1;
        }

        if ((gameSettings.sprintKey.isPressed() || lastSprintTime != -1)
                && !lastTick.onGround ) {
            lastSprintTime++;
            if (lastTiming.startsWith("Jam") && lastSprintTime == 0 && !locked && lastTick.keys[0]) {
                if (lastJumpTime < 1) {
                } else {
                    if (lastJumpTime == 1) lastTiming = "Max FMM";
                    else lastTiming = "FMM " + (lastJumpTime) + " ticks";
                    /*
                    if (showMS && Math.abs((gameSettings.keyBindSprint.lastPressTime - gameSettings.keyBindJump.lastPressTime) / 1000000) < 10000)
                        lastTiming += " (" + ((gameSettings.keyBindSprint.lastPressTime - gameSettings.keyBindJump.lastPressTime) / 1000000) + " ms)";
                    */
                    locked = true;
                }
            }

        } else {
            lastSprintTime = -1;
        }

        //reset
        if (!(gameSettings.forwardKey.isPressed() || //ANYTHING IS PRESSED
                gameSettings.backKey.isPressed() ||
                gameSettings.leftKey.isPressed() ||
                gameSettings.rightKey.isPressed() ||
                gameSettings.jumpKey.isPressed()) &&
                MinecraftClient.getInstance().player.isOnGround()) {
            resetLastTiming();
        }

        //sidestep
        if (gameSettings.jumpKey.isPressed() && airtime == 0) {
            if (((lastTick.strafe() != 0) && MinecraftClient.getInstance().player.input.movementSideways == 0)) {
                sidestepTime = 1;
                sidestep = 0;
            } else if (MinecraftClient.getInstance().player.input.movementSideways != 0) {
                sidestepTime = 1;
                sidestep = 1;
            } else {
                sidestep = -1;
                sidestepTime = 0;
            }

        } else if (airtime > 0) {
            if (sidestep == -1 && MinecraftClient.getInstance().player.input.movementSideways != 0) {
                sidestep = 0;
                sidestepTime = airtime;
            }

            if (sidestepTime == airtime && MinecraftClient.getInstance().player.input.movementSideways == 0.0f
                    && sidestep == 0) {
                sidestepTime++;
            }

        }

        //overflow prevention
        if (lastJumpTime > 999) lastJumpTime = 999;
        if (lastGroundMoveTime > 999) lastGroundMoveTime = 999;
        if (lastMoveTime > 999) lastMoveTime = 999;
        if (lastSprintTime > 999) lastSprintTime = 999;
    }

    public static void resetLastTiming() {
        locked = false;
        hasActed = false;
        grindStarted = false;
        hasCollided = false;
    }

    public static class PosTick {

        public PosTick(ClientPlayerEntity player, double vx, double vy, double vz, int airtime, boolean[] keys) {

            this.x = player.getX();
            this.y = player.getY();
            this.z = player.getZ();
            this.f = player.getYaw();
            this.p = player.getPitch();
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
            this.onGround = player.isOnGround();
            this.airtime = airtime;
            this.keys = keys;
        }

        public PosTick(double x, double y, double z, int airtime, boolean[] keys) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.f = 0;
            this.p = 0;
            this.vx = 0;
            this.vy = 0;
            this.vz = 0;
            this.onGround = true;
            this.airtime = airtime;
            this.keys = keys;
        }

        public PosTick(double x, double y, double z, float yaw, float pitch, double motionX, double motionY,
                       double motionZ, boolean onGround, int airtime, boolean[] keys) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.f = yaw;
            this.p = pitch;
            this.vx = motionX;
            this.vy = motionY;
            this.vz = motionZ;
            this.onGround = onGround;
            this.airtime = airtime;
            this.keys = keys;
        }


        public boolean[] keys;
        public double x;
        public double y;
        public double z;

        public float f;
        public float p;

        public double vx;
        public double vy;
        public double vz;

        public double true_vx, true_vy, true_vz;

        public boolean onGround;
        public int airtime;

        boolean hasCollidedHorizontally;

        int strafe() {
            int i = 0;
            if (keys[1] == true) i--;
            if (keys[3] == true) i++;
            return i;
        }

        int forward() {
            int i = 0;
            if (keys[0] == true) i++;
            if (keys[3] == true) i--;
            return i;
        }

    }

    public static float formatYaw(float yaw) {
        float facing = yaw % 360;
        if (facing > 180) facing -= 360;
        else if (facing < -180) facing += 360;
        return facing;
    }

    /*
    public void onRender(WorldRenderEvent e) {
        if (e.phase != Phase.MID) return;

        Entity p = Minecraft.getMinecraft().getRenderViewEntity();
        double pX = p.lastTickPosX + (e.renderTickTime * (p.posX - p.lastTickPosX));
        double pY = p.lastTickPosY + (e.renderTickTime * (p.posY - p.lastTickPosY));
        double pZ = p.lastTickPosZ + (e.renderTickTime * (p.posZ - p.lastTickPosZ));

        GL11.glLineWidth(2);
        GlStateManager.disableDepth();

        if (CyvClientConfig.getBoolean("highlightLandingCond", false)) {
            if (landingBlock != null) {
                AxisAlignedBB bb = new AxisAlignedBB(landingBlock.xMinCond - pX + 0.3, landingBlock.smallestY() - pY,
                        landingBlock.zMinCond - pZ + 0.3, landingBlock.xMaxCond - pX - 0.3, landingBlock.largestY() - pY,
                        landingBlock.zMaxCond - pZ - 0.3);

                RenderUtils.drawFilledBox(bb.expand(0.001, 0.001, 0.001), 0, 192, 255, 25);
                RenderUtils.drawBoxOutline(bb.expand(0.001, 0.001, 0.001), 0, 192, 255, 75);
            }

            if (momentumBlock != null) {
                AxisAlignedBB bb = new AxisAlignedBB(momentumBlock.xMinCond - pX + 0.3, momentumBlock.smallestY() - pY,
                        momentumBlock.zMinCond - pZ + 0.3, momentumBlock.xMaxCond - pX - 0.3, momentumBlock.largestY() - pY,
                        momentumBlock.zMaxCond - pZ - 0.3);

                RenderUtils.drawFilledBox(bb.expand(0.001, 0.001, 0.001), 255, 0, 0, 25);
                RenderUtils.drawBoxOutline(bb.expand(0.001, 0.001, 0.001), 255, 0, 0, 75);
            }
        }

        if (CyvClientConfig.getBoolean("highlightLanding", false)) {
            if (landingBlock != null) {
                for (AxisAlignedBB bb : landingBlock.AABB) {
                    AxisAlignedBB renderBB = bb.offset(-pX, -pY, -pZ).contract(0.3, 0, 0.3);
                    RenderUtils.drawFilledBox(renderBB.expand(0.001, 0.001, 0.001), 0, 192, 255, 100);
                    RenderUtils.drawBoxOutline(renderBB.expand(0.001, 0.001, 0.001), 0, 192, 255, 200);
                }
            }

            if (momentumBlock != null) {
                for (AxisAlignedBB bb : momentumBlock.AABB) {
                    AxisAlignedBB renderBB = bb.offset(-pX, -pY, -pZ).contract(0.3, 0, 0.3);
                    RenderUtils.drawFilledBox(renderBB.expand(0.001, 0.001, 0.001), 255, 0, 0, 100);
                    RenderUtils.drawBoxOutline(renderBB.expand(0.001, 0.001, 0.001), 255, 0, 0, 200);
                }
            }
        }

        GlStateManager.enableDepth();

    }
     */
}
