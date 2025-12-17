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

public class ConfigPanelEmptySpace implements ConfigPanel {
    public final int index;
    public GuiModConfig screenIn;

    private int xPosition;
    private int yPosition;
    private int sizeX;
    private int sizeY;

    public ConfigPanelEmptySpace(int index, GuiModConfig screenIn) {
        this.index = index;
        this.screenIn = screenIn;

        Window sr = Minecraft.getInstance().getWindow();
        sizeX = screenIn.sizeX - 20;
        sizeY = Minecraft.getInstance().font.lineHeight * 3 / 2;
        this.xPosition = sr.getGuiScaledWidth() / 2 - screenIn.sizeX / 2 + 10;
        this.yPosition = sr.getGuiScaledHeight() / 2 - screenIn.sizeY / 2 + 10 + (index * Minecraft.getInstance().font.lineHeight * 2);

    }

    @Override
    public void draw(GuiGraphics context, int mouseX, int mouseY, int scroll) {}

    @Override
    public void mouseDragged(double mouseX, double mouseY) {}

    @Override
    public boolean mouseInBounds(double mouseX, double mouseY) {
        if (mouseX > this.xPosition + this.sizeX / 2 && mouseY > this.yPosition
                && mouseX < this.xPosition + this.sizeX && mouseY < this.yPosition + this.sizeY) return true;
        return false;
    }

    @Override
    public void mouseClicked(MouseButtonEvent click, boolean doubled) {}

    @Override
    public void charTyped(CharacterEvent input) {}

    @Override
    public void save() {}
}