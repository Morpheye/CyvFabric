package net.cyvfabric.hud.nonlabels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.cyvfabric.keybinding.KeybindingTogglesprint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.text.DecimalFormat;

public class TogglesprintHUD extends DraggableHUDElement {
    @Override
    public String getName() {return "togglesprintHUD";}
    @Override
    public String getDisplayName() {return "Togglesprint HUD";}
    @Override
    public boolean enabledByDefault() {return true;}
    @Override
    public int getWidth() {
        Font font = Minecraft.getInstance().font;
        return font.width("[Sprint Toggled]");
    }
    @Override
    public int getHeight() {return 9;}
    @Override
    public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 232);}

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos) {
        if (!this.isVisible) return;
        if (!KeybindingTogglesprint.sprintToggled) return;
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;
        Font font = mc.font;
        DecimalFormat df = CyvFabric.df;
        String p;
        text(context, "[", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
        text(context, "Sprint Toggled", pos.getAbsoluteX() + 1 + font.width("["), pos.getAbsoluteY() + 1, color2);
        text(context, "]", pos.getAbsoluteX() + 1+ font.width("[Sprint Toggled"), pos.getAbsoluteY() + 1, color1);
    }
    @Override
    public void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;
        Font font = mc.font;
        text(context, "[", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
        text(context, "Sprint Toggled", pos.getAbsoluteX() + 1 + font.width("["), pos.getAbsoluteY() + 1, color2);
        text(context, "]", pos.getAbsoluteX() + 1+ font.width("[Sprint Toggled"), pos.getAbsoluteY() + 1, color1);
    }
}
