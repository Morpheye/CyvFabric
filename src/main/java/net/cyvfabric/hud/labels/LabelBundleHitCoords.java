package net.cyvfabric.hud.labels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import java.text.DecimalFormat;

public class LabelBundleHitCoords extends LabelBundle {

    public LabelBundleHitCoords() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelHitX";}
            public String getDisplayName() {return "Hit X";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 131);}
            public void render(GuiGraphics context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                DecimalFormat df = CyvFabric.df;
                String x = df.format(ParkourTickListener.hx);

                drawString(context, "Hit X: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(context, x, pos.getAbsoluteX() + 1 + font.width("Hit X: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(GuiGraphics context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "Hit X: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.width("Hit X: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelHitY";}
            public String getDisplayName() {return "Hit Y";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 140);}
            public void render(GuiGraphics context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                DecimalFormat df = CyvFabric.df;
                String y = df.format(ParkourTickListener.hy);

                drawString(context, "Hit Y: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, y, pos.getAbsoluteX() + 1 + font.width("Hit Y: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(GuiGraphics context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "Hit Y: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.width("Hit Y: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelHitZ";}
            public String getDisplayName() {return "Hit Z";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 149);}
            public void render(GuiGraphics context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                DecimalFormat df = CyvFabric.df;
                String z = df.format(ParkourTickListener.hz);

                drawString(context, "Hit Z: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, z, pos.getAbsoluteX() + 1 + font.width("Hit Z: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(GuiGraphics context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "Hit Z: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.width("Hit Z: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });


    }

}
