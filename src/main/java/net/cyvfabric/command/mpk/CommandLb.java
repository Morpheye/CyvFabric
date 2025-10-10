package net.cyvfabric.command.mpk;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.gui.GuiLb;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class CommandLb extends CyvCommand {
    public CommandLb() {
        super("lb");
        aliases.add("landingblock");
        aliases.add("landing");
        this.helpString = "Open GUi for modifying landing block settings.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        if (ParkourTickListener.landingBlock != null) GuiHandler.setScreen(new GuiLb(ParkourTickListener.landingBlock));
        else {
            CyvFabric.sendChatMessage("You must set a landing block to use this command.");
        }
    }
}