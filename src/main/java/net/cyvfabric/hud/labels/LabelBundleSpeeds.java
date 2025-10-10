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

public class LabelBundleSpeeds extends LabelBundle {

    public LabelBundleSpeeds() {
        labels.add(new DraggableHUDElement() {
            public String getName() {return "labelXSpeed";}
            public String getDisplayName() {return "X Speed";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 187);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                DecimalFormat df = CyvFabric.df;
                String x = df.format(ParkourTickListener.vx);
                drawString(context, "X Speed: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1 + getHeight()*0, color1);
                drawString(context, x, pos.getAbsoluteX() + 1 + font.getWidth("X Speed: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                String str = "0.";
                for (int i=0; i<Integer.valueOf(CyvFabric.config.configFields.get("df").value.toString()); i++) str += "0";
                drawString(context, "X Speed: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.getWidth("X Speed: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        labels.add(new DraggableHUDElement() {
            public String getName() {return "labelYSpeed";}
            public String getDisplayName() {return "Y Speed";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 196);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                DecimalFormat df = CyvFabric.df;
                String y = df.format(ParkourTickListener.vy);
                drawString(context, "Y Speed: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1 + getHeight()*0, color1);
                drawString(context, y, pos.getAbsoluteX() + 1 + font.getWidth("Y Speed: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                String str = "0.";
                for (int i=0; i<Integer.valueOf(CyvFabric.config.configFields.get("df").value.toString()); i++) str += "0";
                drawString(context, "Y Speed: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.getWidth("Y Speed: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        labels.add(new DraggableHUDElement() {
            public String getName() {return "labelZSpeed";}
            public String getDisplayName() {return "Z Speed";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 205);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                DecimalFormat df = CyvFabric.df;
                String z = df.format(ParkourTickListener.vz);
                drawString(context, "Z Speed: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1 + getHeight()*0, color1);
                drawString(context, z, pos.getAbsoluteX() + 1 + font.getWidth("Z Speed: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                String str = "0.";
                for (int i=0; i<Integer.valueOf(CyvFabric.config.configFields.get("df").value.toString()); i++) str += "0";
                drawString(context, "Z Speed: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.getWidth("Z Speed: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

    }
}
