package net.cyvfabric.event.events;

import net.cyvfabric.keybinding.KeybindingTogglesprint;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

public class SprintSneakHandler {

    public static void register() {
        ClientTickEvents.START_CLIENT_TICK.register((client) -> {
            try {
                if (KeybindingTogglesprint.sprintToggled) {
                    if (Minecraft.getInstance().player != null) {
                        Minecraft.getInstance().options.keySprint.setDown(true);
                    }
                }

            } catch (Exception e) {

            }
        });
    }

}
