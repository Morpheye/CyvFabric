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

public class LabelBundleHitExtras extends LabelBundle {

    public LabelBundleHitExtras() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelHitAngle";}
            public String getDisplayName() {return "Hit Angle";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 146);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String z = df.format(ParkourTickListener.formatYaw(ParkourTickListener.hf))+"\u00B0";

                drawString(context, "Hit Angle: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, z, pos.getAbsoluteX() + 1 + font.getWidth("Hit Angle: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "Hit Angle: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, str+"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("Hit Angle: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelHitVector";}
            public String getDisplayName() {return "Hit Vector";}
            public int getWidth() {
                TextRenderer font = mc.textRenderer;
                StringBuilder str = new StringBuilder(getDisplayName() + ": 00000.");
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str.append("0");
                str.append("/000.");
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str.append("0");
                str.append("\u00B0");
                return font.getWidth(str.toString());
            }
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 155);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String speed = df.format(Math.hypot(ParkourTickListener.hvx, ParkourTickListener.hvz));
                String angle = df.format(Math.toDegrees(Math.atan2((ParkourTickListener.hvx == 0) ? 0 : -ParkourTickListener.hvx, ParkourTickListener.hvz)));

                drawString(context, "Hit Vector: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1 + getHeight()*0, color1);
                drawString(context, speed, pos.getAbsoluteX() + 1 + font.getWidth("Hit Vector: ")
                        , pos.getAbsoluteY() + 1, color2);
                drawString(context, "/", pos.getAbsoluteX() + 1 + font.getWidth("Hit Vector: " + speed)
                        , pos.getAbsoluteY() + 1, color1);
                drawString(context, angle+"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("Hit Vector: " + speed + "/")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0.";
                for (int i=0; i<Integer.valueOf(CyvFabric.config.configFields.get("df").value.toString()); i++) str += "0";
                drawString(context, "Hit Vector: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1 + getHeight()*0, color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.getWidth("Hit Vector: ")
                        , pos.getAbsoluteY() + 1, color2);
                drawString(context, "/", pos.getAbsoluteX() + 1 + font.getWidth("Hit Vector: " + str)
                        , pos.getAbsoluteY() + 1, color1);
                drawString(context, str+"\u00B0", pos.getAbsoluteX() + 1 + font.getWidth("Hit Vector: " + str + "/")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });
    }

}
