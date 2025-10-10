package net.cyvfabric.command.mpk;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.gui.GuiLb;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class CommandMm extends CyvCommand {
    public CommandMm() {
        super("mm");
        aliases.add("momentumblock");
        aliases.add("momentum");
        this.helpString = "Open GUi for modifying momentum block settings.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        if (ParkourTickListener.momentumBlock != null) GuiHandler.setScreen(new GuiLb(ParkourTickListener.momentumBlock));
        else {
            CyvFabric.sendChatMessage("You must set a momentum block to use this command.");
        }
    }
}