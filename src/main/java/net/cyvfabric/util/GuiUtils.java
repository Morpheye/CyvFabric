package net.cyvfabric.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class GuiUtils extends Screen {
    protected GuiUtils() {
        super(Text.of("GuiUtils"));
    }

    //draw rounded rect
    public static void drawRoundedRect(DrawContext context, int x, int y, int x2, int y2, int r, int color) {
        RenderLayer layer = RenderLayer.getGui();
        Matrix4f matrix4f = context.getMatrices().peek().getPositionMatrix();

        float f = (float)ColorHelper.Argb.getAlpha(color) / 255.0f;
        float g = (float)ColorHelper.Argb.getRed(color) / 255.0f;
        float h = (float)ColorHelper.Argb.getGreen(color) / 255.0f;
        float j = (float)ColorHelper.Argb.getBlue(color) / 255.0f;

        VertexConsumer vertexConsumer = context.getVertexConsumers().getBuffer(layer);
        vertexConsumer.vertex(matrix4f, x, y, 0).color(g, h, j, f);
        vertexConsumer.vertex(matrix4f, x, y2, 0).color(g, h, j, f);
        vertexConsumer.vertex(matrix4f, x2, y2, 0).color(g, h, j, f);
        vertexConsumer.vertex(matrix4f, x2, y, 0).color(g, h, j, f);

        context.draw();
    }

}
