package net.cyvfabric.command.mpk;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class CommandSetcond extends CyvCommand {
    public CommandSetcond() {
        super("setcond");
        this.helpString = "Set landing block detection zone.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        try {
            double x1 = Double.parseDouble(args[0]);
            double x2 = Double.parseDouble(args[1]);
            double z1 = Double.parseDouble(args[2]);
            double z2 = Double.parseDouble(args[3]);

            if (ParkourTickListener.landingBlock != null) {
                ParkourTickListener.landingBlock.adjustCond(x1, x2, z1, z2);
                CyvFabric.sendChatMessage("Successfully set landing conditions.");
            } else {
                CyvFabric.sendChatMessage("No target block exists.");
            }

        } catch (Exception e) {
            CyvFabric.sendChatMessage("Please enter valid coordinates.");
        }
    }
}