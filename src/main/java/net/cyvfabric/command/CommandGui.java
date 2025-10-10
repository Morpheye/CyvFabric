package net.cyvfabric.command;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.gui.GuiMPK;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class CommandGui extends CyvCommand {
    public CommandGui() {
        super("gui");
        this.helpString = "Open the MPK gui.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        GuiHandler.setScreen(new GuiMPK());
    }
}