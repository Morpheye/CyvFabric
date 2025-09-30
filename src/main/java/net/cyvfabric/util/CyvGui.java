package net.cyvfabric.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class CyvGui extends Screen {
    private Screen parent; //parent screen

    public CyvGui(String name) {
        super(Text.of(name));
    }

    @Override //called upon GUI initialization or resizing
    protected void init() {}

    @Override //called each frame, put the drawScreen things here.
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        MinecraftClient mc = MinecraftClient.getInstance();
    }

    @Override //called every tick
    public void tick() {

    }

    @Override //called upon GUI closing
    public void close() {
        MinecraftClient.getInstance().setScreen(parent); //sets screen to parent, or closes
    }
}
