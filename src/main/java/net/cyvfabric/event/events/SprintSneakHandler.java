package net.cyvfabric.event.events;

import net.cyvfabric.keybinding.KeybindingTogglesprint;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class SprintSneakHandler {

    public static void register() {
        ClientTickEvents.START_CLIENT_TICK.register((client) -> {
            try {
                if (KeybindingTogglesprint.sprintToggled) {
                    if (MinecraftClient.getInstance().player != null) {
                        MinecraftClient.getInstance().options.sprintKey.setPressed(true);
                    }
                }

            } catch (Exception e) {

            }
        });
    }

}
