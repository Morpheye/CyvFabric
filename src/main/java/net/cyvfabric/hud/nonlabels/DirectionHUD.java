package net.cyvfabric.hud.nonlabels;

import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;

public class DirectionHUD extends DraggableHUDElement {
    public DirectionHUD() {
        this.isDraggable = false;
    }

    @Override
    public ScreenPosition getDefaultPosition() {
        try {
            Window window = MinecraftClient.getInstance().getWindow();
            return new ScreenPosition(window.getScaledWidth() / 2 - this.getWidth() / 2, 0);
        } catch (Exception e) {
            return new ScreenPosition(0, 0);
        }
    }

    @Override
    public String getName() {
        return "directionHUD";
    }

    @Override
    public String getDisplayName() {
        return "Direction HUD";
    }

    @Override
    public int getWidth() {
        return 120;
    }

    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public void render(DrawContext context, ScreenPosition pos) {
        this.position = this.getDefaultPosition();
        long color2 = 0xFFFFFFFF;
        float f = mc.player.getYaw();
        f = f % 360;
        if (f < 0) f += 360;

        TextRenderer font = MinecraftClient.getInstance().textRenderer;

        for (float i=0; i<360; i += 22.5) { //compass
            float distance = (Math.abs(f - i) <= 180) ? i-f : (f > 180) ? (i - (f - 360)) : ((i - 360) - f);
            if (Math.abs(distance) > 95) continue; //distance ranges from -90 to 90
            int xOffset = (int) ( distance * 0.5 * this.getWidth() / 100 );
            int height = (i%90==0) ? font.fontHeight*2/3 : (i%45==0) ? font.fontHeight/2 : font.fontHeight/3;
            context.drawVerticalLine(this.position.getAbsoluteX() + this.getWidth()/2 + xOffset,
                    this.position.getAbsoluteY()+1, this.position.getAbsoluteY()+1+height, 0xFFFFFFFF);

            if (i==0) {//south
                drawString(context, "S", this.position.getAbsoluteX() + this.getWidth()/2 + xOffset - font.getWidth("S")/2,
                        this.position.getAbsoluteY()+2+height, color2);
            } else if (i==90) {//west
                drawString(context, "W", this.position.getAbsoluteX() + this.getWidth()/2 + xOffset - font.getWidth("W")/2,
                        this.position.getAbsoluteY()+2+height, color2);
            } else if (i==180) {//north
                drawString(context, "N", this.position.getAbsoluteX() + this.getWidth()/2 + xOffset - font.getWidth("N")/2,
                        this.position.getAbsoluteY()+2+height, color2);
            } else if (i==270) {//east
                drawString(context, "E", this.position.getAbsoluteX() + this.getWidth()/2 + xOffset - font.getWidth("E")/2,
                        this.position.getAbsoluteY()+2+height, color2);
            }

        }

        context.drawVerticalLine(this.position.getAbsoluteX() + this.getWidth()/2, this.position.getAbsoluteY()+1,
                this.position.getAbsoluteY() + font.fontHeight*3/2, 0xFFFF0000);

    }

    @Override
    public void renderDummy(DrawContext context, ScreenPosition pos) {
        if (!this.isVisible) return;

        this.render(context, pos);

    }
}
