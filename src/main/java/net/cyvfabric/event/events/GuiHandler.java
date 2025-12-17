package net.cyvfabric.event.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

//This class is used to handle GUIs for CyvFabric
public class GuiHandler {
    private static Screen screenAwaiting; //screen which will be shown next tick

    public static void setScreen(Screen screen) {
        screenAwaiting = screen;
    }

    public static void register() { //register the eventListener
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (Minecraft.getInstance().level == null) return; //don't run unless in-game
            if (screenAwaiting != null) {
                Minecraft.getInstance().setScreen(screenAwaiting); //set the screen
                screenAwaiting = null; //now that no screen is awaiting, clear it
                return;
            }

        });
    }

}
