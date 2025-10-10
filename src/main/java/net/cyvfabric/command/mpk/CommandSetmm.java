package net.cyvfabric.command.mpk;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.util.CyvCommand;
import net.cyvfabric.util.parkour.LandingAxis;
import net.cyvfabric.util.parkour.LandingBlock;
import net.cyvfabric.util.parkour.LandingMode;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class CommandSetmm extends CyvCommand {
    public CommandSetmm() {
        super("setmm");
        hasArgs = true;
        usage = "[arguments]";
        this.helpString = "Set momentum block";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        run(args);
    }

    public static void run(String[] args) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity player = mc.player;

        new Thread(() -> {
            LandingMode mode = LandingMode.landing;
            LandingAxis axis = LandingAxis.both;
            boolean box = false;
            boolean target = false;
            for (String s : args) {
                s = s.toLowerCase();
                if (s.equals("x")) axis = LandingAxis.x;
                else if (s.equals("z")) axis = LandingAxis.z;
                else if (s.equals("land") || s.equals("landing")) mode = LandingMode.landing;
                else if (s.equals("hit")) mode = LandingMode.hit;
                else if (s.equals("zneo") || s.equals("z-neo") || s.equals("neo") || s.equals("z_neo")) mode = LandingMode.z_neo;
                else if (s.equals("enter")) mode = LandingMode.enter;
                else if (s.equals("box")) box = true;
                else if (s.equals("target")) target = true;
            }

            if (target) {
                HitResult hit = player.raycast(100, 0, false);
                if (hit.getType().equals(HitResult.Type.BLOCK)) {
                    try {
                        BlockPos pos = ((BlockHitResult) hit).getBlockPos();

                        if (mc.world.getBlockState(pos).getCollisionShape(mc.world, pos).isEmpty()) {
                            CyvFabric.sendChatMessage("Please look at a valid block.");
                            return;
                        } else {
                            ParkourTickListener.momentumBlock = new LandingBlock(pos, mode, axis, box);
                            CyvFabric.sendChatMessage("Successfully set momentum block.");
                        }
                    } catch (Exception e) {
                        CyvFabric.sendChatMessage("Please look at a valid block.");
                    }
                } else {
                    CyvFabric.sendChatMessage("Please look at a valid block.");
                    return;
                }
            }
            else {
                if (player.isOnGround()) {
                    int yLevel = player.getBlockY();

                    BlockPos pos = new BlockPos(player.getBlockX(),
                            (int) (yLevel > 0 ? Math.floor(yLevel-0.001) : Math.floor(yLevel-0.001)), player.getBlockZ());

                    if (mc.world.getBlockState(pos).getCollisionShape(mc.world, pos).isEmpty()) pos = pos.down();

                    if (mc.world.getBlockState(pos).getCollisionShape(mc.world, pos).isEmpty()) {
                        CyvFabric.sendChatMessage("Please stand on a valid block.");
                        return;
                    } else {
                        ParkourTickListener.momentumBlock = new LandingBlock(pos, mode, axis, box);
                        CyvFabric.sendChatMessage("Successfully set momentum block.");
                    }

                } else {
                    CyvFabric.sendChatMessage("Please stand on a valid block.");
                    return;
                }
            }
        }, "Set landing block").start();
    }
}