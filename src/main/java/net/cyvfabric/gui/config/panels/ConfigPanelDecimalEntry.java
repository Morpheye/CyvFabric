package net.cyvfabric.gui.config.panels;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.gui.GuiModConfig;
import net.cyvfabric.gui.config.ConfigPanel;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class ConfigPanelDecimalEntry implements ConfigPanel {
    public TextFieldWidget field;
    public String configOption;
    public String displayString;
    public final int index;
    public GuiModConfig screenIn;

    private int xPosition;
    private int yPosition;
    private int sizeX;
    private int sizeY;

    private double minBound = -Double.MAX_VALUE;
    private double maxBound = Double.MAX_VALUE;

    public ConfigPanelDecimalEntry(int index, String configOption, String displayString, double min, double max, GuiModConfig screenIn) {
        this(index, configOption, displayString, screenIn);
        this.minBound = min;
        this.maxBound = max;
    }

    public ConfigPanelDecimalEntry(int index, String configOption, String displayString, GuiModConfig screenIn) {
        this.index = index;
        this.displayString = displayString;
        this.configOption = configOption;
        this.screenIn = screenIn;

        Window sr = MinecraftClient.getInstance().getWindow();
        sizeX = screenIn.sizeX-20;
        sizeY = MinecraftClient.getInstance().textRenderer.fontHeight*3/2;
        this.xPosition = sr.getScaledWidth()/2-screenIn.sizeX/2+10;
        this.yPosition = sr.getScaledHeight()/2-screenIn.sizeY/2+10 + (index * MinecraftClient.getInstance().textRenderer.fontHeight * 2);

        this.field = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, this.xPosition+this.sizeX/2+2, this.yPosition+this.sizeY/2-MinecraftClient.getInstance().textRenderer.fontHeight/2+1, this.sizeX/2-4, MinecraftClient.getInstance().textRenderer.fontHeight/2, Text.empty());
        this.field.setText(CyvClientConfig.getDouble(configOption, 0)+"");
        this.field.setDrawsBackground(false);
        this.field.setVisible(true);

    }

    @Override
    public void draw(DrawContext context, int mouseX, int mouseY, int scroll) {
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, this.displayString, this.xPosition, this.yPosition+this.sizeY/2-MinecraftClient.getInstance().textRenderer.fontHeight/2+1-scroll, 0xFFFFFFFF);
        //bg
        GuiUtils.drawRoundedRect(context, this.xPosition+this.sizeX/2, this.yPosition-scroll, this.xPosition+this.sizeX, this.yPosition+this.sizeY-scroll, 3, this.mouseInBounds(mouseX, mouseY) ? CyvFabric.theme.shade1 : CyvFabric.theme.shade2);


        this.field.setY(this.yPosition+this.sizeY/2-MinecraftClient.getInstance().textRenderer.fontHeight/2+1-scroll);
        this.field.render(context, mouseX, mouseY, MinecraftClient.getInstance().getRenderTickCounter().getTickProgress(false));
    }

    @Override
    public void mouseDragged(double mouseX, double mouseY) {
    }

    @Override
    public boolean mouseInBounds(double mouseX, double mouseY) {
        if (mouseX > this.xPosition+this.sizeX/2 && mouseY > this.yPosition
                && mouseX < this.xPosition+this.sizeX && mouseY < this.yPosition+this.sizeY) return true;
        return false;
    }

    @Override
    public void mouseClicked(Click click, boolean doubled) {
        this.field.mouseClicked(click, doubled);

        if (!(click.x() >= field.getX() && click.x() <= field.getX() + field.getWidth() && click.y() >= field.getY() && click.y() <= field.getY() + field.getHeight())) {
            this.unselect();
        } else {
            this.select();
        }
    }


    @Override
    public void charTyped(CharInput input) {
        this.field.charTyped(input);
    }

    @Override
    public void keyPressed(KeyInput input) {
        this.field.keyPressed(input);
    }

    @Override
    public void save() {
        double val = 0;
        try {
            val = MathHelper.clamp(Double.valueOf(this.field.getText()), this.minBound, this.maxBound);
            this.field.setText(val+"");
            CyvClientConfig.set(this.configOption, val);
        } catch (Exception e) {}
    }

    @Override
    public void select() {
        this.field.setFocused(true);
    }

    @Override
    public void unselect() {
        this.field.setFocused(false);
        try {
            double val = MathHelper.clamp(Double.valueOf(this.field.getText()), this.minBound, this.maxBound);
            this.field.setText(val+"");
            CyvClientConfig.set(this.configOption, val);
        } catch (Exception e) {}
        onValueChange();
    }


}