package net.cyvfabric.keybinding;

import net.cyvfabric.util.CyvKeybinding;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class KeybindingTogglesprint extends CyvKeybinding {
    public KeybindingTogglesprint() {
        super("key.cyvfabric.togglesprint", GLFW.GLFW_KEY_UNKNOWN);
    }

    public static boolean sprintToggled = false;

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            sprintToggled = !sprintToggled;

            if (!sprintToggled) MinecraftClient.getInstance().options.sprintKey.setPressed(false);
        }
    }
}
