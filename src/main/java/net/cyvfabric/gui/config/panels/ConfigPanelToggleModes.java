package net.cyvfabric.gui.config.panels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.gui.GuiModConfig;
import net.cyvfabric.gui.config.ConfigPanel;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;

public class ConfigPanelToggleModes implements ConfigPanel {
    public boolean sliderValue;
    public String configOption;
    public String displayString;
    public final int index;
    public GuiModConfig screenIn;

    public final String s1, s2;

    private int xPosition;
    private int yPosition;
    private int sizeX;
    private int sizeY;

    public ConfigPanelToggleModes(int index, String configOption, String displayString, String s1, String s2, GuiModConfig screenIn) {
        this.index = index;
        this.displayString = displayString;
        this.configOption = configOption;
        this.screenIn = screenIn;

        this.s1 = s1;
        this.s2 = s2;

        Window sr = MinecraftClient.getInstance().getWindow();
        sizeX = screenIn.sizeX - 20;
        sizeY = MinecraftClient.getInstance().textRenderer.fontHeight * 3 / 2;
        this.xPosition = sr.getScaledWidth() / 2 - screenIn.sizeX / 2 + 10;
        this.yPosition = sr.getScaledHeight() / 2 - screenIn.sizeY / 2 + 10 + (index * MinecraftClient.getInstance().textRenderer.fontHeight * 2);
        this.sliderValue = CyvClientConfig.getBoolean(configOption, false);

    }

    @Override
    public void draw(DrawContext context, int mouseX, int mouseY, int scroll) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        //text label
        context.drawTextWithShadow(textRenderer, this.displayString, this.xPosition, this.yPosition + this.sizeY / 2 - textRenderer.fontHeight / 2 + 1 - scroll, 0xFFFFFFFF);

        //bg
        GuiUtils.drawRoundedRect(context, this.xPosition + this.sizeX / 2, this.yPosition - scroll, this.xPosition + this.sizeX, this.yPosition + this.sizeY - scroll, 3, this.mouseInBounds(mouseX, mouseY) ? CyvFabric.theme.accent1 : CyvFabric.theme.accent2);

        //amount
        context.drawCenteredTextWithShadow(textRenderer, this.sliderValue ? s1 : s2, this.xPosition + this.sizeX * 3 / 4, this.yPosition + this.sizeY / 2 - textRenderer.fontHeight / 2 + 1 - scroll, 0xFFFFFFFF);

    }

    @Override
    public void mouseDragged(double mouseX, double mouseY) {

    }

    @Override
    public boolean mouseInBounds(double mouseX, double mouseY) {
        if (mouseX > this.xPosition + this.sizeX / 2 && mouseY > this.yPosition
                && mouseX < this.xPosition + this.sizeX && mouseY < this.yPosition + this.sizeY) return true;
        return false;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        this.sliderValue = !this.sliderValue;
        CyvClientConfig.set(this.configOption, this.sliderValue);
        onValueChange();

    }


    @Override
    public void keyTyped(char typedChar, int keyCode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save() {
        CyvClientConfig.set(this.configOption, this.sliderValue);
    }
}