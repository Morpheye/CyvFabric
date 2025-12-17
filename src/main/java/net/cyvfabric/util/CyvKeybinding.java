package net.cyvfabric.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.cyvfabric.CyvFabric;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

/**CyvFabric custom keybinding*/
public class CyvKeybinding extends KeyMapping {
    public static final Minecraft client = Minecraft.getInstance();
    public static final KeyMapping.Category KEY_CATEGORY_CYVFABRIC = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(CyvFabric.MOD_ID, "cyv_keys")); //cyvfabric keybinding category

    public CyvKeybinding(String name, int glfw_key) {
        super(name, InputConstants.Type.KEYSYM, glfw_key, KEY_CATEGORY_CYVFABRIC);
    }

    /**Called on start of tick*/
    public void onTickStart(boolean isPressed) {}

    /**Called on end of tick*/
    public void onTickEnd(boolean isPressed) {}

}
