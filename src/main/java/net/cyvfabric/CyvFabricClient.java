package net.cyvfabric;

import net.cyvfabric.event.CommandInitializer;
import net.cyvfabric.event.ConfigLoader;
import net.cyvfabric.event.events.*;
import net.cyvfabric.hud.HUDManager;
import net.fabricmc.api.ClientModInitializer;

//Client-sided portion of the mod
public class CyvFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() { //called when the mod is initialized on the client-side
        HUDManager.init(); //this happens before config, so the config can load in the mod data afterwards.
        CyvFabric.config.init(); //then init the config, to create the config fields for all the labels
        ConfigLoader.init(CyvFabric.config); //now read in the label data from the file

        KeyInputHandler.register(); //register mod keybinds
        CommandInitializer.register(); //register mod commands
        GuiHandler.register(); //register gui listener

        ParkourTickListener.register();
        MacroListener.register();
        SprintSneakHandler.register();

        CyvFabric.LOGGER.info("CyvFabric client initialized!");
    }
}
