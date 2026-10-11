package net.cyvfabric.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import net.cyvfabric.command.mpk.CommandMacro;
import net.cyvfabric.util.CyvKeybinding;

public class KeybindingStopMacro extends CyvKeybinding {
    public KeybindingStopMacro() {
        super("key.cyvfabric.stopmacro", InputConstants.UNKNOWN.getValue());
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            CommandMacro.macroRunning = 1;
        }
    }
}
