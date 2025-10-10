package net.cyvfabric.util.parkour;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.events.ParkourTickListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Box;

import java.text.DecimalFormat;

public class LandingBlockOffset {
    private static Double currentPb;
    private static Double currentXOffset;
    private static Double currentZOffset;
    private static boolean sendChatOffset = true;

    public static void refreshPb() { //called at the start of each tick before checks happen
        currentPb = null;
        currentXOffset = null;
        currentZOffset = null;
        sendChatOffset = false;
    }

    public static void check(double x, double y, double z, double lastx, double lasty, double lastz, LandingBlock b, int i) {
        Double xOffset = null, zOffset = null;
        Double eventX = null, eventZ = null;

        if (y < b.bb[i].minY) return; //below bottom y
        else if (y < b.bb[i].maxY && b.mode.equals(LandingMode.enter)) { //inside block on y
            xOffset = checkX(x, b, i);
            zOffset = checkZ(z, b, i);
            eventX = x;
            eventZ = z;
        } else if (y <= b.bb[i].maxY && b.mode.equals(LandingMode.hit) && lasty >= b.bb[i].maxY) { //hit tick
            xOffset = checkX(x, b, i);
            zOffset = checkZ(z, b, i);
            eventX = x;
            eventZ = z;
        } else if (y >= b.bb[i].maxY && (b.mode.equals(LandingMode.z_neo) || b.mode.equals(LandingMode.landing))) {
            xOffset = checkX(x, b, i);
            eventX = x;
            if (b.mode.equals(LandingMode.z_neo)) {
                zOffset = checkZ(lastz, b, i);
                eventZ = lastz;

                if (b.neoAndNormal) {
                    double temp = checkZ(z, b, i);
                    zOffset = temp < zOffset ? temp : zOffset;
                    eventZ = z;
                }

            } else {
                zOffset = checkZ(z, b, i);
                eventZ = z;
            }

        }

        //conditions
        if (eventX == null || eventZ == null || !(eventX <= b.xMaxCond && eventX >= b.xMinCond &&
                eventZ <= b.zMaxCond && eventZ >= b.zMinCond)) return;

        //finalize x and z offset
        Box playerBB = MinecraftClient.getInstance().player.getBoundingBox();

        double pb = calculatePb(xOffset, zOffset, b);
        if (currentPb == null) {
            currentPb = pb;
            currentXOffset = xOffset;
            currentZOffset = zOffset;
            if (((CyvClientConfig.getBoolean("sendLbChatOffset", false) && b == ParkourTickListener.landingBlock) ||
                    (CyvClientConfig.getBoolean("sendMmChatOffset", false) && b == ParkourTickListener.momentumBlock ))
                    && (eventX <= b.xMaxCond + playerBB.getLengthX()/2 && eventX >= b.xMinCond - playerBB.getLengthX()/2 &&
                    eventZ <= b.zMaxCond + playerBB.getLengthZ()/2 && eventZ >= b.zMinCond - playerBB.getLengthZ()/2)) {
                sendChatOffset = true;
            } else sendChatOffset = false;
        } else if (pb > currentPb) {
            currentPb = pb;
            currentXOffset = xOffset;
            currentZOffset = zOffset;
            if (((CyvClientConfig.getBoolean("sendLbChatOffset", false) && b == ParkourTickListener.landingBlock) ||
                    (CyvClientConfig.getBoolean("sendMmChatOffset", false) && b == ParkourTickListener.momentumBlock ))
                    && (eventX <= b.xMaxCond + playerBB.getLengthX()/2 && eventX >= b.xMinCond - playerBB.getLengthX()/2 &&
                    eventZ <= b.zMaxCond + playerBB.getLengthZ()/2 && eventZ >= b.zMinCond - playerBB.getLengthZ()/2)) {
                sendChatOffset = true;
            } else sendChatOffset = false;
        }

    }

