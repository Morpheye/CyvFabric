package net.cyvfabric.hud.structure;

import net.minecraft.client.gui.GuiGraphics;

public interface IRenderer {
    int getWidth();
    int getHeight();

    public void save(ScreenPosition pos);

    public ScreenPosition load();

    void render(GuiGraphics context, ScreenPosition pos);

    default void renderDummy(GuiGraphics context, ScreenPosition pos) {
        render(context, pos);
    }

    default boolean renderInChat() {
        return true;
    }

    default boolean renderInGui() {
        return false;
    }

    default boolean renderInOverlay() {
        return false;
    }

    

}
