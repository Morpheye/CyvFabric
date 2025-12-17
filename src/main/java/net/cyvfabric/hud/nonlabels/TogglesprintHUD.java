package net.cyvfabric.hud.nonlabels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.cyvfabric.keybinding.KeybindingTogglesprint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import java.text.DecimalFormat;

public class TogglesprintHUD extends DraggableHUDElement {
    public String getName() {return "togglesprintHUD";}
    public String getDisplayName() {return "Togglesprint HUD";}
    public boolean enabledByDefault() {return true;}
    public int getWidth() {
        Font font = Minecraft.getInstance().font;
        return font.width("[Sprint Toggled]");
    }
    public int getHeight() {return 9;}
    public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 232);}
    public void render(GuiGraphics context, ScreenPosition pos) {
        if (!this.isVisible) return;
        if (!KeybindingTogglesprint.sprintToggled) return;
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;
        Font font = mc.font;
        DecimalFormat df = CyvFabric.df;
        String p;
        drawString(context, "[", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
        drawString(context, "Sprint Toggled", pos.getAbsoluteX() + 1 + font.width("["), pos.getAbsoluteY() + 1, color2);
        drawString(context, "]", pos.getAbsoluteX() + 1+ font.width("[Sprint Toggled"), pos.getAbsoluteY() + 1, color1);

    }
    public void renderDummy(GuiGraphics context, ScreenPosition pos) {
        int d = CyvClientConfig.getInt("df",5);
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;
        Font font = mc.font;
        drawString(context, "[", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
        drawString(context, "Sprint Toggled", pos.getAbsoluteX() + 1 + font.width("["), pos.getAbsoluteY() + 1, color2);
        drawString(context, "]", pos.getAbsoluteX() + 1+ font.width("[Sprint Toggled"), pos.getAbsoluteY() + 1, color1);
    }

}
