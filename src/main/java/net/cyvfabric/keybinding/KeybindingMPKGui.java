package net.cyvfabric.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import net.cyvfabric.event.events.GuiHandler;
import net.cyvfabric.gui.GuiMPK;
import net.cyvfabric.util.CyvKeybinding;

public class KeybindingMPKGui extends CyvKeybinding {
    public KeybindingMPKGui() {
        super("key.cyvfabric.openmpkgui", InputConstants.KEY_P);
    }

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            GuiHandler.setScreen(new GuiMPK());
        }
    }
}
