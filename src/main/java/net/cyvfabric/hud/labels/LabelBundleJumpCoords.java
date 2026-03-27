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

public class LabelBundleJumpCoords extends LabelBundle {

    public LabelBundleJumpCoords() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelJumpX";}
            public String getDisplayName() {return "Jump X";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 159);}
            @Override
            public void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;
                
                DecimalFormat df = CyvFabric.df;
                String x = df.format(ParkourTickListener.jx);

                text(context, "Jump X: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                text(context, x, pos.getAbsoluteX() + 1 + font.width("Jump X: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            @Override
            public void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;
                
                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                text(context, "Jump X: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                text(context, str, pos.getAbsoluteX() + 1 + font.width("Jump X: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelJumpY";}
            public String getDisplayName() {return "Jump Y";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 168);}
            @Override
            public void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                DecimalFormat df = CyvFabric.df;
                String y = df.format(ParkourTickListener.jy);

                text(context, "Jump Y: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, y, pos.getAbsoluteX() + 1 + font.width("Jump Y: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            @Override
            public void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                text(context, "Jump Y: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, str, pos.getAbsoluteX() + 1 + font.width("Jump Y: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelJumpZ";}
            public String getDisplayName() {return "Jump Z";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 177);}
            @Override
            public void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                DecimalFormat df = CyvFabric.df;
                String z = df.format(ParkourTickListener.jz);

                text(context, "Jump Z: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, z, pos.getAbsoluteX() + 1 + font.width("Jump Z: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            @Override
            public void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                text(context, "Jump Z: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, str, pos.getAbsoluteX() + 1 + font.width("Jump Z: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });
    }
}
