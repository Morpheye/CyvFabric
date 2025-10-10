package net.cyvfabric.keybinding;

import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.gui.GuiHUDPositions;
import net.cyvfabric.util.CyvKeybinding;
import org.lwjgl.glfw.GLFW;

public class KeybindingHUDPositions extends CyvKeybinding {
    public KeybindingHUDPositions() {
        super("key.cyvfabric.openhudpositions", GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            GuiHandler.setScreen(new GuiHUDPositions(false));
        }
    }
}
