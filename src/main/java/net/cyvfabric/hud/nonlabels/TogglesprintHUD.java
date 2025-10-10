package net.cyvfabric.hud.nonlabels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.cyvfabric.keybinding.KeybindingTogglesprint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.text.DecimalFormat;

public class TogglesprintHUD extends DraggableHUDElement {
    public String getName() {return "togglesprintHUD";}
    public String getDisplayName() {return "Togglesprint HUD";}
    public boolean enabledByDefault() {return true;}
    public int getWidth() {
        TextRenderer font = MinecraftClient.getInstance().textRenderer;
        return font.getWidth("[Sprint Toggled]");
    }
    public int getHeight() {return 9;}
    public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 232);}
    public void render(DrawContext context, ScreenPosition pos) {
        if (!this.isVisible) return;
        if (!KeybindingTogglesprint.sprintToggled) return;
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;
        TextRenderer font = mc.textRenderer;
        DecimalFormat df = CyvFabric.df;
        String p;
        drawString(context, "[", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
        drawString(context, "Sprint Toggled", pos.getAbsoluteX() + 1 + font.getWidth("["), pos.getAbsoluteY() + 1, color2);
        drawString(context, "]", pos.getAbsoluteX() + 1+ font.getWidth("[Sprint Toggled"), pos.getAbsoluteY() + 1, color1);

    }
    public void renderDummy(DrawContext context, ScreenPosition pos) {
        int d = CyvClientConfig.getInt("df",5);
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;
        TextRenderer font = mc.textRenderer;
        drawString(context, "[", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
        drawString(context, "Sprint Toggled", pos.getAbsoluteX() + 1 + font.getWidth("["), pos.getAbsoluteY() + 1, color2);
        drawString(context, "]", pos.getAbsoluteX() + 1+ font.getWidth("[Sprint Toggled"), pos.getAbsoluteY() + 1, color1);
    }

}
