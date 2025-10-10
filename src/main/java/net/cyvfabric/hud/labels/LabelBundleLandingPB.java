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

public class LabelBundleLandingPB extends LabelBundle {

    public LabelBundleLandingPB() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelPB";}
            public String getDisplayName() {return "PB";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(106, 0);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                
                DecimalFormat df = CyvFabric.df;
                String pb;
                if (ParkourTickListener.landingBlock == null) {
                    pb = "NaN";
                } else {
                    pb = (ParkourTickListener.landingBlock.pb == null) ? "NaN" : df.format(ParkourTickListener.landingBlock.pb);
                }

                drawString(context, "PB: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(context, pb, pos.getAbsoluteX() + 1 + font.getWidth("PB: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;
                
                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "PB: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.getWidth("PB: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelXOffset";}
            public String getDisplayName() {return "X Offset";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(106, 9);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String x;
                if (ParkourTickListener.landingBlock == null) {
                    x = "NaN";
                } else {
                    x = (ParkourTickListener.landingBlock.lastOffsetX == null) ? "NaN" : df.format(ParkourTickListener.landingBlock.lastOffsetX.doubleValue());
                }

                drawString(context, "X Offset: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, x, pos.getAbsoluteX() + 1 + font.getWidth("X Offset: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "X Offset: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.getWidth("X Offset: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelZOffset";}
            public String getDisplayName() {return "Z Offset";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(106, 18);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String z;
                if (ParkourTickListener.landingBlock == null) {
                    z = "NaN";
                } else {
                    z = (ParkourTickListener.landingBlock.lastOffsetZ == null) ? "NaN" : df.format(ParkourTickListener.landingBlock.lastOffsetZ.doubleValue());
                }

                drawString(context, "Z Offset: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, z, pos.getAbsoluteX() + 1 + font.getWidth("Z Offset: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "Z Offset: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.getWidth("Z Offset: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelTotalOffset";}
            public String getDisplayName() {return "Total Offset";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(106, 45);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String pb;
                if (ParkourTickListener.landingBlock == null) {
                    pb = "NaN";
                } else {
                    pb = (ParkourTickListener.landingBlock.lastPb == null) ? "NaN" : df.format(ParkourTickListener.landingBlock.lastPb);
                }

                drawString(context, "Total Offset: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(context, pb, pos.getAbsoluteX() + 1 + font.getWidth("Total Offset: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "Total Offset: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.getWidth("Total Offset: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelXPB";}
            public String getDisplayName() {return "X PB";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(106, 27);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String x;
                if (ParkourTickListener.landingBlock == null) {
                    x = "NaN";
                } else {
                    x = (ParkourTickListener.landingBlock.pbX == null) ? "NaN" : df.format(ParkourTickListener.landingBlock.pbX.doubleValue());
                }

                drawString(context, "X PB: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, x, pos.getAbsoluteX() + 1 + font.getWidth("X PB: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "X PB: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.getWidth("X PB: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelZPB";}
            public String getDisplayName() {return "Z PB";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(106, 36);}
            public void render(DrawContext context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                DecimalFormat df = CyvFabric.df;
                String z;
                if (ParkourTickListener.landingBlock == null) {
                    z = "NaN";
                } else {
                    z = (ParkourTickListener.landingBlock.pbZ == null) ? "NaN" : df.format(ParkourTickListener.landingBlock.pbZ.doubleValue());
                }

                drawString(context, "Z PB: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, z, pos.getAbsoluteX() + 1 + font.getWidth("Z PB: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(DrawContext context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                TextRenderer font = mc.textRenderer;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "Z PB: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.getWidth("Z PB: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });
    }

}
