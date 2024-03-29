package net.cyvfabric.event;

import net.cyvfabric.keybinding.KeybindingMPKGui;
import net.cyvfabric.keybinding.KeybindingTogglesprint;
import net.cyvfabric.util.CyvKeybinding;
import net.cyvfabric.keybinding.KeybindingHUDPositions;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

import java.util.ArrayList;

public class KeyInputHandler {
    public static ArrayList<CyvKeybinding> cyvKeybindings = new ArrayList<CyvKeybinding>();

    /**register all keybindings to Minecraft*/
    public static void register() {
        cyvKeybindings.add(new KeybindingHUDPositions());
        cyvKeybindings.add(new KeybindingMPKGui());
        cyvKeybindings.add(new KeybindingTogglesprint());

        for (KeyBinding k : cyvKeybindings) { //register each keybinding in the array
            KeyBindingHelper.registerKeyBinding(k);
        }

        registerKeyInputs();
    }

    /**register actions of keys to Minecraft*/
    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> { //start of tick
            if (MinecraftClient.getInstance().world == null) return; //don't run unless in-game
            for (CyvKeybinding k : cyvKeybindings) {
                k.onTickStart(k.isPressed());
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> { //end of tick
            if (MinecraftClient.getInstance().world == null) return; //don't run unless in-game
            for (CyvKeybinding k : cyvKeybindings) {
                k.onTickEnd(k.wasPressed());
            }
        });
    }


}
