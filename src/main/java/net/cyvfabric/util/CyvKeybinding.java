package net.cyvfabric.util;

import net.cyvfabric.CyvFabric;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

/**CyvFabric custom keybinding*/
public class CyvKeybinding extends KeyBinding {
    public static final MinecraftClient client = MinecraftClient.getInstance();
    public static final KeyBinding.Category KEY_CATEGORY_CYVFABRIC = KeyBinding.Category.create(Identifier.of(CyvFabric.MOD_ID, "cyv_keys")); //cyvfabric keybinding category

    public CyvKeybinding(String name, int glfw_key) {
        super(name, InputUtil.Type.KEYSYM, glfw_key, KEY_CATEGORY_CYVFABRIC);
    }

    /**Called on start of tick*/
    public void onTickStart(boolean isPressed) {}

    /**Called on end of tick*/
    public void onTickEnd(boolean isPressed) {}

}
