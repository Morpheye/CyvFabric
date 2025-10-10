package net.cyvfabric.keybinding;

import net.cyvfabric.command.mpk.CommandMacro;
import net.cyvfabric.util.CyvKeybinding;
import org.lwjgl.glfw.GLFW;

public class KeybindingStopMacro extends CyvKeybinding {
    public KeybindingStopMacro() {
        super("key.cyvfabric.stopmacro", GLFW.GLFW_KEY_UNKNOWN);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            CommandMacro.macroRunning = 1;
        }
    }
}
