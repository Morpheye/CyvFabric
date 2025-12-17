package net.cyvfabric.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CyvGui extends Screen {
    private Screen parent; //parent screen

    public CyvGui(String name) {
        super(Component.nullToEmpty(name));
    }

    @Override //called upon GUI initialization or resizing
    protected void init() {}

    @Override //called each frame, put the drawScreen things here.
    public void render(GuiGraphics context, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
    }

    @Override //called every tick
    public void tick() {

    }

    @Override //called upon GUI closing
    public void onClose() {
        Minecraft.getInstance().setScreen(parent); //sets screen to parent, or closes
    }
}
