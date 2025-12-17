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
import net.minecraft.client.player.ClientInput;
import java.text.DecimalFormat;

public class LabelBundleLasts extends LabelBundle {

    public LabelBundleLasts() {
        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLast45";}
            public String getDisplayName() {return "Last 45";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 74);}
            public void render(GuiGraphics context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;
                
                DecimalFormat df = CyvFabric.df;
                String str = df.format(ParkourTickListener.last45);

                drawString(context, "Last 45: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.width("Last 45: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
            public void renderDummy(GuiGraphics context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "Last 45: ", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.width("Last 45: ")
                        , pos.getAbsoluteY() + 1, color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLastInput";}
            public String getDisplayName() {return "Last Input";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 92);}
            public void render(GuiGraphics context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                ClientInput input = mc.player.input;
                String str = (input.getMoveVector().y > 0 ? "W" : "")
                        + (input.getMoveVector().x > 0 ? "A" : "")
                        + (input.getMoveVector().y < 0 ? "S" : "")
                        + (input.getMoveVector().x < 0 ? "D" : "");

                drawString(context, "Last Input: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.width("Last Input: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(GuiGraphics context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                drawString(context, "Last Input: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, "WASD", pos.getAbsoluteX() + 1 + font.width("Last Input: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLastTurningYaw";}
            public String getDisplayName() {return "Last Turning";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 83);}
            public void render(GuiGraphics context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                DecimalFormat df = CyvFabric.df;
                String z = df.format(ParkourTickListener.lastTurning);

                drawString(context, "Last Turning: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, z, pos.getAbsoluteX() + 1 + font.width("Last Turning: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(GuiGraphics context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                String str = "0.";
                for (int i=0; i<CyvClientConfig.getInt("df",5); i++) str += "0";

                drawString(context, "Last Turning: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.width("Last Turning: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });

        this.labels.add(new DraggableHUDElement() {
            public String getName() {return "labelLastSidestep";}
            public String getDisplayName() {return "Last Sidestep";}
            public int getWidth() {return getLabelWidth(getDisplayName());}
            public int getHeight() {return getLabelHeight();}
            public boolean enabledByDefault() {return false;}
            public ScreenPosition getDefaultPosition() {return new ScreenPosition(177, 101);}
            public void render(GuiGraphics context, ScreenPosition pos) {
                if (!this.isVisible) return;
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                String str = "None";

                if (ParkourTickListener.sidestep == 0) {
                    str = "WAD " + ParkourTickListener.sidestepTime + " ticks";
                }
                else if (ParkourTickListener.sidestep == 1) {
                    str = "WDWA";
                }

                drawString(context, "Last Sidestep: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, str, pos.getAbsoluteX() + 1 + font.width("Last Sidestep: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
            public void renderDummy(GuiGraphics context, ScreenPosition pos) {
                int d = CyvClientConfig.getInt("df",5);
                long color1 = CyvClientColorHelper.color1.drawColor;
                long color2 = CyvClientColorHelper.color2.drawColor;
                Font font = mc.font;

                drawString(context, "Last Sidestep: ", pos.getAbsoluteX() + 1, (int) (pos.getAbsoluteY() + 1), color1);
                drawString(context, "WAD", pos.getAbsoluteX() + 1 + font.width("Last Sidestep: ")
                        , (int) (pos.getAbsoluteY() + 1), color2);
            }
        });
    }

}
