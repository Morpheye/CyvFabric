package net.cyvfabric.hud.nonlabels;

import com.mojang.blaze3d.platform.Window;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class DirectionHUD extends DraggableHUDElement {
    public DirectionHUD() {
        this.isDraggable = false;
    }

    @Override
    public ScreenPosition getDefaultPosition() {
        try {
            Window window = Minecraft.getInstance().getWindow();
            return new ScreenPosition(window.getGuiScaledWidth() / 2 - this.getWidth() / 2, 0);
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
    public void render(GuiGraphics context, ScreenPosition pos) {
        this.position = this.getDefaultPosition();
        long color2 = 0xFFFFFFFF;
        float f = mc.player.getYRot();
        f = f % 360;
        if (f < 0) f += 360;

        Font font = Minecraft.getInstance().font;

        for (float i=0; i<360; i += 22.5) { //compass
            float distance = (Math.abs(f - i) <= 180) ? i-f : (f > 180) ? (i - (f - 360)) : ((i - 360) - f);
            if (Math.abs(distance) > 95) continue; //distance ranges from -90 to 90
            int xOffset = (int) ( distance * 0.5 * this.getWidth() / 100 );
            int height = (i%90==0) ? font.lineHeight*2/3 : (i%45==0) ? font.lineHeight/2 : font.lineHeight/3;
            context.vLine(this.position.getAbsoluteX() + this.getWidth()/2 + xOffset,
                    this.position.getAbsoluteY()+1, this.position.getAbsoluteY()+1+height, 0xFFFFFFFF);

            if (i==0) {//south
                drawString(context, "S", this.position.getAbsoluteX() + this.getWidth()/2 + xOffset - font.width("S")/2,
                        this.position.getAbsoluteY()+2+height, color2);
            } else if (i==90) {//west
                drawString(context, "W", this.position.getAbsoluteX() + this.getWidth()/2 + xOffset - font.width("W")/2,
                        this.position.getAbsoluteY()+2+height, color2);
            } else if (i==180) {//north
                drawString(context, "N", this.position.getAbsoluteX() + this.getWidth()/2 + xOffset - font.width("N")/2,
                        this.position.getAbsoluteY()+2+height, color2);
            } else if (i==270) {//east
                drawString(context, "E", this.position.getAbsoluteX() + this.getWidth()/2 + xOffset - font.width("E")/2,
                        this.position.getAbsoluteY()+2+height, color2);
            }

        }

        context.vLine(this.position.getAbsoluteX() + this.getWidth()/2, this.position.getAbsoluteY()+1,
                this.position.getAbsoluteY() + font.lineHeight*3/2, 0xFFFF0000);

    }

    @Override
    public void renderDummy(GuiGraphics context, ScreenPosition pos) {
        if (!this.isVisible) return;

        this.render(context, pos);

    }
}
