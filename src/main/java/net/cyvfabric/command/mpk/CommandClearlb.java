package net.cyvfabric.command.mpk;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class CommandClearlb extends CyvCommand {
    public CommandClearlb() {
        super("clearlb");
        this.helpString = "Clear the landing block.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        if (ParkourTickListener.landingBlock != null) ParkourTickListener.landingBlock = null;
        CyvFabric.sendChatMessage("Successfully cleared landing block.");
    }
}