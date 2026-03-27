package net.cyvfabric.hud.labels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.text.DecimalFormat;

public class LabelBundleLandingCoords extends LabelBundle {

    public LabelBundleLandingCoords() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLandingX";}
            public String getDisplayName() {return "Last Landing X";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 75);}
            public void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;
                
                DecimalFormat df = CyvFabric.df;
                String x = df.format(ParkourTickListener.lx);

                text(context, "Last Landing X: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                text(context, x, pos.getAbsoluteX() + 1 + font.width("Last Landing X: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;
                
                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                text(context, "Last Landing X: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                text(context, str, pos.getAbsoluteX() + 1 + font.width("Last Landing X: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLandingY";}
            public String getDisplayName() {return "Last Landing Y";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 84);}
            public void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                DecimalFormat df = CyvFabric.df;
                String y = df.format(ParkourTickListener.ly);

                text(context, "Last Landing Y: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, y, pos.getAbsoluteX() + 1 + font.width("Last Landing Y: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                text(context, "Last Landing Y: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, str, pos.getAbsoluteX() + 1 + font.width("Last Landing Y: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLandingZ";}
            public String getDisplayName() {return "Last Landing Z";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 93);}
            public void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                DecimalFormat df = CyvFabric.df;
                String z = df.format(ParkourTickListener.lz);

                text(context, "Last Landing Z: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, z, pos.getAbsoluteX() + 1 + font.width("Last Landing Z: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                text(context, "Last Landing Z: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, str, pos.getAbsoluteX() + 1 + font.width("Last Landing Z: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });
    }

}
