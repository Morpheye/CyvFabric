package net.cyvfabric.command.mpk;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class CommandClearpb extends CyvCommand {
    public CommandClearpb() {
        super("clearpb");
        this.helpString = "Clear the landing block's existing offsets.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        if (ParkourTickListener.landingBlock != null) {
            ParkourTickListener.landingBlock.pb = null;
            ParkourTickListener.landingBlock.pbX = null;
            ParkourTickListener.landingBlock.pbZ = null;
        }
        CyvFabric.sendChatMessage("Successfully cleared landing offsets.");
    }
}