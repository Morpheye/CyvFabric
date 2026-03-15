package net.cyvfabric.hud.structure;

import net.minecraft.client.gui.GuiGraphicsExtractor;

public interface IRenderer {
    int getWidth();
    int getHeight();

    void save(ScreenPosition pos);

    ScreenPosition load();

    void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos);

    default void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
        extractRenderState(context, pos);
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
