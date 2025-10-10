package net.cyvfabric.command.mpk;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.util.CyvCommand;
import net.cyvfabric.util.parkour.LandingBlock;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Box;

public class CommandSetbox extends CyvCommand {
    public CommandSetbox() {
        super("setbox");
        this.helpString = "Creates landing zone with set dimensions.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        try {
            Box player = MinecraftClient.getInstance().player.getBoundingBox();

            double x1 = Double.parseDouble(args[0]) + player.getLengthX()/2;
            double x2 = Double.parseDouble(args[1]) - player.getLengthX()/2;
            double y1 = Double.parseDouble(args[2]);
            double y2 = Double.parseDouble(args[3]);
            double z1 = Double.parseDouble(args[4]) + player.getLengthZ()/2;
            double z2 = Double.parseDouble(args[5]) - player.getLengthZ()/2;
            Box box = new Box(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2),
                    Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));

            if (ParkourTickListener.landingBlock != null) {
                ParkourTickListener.landingBlock.bb = new Box[]{box};
                CyvFabric.sendChatMessage("Landing box changed.");

            } else {


                ParkourTickListener.landingBlock = new LandingBlock(box);
                CyvFabric.sendChatMessage("Landing box set.");

            }
        } catch (Exception e) {
            CyvFabric.sendChatMessage("Invalid setbox syntax.");
        }
    }
}