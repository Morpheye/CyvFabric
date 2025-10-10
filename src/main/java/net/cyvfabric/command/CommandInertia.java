package net.cyvfabric.command;

import com.mojang.brigadier.context.CommandContext;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class CommandInertia extends CyvCommand {
    public CommandInertia() {
        super("inertia");
        this.hasArgs = true;
        this.usage = "[subcategory]";
        this.helpString = "Configure inertia listener. Use /cyv help inertia for more info.";
    }

    @Override
    public void run(CommandContext<FabricClientCommandSource> context, String[] args) {
        boolean enabled = CyvClientConfig.getBoolean("inertiaEnabled", false);
        String subcategory = "toggle";

        //add a checker for no args to not toggle on/off inertia

        if (args.length != 0) {
            subcategory = args[0].toString().toLowerCase();
        }

        if (subcategory.equals("toggle")) {
            if (enabled) {
                CyvClientConfig.set("inertiaEnabled", false);
                CyvFabric.sendChatMessage("Inertia listener toggled off");
            } else {
                CyvClientConfig.set("inertiaEnabled", true);
                CyvFabric.sendChatMessage("Inertia listener toggled on");
            }

        } else if (subcategory.equals("min") || subcategory.equals("minimum")) {
            try {
                CyvClientConfig.set("inertiaMin", Double.parseDouble(args[1]));
                CyvFabric.sendChatMessage("Inertia minimum speed threshold set to " + args[1] + ".");
            } catch (Exception e) {
                CyvFabric.sendChatMessage("Invalid minimum speed.");
            }

        } else if (subcategory.equals("max") || subcategory.equals("maximum")) {
            try {
                CyvClientConfig.set("inertiaMax", Double.parseDouble(args[1]));
                CyvFabric.sendChatMessage("Inertia maximum speed threshold set to " + args[1] + ".");
            } catch (Exception e) {
                CyvFabric.sendChatMessage("Invalid maximum speed.");
            }

        } else if (subcategory.equals("mode") || subcategory.equals("axis")) {
            if (args[1].toLowerCase().equals("x")) {
                CyvClientConfig.set("inertiaAxis", 'x');
                CyvFabric.sendChatMessage("Inertia axis set to x.");
            } else if (args[1].toLowerCase().equals("z")) {
                CyvClientConfig.set("inertiaAxis", 'z');
                CyvFabric.sendChatMessage("Inertia axis set to z.");
            } else {
                CyvFabric.sendChatMessage("Invalid axis. Only x and z are allowed.");
            }

        } else if (subcategory.equals("tick") || subcategory.equals("t")) {
            try {
                if (Integer.parseInt(args[1]) > 12 || Integer.parseInt(args[1]) < 1) {
                    CyvFabric.sendChatMessage("Inertia tick must be from 1 to 12.");
                } else {
                    CyvClientConfig.set("inertiaTick", Integer.parseInt(args[1]));
                    CyvFabric.sendChatMessage("Inertia tick set to " + args[1] + ".");
                }
            } catch (Exception e) {
                CyvFabric.sendChatMessage("Invalid tick.");
            }

        } else if (subcategory.equals("ground") || subcategory.equals("groundmode") || subcategory.equals("groundtype")) {
            if (args[1].toLowerCase().equals("normal")) {
                CyvClientConfig.set("inertiaGroundType", "normal");
                CyvFabric.sendChatMessage("Ground type set to normal.");
            } else if (args[1].toLowerCase().equals("ice")) {
                CyvClientConfig.set("inertiaGroundType", "ice");
                CyvFabric.sendChatMessage("Ground type set to ice.");
            } else if (args[1].toLowerCase().equals("slime")) {
                CyvClientConfig.set("inertiaGroundType", "slime");
                CyvFabric.sendChatMessage("Ground type set to slime.");
            } else {
                CyvFabric.sendChatMessage("Invalid ground type. Only normal, ice, and slime are allowed.");
            }

        } else {
            CyvFabric.sendChatMessage("Unrecognized argument.");
        }
    }

    @Override
    public String getDetailedHelp() {
        return "Toggle on/off or modify inertia listener settings. Subcategories:"
                + "\n[toggle]: Toggle listener on or off."
                + "\n[min/max]: Set minimum and maximum speed bounds for a chat message."
                + "\n[mode]: Set listener to x or z axis."
                + "\n[tick]: Set airtick to check for inertia."
                + "\n[ground]: Change ground type between ice, slime, or normal.";
    }

}
