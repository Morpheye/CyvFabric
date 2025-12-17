package net.cyvfabric.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GuiUtils extends Screen {
    protected GuiUtils() {
        super(Component.nullToEmpty("GuiUtils"));
    }

    //draw rounded rect
    public static void drawRoundedRect(GuiGraphics context, int x, int y, int x2, int y2, int r, int color) {
        context.fill(x, y, x2, y2, color);
    }

    public static void drawBorder(GuiGraphics context, int x, int y, int width, int height, int color) {
        int x2 = x + width - 1;
        int y2 = y + height - 1;
        context.hLine(x, x2, y, color);
        context.hLine(x, x2, y2, color);
        context.vLine(x, y, y2, color);
        context.vLine(x2, y, y2, color);
    }
}
