package net.cyvfabric.event.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

//This class is used to handle GUIs for CyvFabric
public class GuiHandler {
    private static Screen screenAwaiting; //screen which will be shown next tick

    public static void setScreen(Screen screen) {
        screenAwaiting = screen;
    }

    public static void register() { //register the eventListener
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (MinecraftClient.getInstance().world == null) return; //don't run unless in-game
            if (screenAwaiting != null) {
                MinecraftClient.getInstance().setScreen(screenAwaiting); //set the screen
                screenAwaiting = null; //now that no screen is awaiting, clear it
                return;
            }

        });
    }

}
