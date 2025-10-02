package net.cyvfabric.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class GuiUtils extends Screen {
    protected GuiUtils() {
        super(Text.of("GuiUtils"));
    }

    //draw rounded rect
    public static void drawRoundedRect(DrawContext context, int x, int y, int x2, int y2, int r, int color) {
        context.fill(x, y, x2, y2, color);
    }

    public static void drawBorder(DrawContext context, int x, int y, int width, int height, int color) {
        int x2 = x + width - 1;
        int y2 = y + height - 1;
        context.drawHorizontalLine(x, x2, y, color);
        context.drawHorizontalLine(x, x2, y2, color);
        context.drawVerticalLine(x, y, y2, color);
        context.drawVerticalLine(x2, y, y2, color);
    }
}
