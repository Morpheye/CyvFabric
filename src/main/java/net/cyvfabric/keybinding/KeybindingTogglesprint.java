package net.cyvfabric.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import net.cyvfabric.util.CyvKeybinding;
import net.minecraft.client.Minecraft;

public class KeybindingTogglesprint extends CyvKeybinding {
    public KeybindingTogglesprint() {
        super("key.cyvfabric.togglesprint", InputConstants.UNKNOWN.getValue());
    }

    public static boolean sprintToggled = false;

    @Override
    public void onTickEnd(boolean isPressed) {
        if (isPressed) {
            sprintToggled = !sprintToggled;

            if (!sprintToggled) Minecraft.getInstance().options.keySprint.setDown(false);
        }
    }
}
