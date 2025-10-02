package net.cyvfabric.gui.config;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;

public interface ConfigPanel {
    boolean mouseInBounds(double mouseX, double mouseY);
    void mouseClicked(Click click, boolean doubled);
    void charTyped(CharInput input);
    default void keyPressed(KeyInput input) {}
    void draw(DrawContext context, int mouseX, int mouseY, int scroll);
    default void update() {}
    void mouseDragged(double mouseX, double mouseY);
    void save();
    default void select() {}
    default void unselect() {}

    default void onValueChange() {}
}