    public static Double checkX(double x, LandingBlock b, int i) {
        double halfPlayerSize = MinecraftClient.getInstance().player.getBoundingBox().getLengthX()/2;

        double rightWallOffset = (b.xMinWall == null) ? 0 : (b.bb[i].minX - b.xMinWall) - halfPlayerSize*2;
        double leftWallOffset = (b.xMaxWall == null) ? 0 : (b.xMaxWall - b.bb[i].maxX) - halfPlayerSize*2;
        if (rightWallOffset > 0) rightWallOffset = 0;
        if (leftWallOffset > 0) leftWallOffset = 0;

        double left = (b.bb[i].maxX + leftWallOffset) - (x) + halfPlayerSize;
        double right = (x) - (b.bb[i].minX - rightWallOffset) + halfPlayerSize;

        if (left > Math.abs(right)) return right;
        if (right > Math.abs(left)) return left;

        return Math.max(left, right);
    }

    public static Double checkZ(double z, LandingBlock b, int i) {
        double halfPlayerSize = MinecraftClient.getInstance().player.getBoundingBox().getLengthZ()/2;

        //as max wall gets closer to block, frontwall decreases towards 0
        double backWallOffset = (b.zMinWall == null) ? 0 : (b.bb[i].minZ - b.zMinWall) - halfPlayerSize*2;
        double frontWallOffset = (b.zMaxWall == null) ? 0 : (b.zMaxWall - b.bb[i].maxZ) - halfPlayerSize*2;
        if (backWallOffset > 0) backWallOffset = 0;
        if (frontWallOffset > 0) frontWallOffset = 0;

        double front = (b.bb[i].maxZ + frontWallOffset) - (z) + halfPlayerSize;
        double back = (z) - (b.bb[i].minZ - backWallOffset) + halfPlayerSize;

        if (front > Math.abs(back)) return back;
        if (back > Math.abs(front)) return front;

        return Math.max(front, back);
    }

    public static Double calculatePb(Double x, Double z, LandingBlock b) {
        if (x == null) return z;
        else if (z == null) return x;
        else if (b.axis.equals(LandingAxis.x)) return x;
        else if (b.axis.equals(LandingAxis.z)) return z;
        else if (x < 0 && z < 0) return -Math.hypot(x, z);
        else if (x < 0) return x;
        else if (z < 0) return z;
        else return Math.hypot(x, z);
    }

    public static void finalizePb(LandingBlock b) {
        if (currentPb == null) return;
        Double tempLastPb = b.lastPb;

        boolean momentumCancel = CyvClientConfig.getBoolean("momentumPbCancelling", false) &&
                (ParkourTickListener.momentumBlock != null && ParkourTickListener.momentumBlock.lastPb != null
                        && ParkourTickListener.momentumBlock.lastPb < 0);

        b.lastPb = currentPb;
        if (b.lastPb != null) {
            if (currentXOffset != null) {
                b.lastOffsetX = currentXOffset;
            }
            if (currentZOffset != null) {
                b.lastOffsetZ = currentZOffset;
            }

            /*
            if (currentPb.doubleValue() >= 0 && !momentumCancel) {
                if (!(tempLastPb != null && tempLastPb > 0
                        && b.targetBlock instanceof BlockSlime
                        && System.currentTimeMillis() - b.lastLandingTime <
                        ModManager.getMod(ModAntiCP.class).delay*1000)) { //[CyvClient] fix slime issue
                    b.lastLandingTime = System.currentTimeMillis();
                    b.lastLandingHotbarSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;

                }
                b.sentMessage = false;
            }

             */

            if ((b.pb == null || b.lastPb > b.pb) && b == ParkourTickListener.landingBlock && !momentumCancel) { //must be landing block
                b.pb = b.lastPb;
                b.pbX = currentXOffset;
                b.pbZ = currentZOffset;
                DecimalFormat df = CyvFabric.df;
                CyvFabric.sendChatMessage("New pb! " + df.format(b.pb.doubleValue()));

            }
        }

        if (sendChatOffset && !momentumCancel) {
            if (currentXOffset != null && b.axis != LandingAxis.z)
                CyvFabric.sendChatMessage("X Offset: " + CyvFabric.df.format(currentXOffset.doubleValue()));
            if (currentZOffset != null && b.axis != LandingAxis.x)
                CyvFabric.sendChatMessage("Z Offset: " + CyvFabric.df.format(currentZOffset.doubleValue()));
        }

    }

}
