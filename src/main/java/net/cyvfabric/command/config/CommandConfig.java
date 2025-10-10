package net.cyvfabric.command.config;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.gui.GuiModConfig;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class CommandConfig extends CyvCommand {
    public CommandConfig() {
        super("config");
        this.helpString = "Opens the mod config GUI.";

        aliases.add("configuration");
        aliases.add("settings");
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        GuiHandler.setScreen(new GuiModConfig());
    }
}
