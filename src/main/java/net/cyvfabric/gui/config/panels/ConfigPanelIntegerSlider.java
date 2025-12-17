package net.cyvfabric.gui.config.panels;

import com.mojang.blaze3d.platform.Window;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.gui.GuiModConfig;
import net.cyvfabric.gui.config.ConfigPanel;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.util.Mth;

public class ConfigPanelIntegerSlider implements ConfigPanel {
    public int sliderValue;
    public String configOption;
    public String displayString;
    public final int minValue;
    private final int maxValue;
    public final int index;
    public GuiModConfig screenIn;

    private int xPosition;
    private int yPosition;
    private int sizeX;
    private int sizeY;

    public ConfigPanelIntegerSlider(int index, String configOption, String displayString, int minValue, int maxValue, GuiModConfig screenIn) {
        this.index = index;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.displayString = displayString;
        this.configOption = configOption;
        this.screenIn = screenIn;

        Window sr = Minecraft.getInstance().getWindow();
        sizeX = screenIn.sizeX-20;
        sizeY = Minecraft.getInstance().font.lineHeight*3/2;
        this.xPosition = sr.getGuiScaledWidth()/2-screenIn.sizeX/2+10;
        this.yPosition = sr.getGuiScaledHeight()/2-screenIn.sizeY/2+10 + (index * Minecraft.getInstance().font.lineHeight * 2);
        this.sliderValue = CyvClientConfig.getInt(configOption, 0);
        Mth.clamp(this.sliderValue, minValue, maxValue);

    }

    @Override
    public void draw(GuiGraphics context, int mouseX, int mouseY, int scroll) {
        //text label
        context.drawString(Minecraft.getInstance().font, this.displayString, this.xPosition, this.yPosition+this.sizeY/2-Minecraft.getInstance().font.lineHeight/2+1-scroll, 0xFFFFFFFF);
        //bg
        GuiUtils.drawRoundedRect(context, this.xPosition+this.sizeX/2, this.yPosition-scroll, this.xPosition+this.sizeX, this.yPosition+this.sizeY-scroll, 3, this.mouseInBounds(mouseX, mouseY) ? CyvFabric.theme.shade1 : CyvFabric.theme.shade2);
        //slider
        GuiUtils.drawRoundedRect(context, this.xPosition+this.sizeX/2+(int)(sizeX/2 * (sliderValue-minValue)/(maxValue-minValue))-3, this.yPosition-1-scroll,
                this.xPosition+this.sizeX/2+(int)(sizeX/2 * (sliderValue-minValue)/(maxValue-minValue))+3, this.yPosition+this.sizeY+1-scroll, 1, CyvFabric.theme.mainBase());
        //amount
        context.drawCenteredString(Minecraft.getInstance().font, " "+this.sliderValue, this.xPosition+this.sizeX*3/4, this.yPosition+this.sizeY/2-Minecraft.getInstance().font.lineHeight/2+1-scroll, 0xFFFFFFFF);

    }

    @Override
    public void mouseDragged(double mouseX, double mouseY) {
        this.sliderValue = (int)((mouseX+2-(this.xPosition+this.sizeX/2))/(float)(this.sizeX/2) * (this.maxValue - this.minValue)) + this.minValue;
        this.sliderValue = (int) Mth.clamp(this.sliderValue, this.minValue, this.maxValue);
        CyvClientConfig.set(this.configOption, this.sliderValue);
        onValueChange();
    }

    @Override
    public boolean mouseInBounds(double mouseX, double mouseY) {
        if (mouseX > this.xPosition+this.sizeX/2 && mouseY > this.yPosition
                && mouseX < this.xPosition+this.sizeX && mouseY < this.yPosition+this.sizeY) return true;
        return false;
    }

    @Override
    public void mouseClicked(MouseButtonEvent click, boolean doubled) {
        // TODO Auto-generated method stub

    }


    @Override
    public void charTyped(CharacterEvent input) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save() {
        CyvClientConfig.set(this.configOption, this.sliderValue);
    }


}