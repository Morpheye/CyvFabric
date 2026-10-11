package net.cyvfabric.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.gui.GuiHUDPositions;
import net.cyvfabric.util.CyvKeybinding;

public class KeybindingHUDPositions extends CyvKeybinding {
    public KeybindingHUDPositions() {
        super("key.cyvfabric.openhudpositions", InputConstants.KEY_RSHIFT);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            GuiHandler.setScreen(new GuiHUDPositions(false));
        }
    }
}
