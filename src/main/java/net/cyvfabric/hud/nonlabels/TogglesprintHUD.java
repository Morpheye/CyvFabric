package net.cyvfabric.hud.nonlabels;

import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.cyvfabric.keybinding.KeybindingTogglesprint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class TogglesprintHUD extends DraggableHUDElement {
    private static final String TEXT = "Sprint Toggled";
    private static Integer WIDTH = null;

    public TogglesprintHUD() {
        super("togglesprintHUD", "Togglesprint HUD", true, new ScreenPosition(0, 232));
    }

    @Override
    public int getWidth() {
        if (WIDTH == null)
            WIDTH = MinecraftClient.getInstance().textRenderer.getWidth("[" + TEXT + "]");

        return WIDTH;
    }
    @Override
    public int getHeight() {
        return DraggableHUDLabel.HEIGHT;
    }

    @Override
    public void render(DrawContext context, ScreenPosition pos) {
        if (!this.isVisible) return;
        if (!KeybindingTogglesprint.sprintToggled) return;

        renderText(context, pos);
    }
    @Override
    public void renderDummy(DrawContext context, ScreenPosition pos) {
        renderText(context, pos);
    }

    private void renderText(DrawContext context, ScreenPosition pos) {
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;
        TextRenderer font = mc.textRenderer;
        drawString(context, "[", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
        drawString(context, TEXT, pos.getAbsoluteX() + 1 + font.getWidth("["), pos.getAbsoluteY() + 1, color2);
        drawString(context, "]", pos.getAbsoluteX() + 1 + font.getWidth("[" + TEXT), pos.getAbsoluteY() + 1, color1);
    }
}
