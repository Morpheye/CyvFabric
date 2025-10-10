package net.cyvfabric.command.mpk;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class CommandClearmm extends CyvCommand {
    public CommandClearmm() {
        super("clearmm");
        this.helpString = "Clear the momentum block.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        if (ParkourTickListener.momentumBlock != null) ParkourTickListener.momentumBlock = null;
        CyvFabric.sendChatMessage("Successfully cleared momentum block.");
    }
}