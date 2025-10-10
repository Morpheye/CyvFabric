package net.cyvfabric.hud.labels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.text.DecimalFormat;

public class LabelBundleTurningAngles extends LabelBundle {

    public LabelBundleTurningAngles() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelJumpAngle";}
            public String getDisplayName() {return "Jump Angle";}
            public int getWidth() {return getLabelWidth(getDisplayName(), true);}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 103);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String ja = df.format(ParkourTickListener.formatYaw(ParkourTickListener.jf));
                drawString(context, "Jump Angle: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1 + getHeight()*0, color1);
                drawString(context, ja+"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("Jump Angle: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "Jump Angle: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1 + getHeight()*0, color1);
                drawString(context, str+"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("Jump Angle: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelSecondTurn";}
            public String getDisplayName() {return "Second Turn";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 112);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String st = df.format(ParkourTickListener.formatYaw(ParkourTickListener.sf - ParkourTickListener.jf));

                drawString(context, "Second Turn: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, st, pos.getAbsoluteX() + 1 + font.getWidth("Second Turn: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "Second Turn: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.getWidth("Second Turn: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelPreturn";}
            public String getDisplayName() {return "Preturn";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 121);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String pt = df.format(ParkourTickListener.formatYaw(ParkourTickListener.pf));

                drawString(context, "Preturn: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, pt+"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("Preturn: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "Preturn: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, str+"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("Preturn: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });
    }

}
