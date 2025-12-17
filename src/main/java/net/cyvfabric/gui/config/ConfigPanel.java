package net.cyvfabric.gui.config;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;

public interface ConfigPanel {
    boolean mouseInBounds(double mouseX, double mouseY);
    void mouseClicked(MouseButtonEvent click, boolean doubled);
    void charTyped(CharacterEvent input);
    default void keyPressed(KeyEvent input) {}
    void draw(GuiGraphics context, int mouseX, int mouseY, int scroll);
    default void update() {}
    void mouseDragged(double mouseX, double mouseY);
    void save();
    default void select() {}
    default void unselect() {}

    default void onValueChange() {}
}