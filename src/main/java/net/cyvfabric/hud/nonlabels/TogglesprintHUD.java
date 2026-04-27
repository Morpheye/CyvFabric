package net.cyvfabric.hud.nonlabels;

import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.cyvfabric.keybinding.KeybindingTogglesprint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class TogglesprintHUD extends DraggableHUDElement {
    private static final String TEXT = "Sprint Toggled";
    private static Integer WIDTH = null;

    public TogglesprintHUD() {
        super("togglesprintHUD", "Togglesprint HUD", true, new ScreenPosition(0, 232));
    }

    @Override
    public int getWidth() {
        if (WIDTH == null)
            WIDTH = Minecraft.getInstance().font.width("[" + TEXT + "]");

        return WIDTH;
    }
    @Override
    public int getHeight() {
        return DraggableHUDLabel.HEIGHT;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos) {
        if (!this.isVisible) return;
        if (!KeybindingTogglesprint.sprintToggled) return;

        renderText(context, pos);
    }
    @Override
    public void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
        renderText(context, pos);
    }

    private void renderText(GuiGraphicsExtractor context, ScreenPosition pos) {
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;
        Font font = mc.font;
        text(context, "[", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
        text(context, TEXT, pos.getAbsoluteX() + 1 + font.width("["), pos.getAbsoluteY() + 1, color2);
        text(context, "]", pos.getAbsoluteX() + 1 + font.width("[" + TEXT), pos.getAbsoluteY() + 1, color1);
    }
}
