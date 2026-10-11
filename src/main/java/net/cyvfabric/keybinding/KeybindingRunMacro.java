package net.cyvfabric.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import net.cyvfabric.command.mpk.CommandMacro;
import net.cyvfabric.util.CyvKeybinding;

public class KeybindingRunMacro extends CyvKeybinding {
    public KeybindingRunMacro() {
        super("key.cyvfabric.runmacro", InputConstants.KEY_V);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            CommandMacro.runMacro(null);
        }
    }
}
