package net.cyvfabric.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CyvGui extends Screen {
    private Screen parent; //parent screen

    public CyvGui(String name) {
        super(Component.nullToEmpty(name));
    }

    //called each frame, put the drawScreen things here.
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float partialTicks) {
//        Minecraft mc = Minecraft.getInstance();
    }

    @Override //called upon GUI closing
    public void onClose() {
        Minecraft.getInstance().setScreen(parent); //sets screen to parent, or closes
    }
}
