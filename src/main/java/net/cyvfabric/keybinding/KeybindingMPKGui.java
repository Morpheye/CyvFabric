package net.cyvfabric.keybinding;

import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.gui.GuiMPK;
import net.cyvfabric.util.CyvKeybinding;
import org.lwjgl.glfw.GLFW;

public class KeybindingMPKGui extends CyvKeybinding {
    public KeybindingMPKGui() {
        super("key.cyvfabric.openmpkgui", GLFW.GLFW_KEY_P);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            GuiHandler.setScreen(new GuiMPK());
        }
    }
}
