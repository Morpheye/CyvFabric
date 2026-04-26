package net.cyvfabric.hud.labels;

import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class LabelBundleTickTimings extends LabelBundle {

    public LabelBundleTickTimings() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLastTiming";}
            public String getDisplayName() {return "Last Timing";}
            public int getWidth() {
                Font font = mc.font;
                return font.width("Last Timing: [Timing & numbers here]");
            }
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 56);}
            public void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                String timing = ParkourTickListener.lastTiming;
                text(context, "Last Timing: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1 + getHeight()*0, color1);
                text(context, timing, pos.getAbsoluteX() + 1 + font.width("Last Timing: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                text(context, "Last Timing: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                text(context, "[Timing]", pos.getAbsoluteX() + 1 + font.width("Last Timing: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelAirtime";}
            public String getDisplayName() {return "Airtime";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(0, 65);}
            public void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                int airtime = ParkourTickListener.lastAirtime;

                text(context, "Airtime: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, airtime, pos.getAbsoluteX() + 1 + font.width("Airtime: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                text(context, "Airtime: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, 0, pos.getAbsoluteX() + 1 + font.width("Airtime: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelTier";}
            public String getDisplayName() {return "Tier";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 137);}
            public void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                int airtime = 12 - ParkourTickListener.lastAirtime;

                text(context, "Tier: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, airtime, pos.getAbsoluteX() + 1 + font.width("Tier: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                text(context, "Tier: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, 0, pos.getAbsoluteX() + 1 + font.width("Tier: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelStoptime";}
            public String getDisplayName() {return "Stoptime";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(400, 418);}
            public void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                int stopTime = ParkourTickListener.lastStopTime;

                text(context, "Stoptime: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, stopTime, pos.getAbsoluteX() + 1 + font.width("Stoptime: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                text(context, "Stoptime: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                text(context, 0, pos.getAbsoluteX() + 1 + font.width("Stoptime: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });
    }

}
