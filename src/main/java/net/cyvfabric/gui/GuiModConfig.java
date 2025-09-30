package net.cyvfabric.gui;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.ColorTheme;
import net.cyvfabric.config.CyvClientColorHelper;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.gui.config.ConfigPanel;
import net.cyvfabric.gui.config.panels.*;
import net.cyvfabric.util.CyvGui;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class GuiModConfig extends CyvGui {
    public int sizeX = 350;
    public int sizeY = 175;
    ArrayList<ConfigPanel> panels = new ArrayList<ConfigPanel>();
    ConfigPanel selectedPanel;
    Window sr;
    SubButton backButton;

    ColorTheme theme;

    float vScroll = 0;
    float scroll = 0;
    int maxScroll = 0;
    boolean scrollClicked = false;

    public GuiModConfig() {
        super("Mod Config");
        sr = MinecraftClient.getInstance().getWindow();

        this.backButton = new SubButton("Back", sr.getScaledWidth()/2-sizeX/2-4, sr.getScaledHeight()/2-sizeY/2-21);
        this.theme = CyvFabric.theme;

        this.updatePanels();

        maxScroll = (int) Math.max(0, MinecraftClient.getInstance().textRenderer.fontHeight * 2 * Math.ceil(panels.size()) - (sizeY-20));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;
    }

    @Override
    public void init() {
    }

    @Override
    public void resize(MinecraftClient mcIn, int w, int h) {
        this.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        //background
        this.renderInGameBackground(context);
        this.theme = CyvFabric.theme;

        //draw text
        context.drawCenteredTextWithShadow(textRenderer, "CyvFabric Config",
                sr.getScaledWidth()/2, sr.getScaledHeight()/2-sizeY/2-18, 0xFFFFFFFF);

        //draw the menu background
        GuiUtils.drawRoundedRect(context, sr.getScaledWidth()/2-sizeX/2-4, sr.getScaledHeight()/2-sizeY/2-4,
                sr.getScaledWidth()/2+sizeX/2+14, sr.getScaledHeight()/2+sizeY/2+4, 10, theme.background1);

        //buttons
        this.backButton.draw(context, mouseX, mouseY + (int) scroll);

        //begin scissoring (I am a very mature individual who does not have a dirty mind)
        double centerx = sr.getScaledWidth()/2;
        double centery = sr.getScaledHeight()/2;
        double scaleFactor = sr.getScaleFactor();

        context.enableScissor(0, (int) (centery - sizeY/2), sr.getWidth(), (int) (centery + sizeY/2));

        for (ConfigPanel p : this.panels) {
            p.draw(context, mouseX, mouseY + (int)scroll, (int)scroll);
        }

        context.disableScissor();

        //draw scrollbar
        int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;

        int top = sr.getScaledHeight()/2-sizeY/2+4;
        int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;
        int amount = (int) (top + (bottom - top) * ((float) scroll/maxScroll));

        if (maxScroll == 0) amount = top;

        //color
        int color = theme.border2;
        if (mouseX > sr.getScaledWidth()/2+sizeX/2+2 && mouseX < sr.getScaledWidth()/2+sizeX/2+8 &&
                mouseY > amount && mouseY < amount+scrollbarHeight) {
            color = theme.border1;
        }

        GuiUtils.drawRoundedRect(context, sr.getScaledWidth()/2+sizeX/2+2, amount,
                sr.getScaledWidth()/2+sizeX/2+8, amount+scrollbarHeight, 3, color);
    }

    @Override
    public void tick() {
        if (this.selectedPanel != null) this.selectedPanel.update();

        //smooth scrolling
        this.scroll += this.vScroll;
        this.vScroll *= 0.75;

        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;
    }

    private void updatePanels() {
        this.panels.clear();
        this.scroll = 0;

        panels.add(new ConfigPanelOptionSwitcher<String>(0, "color1", "Color 1", CyvClientColorHelper.colorStrings, this) {
            public void onValueChange() {CyvClientColorHelper.setColor1(CyvClientConfig.getString("color1", "aqua"));}});
        panels.add(new ConfigPanelOptionSwitcher<String>(1, "color2", "Color 2", CyvClientColorHelper.colorStrings, this){
            public void onValueChange() {CyvClientColorHelper.setColor2(CyvClientConfig.getString("color2", "aqua"));}});
        panels.add(new ConfigPanelOptionSwitcher<String>(2, "theme", "Color Theme", ColorTheme.getThemes(), this) {
            public void onValueChange() {CyvFabric.theme = ColorTheme.valueOf(CyvClientConfig.getString("theme", "CYVISPIRIA"));}
        });
        panels.add(new ConfigPanelToggle(3, "whiteChat", "Color2 always white in chat", this));
        panels.add(new ConfigPanelIntegerSlider(4, "df", "Decimal Precision", 1, 16, this) {
            public void onValueChange() {CyvFabric.df.setMaximumFractionDigits(CyvClientConfig.getInt("df", 5));}});
        panels.add(new ConfigPanelToggle(5, "trimZeroes", "Trim Zeroes", this) {
            public void onValueChange() {
                if (CyvClientConfig.getBoolean("trimZeroes", true)) CyvFabric.df.setMinimumFractionDigits(0);
                else CyvFabric.df.setMinimumFractionDigits(CyvClientConfig.getInt("df",5));
        }});
        panels.add(new ConfigPanelEmptySpace(6, this));

        //mpk
        panels.add(new ConfigPanelToggle(7, "showMilliseconds", "Show Millisecond Timings", this));
        panels.add(new ConfigPanelToggle(8, "sendLbChatOffset", "Send Landing Offset", this));
        panels.add(new ConfigPanelToggle(9, "sendMmChatOffset", "Send Momentum Offset", this));
        panels.add(new ConfigPanelToggle(10, "highlightLanding", "Highlight Landing Blocks", this));
        panels.add(new ConfigPanelToggle(11, "highlightLandingCond", "Highlight Landing Conditions", this));
        panels.add(new ConfigPanelToggle(12, "momentumPbCancelling", "Momentum PB Cancelling", this));
        panels.add(new ConfigPanelEmptySpace(13, this));

        //inertia
        panels.add(new ConfigPanelToggle(14, "inertiaEnabled", "Inertia Listener Enabled", this));
        panels.add(new ConfigPanelIntegerSlider(15, "inertiaTick", "Air tick", 1, 12, this));
        panels.add(new ConfigPanelDecimalEntry(16, "inertiaMin", "Min Speed", this));
        panels.add(new ConfigPanelDecimalEntry(17, "inertiaMax", "Max Speed", this));
        panels.add(new ConfigPanelOptionSwitcher<String>(18, "inertiaGroundType", "Ground Type", new String[] {"normal", "ice", "slime"}, this));

        //macro
        panels.add(new ConfigPanelEmptySpace(19, this));
        panels.add(new ConfigPanelToggle(20, "smoothMacro", "Smooth Macro", this));

        maxScroll = (int) Math.max(0, MinecraftClient.getInstance().textRenderer.fontHeight * 2 * Math.ceil(panels.size()) - (sizeY-20));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        if (this.scrollClicked) {
            int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
            int top = sr.getScaledHeight()/2-sizeY/2+4;
            int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;

            scroll = (int) ((float) (click.y() - (sr.getScaledHeight()/2-this.sizeY/2) - scrollbarHeight/2) /(bottom - top) * maxScroll);

            if (scroll > maxScroll) scroll = maxScroll;
            if (scroll < 0) scroll = 0;

            return true;
        }

        if (this.selectedPanel != null) {
            this.selectedPanel.mouseDragged(click.x(), click.y());
            return true;
        }

        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;

        int top = sr.getScaledHeight()/2-sizeY/2+4;
        int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;
        int amount = (int) (top + (bottom - top) * ((float) scroll/maxScroll));
        if (maxScroll == 0) amount = top;
        if (mouseX > sr.getScaledWidth()/2+sizeX/2+2 && mouseX < sr.getScaledWidth()/2+sizeX/2+8 &&
                mouseY > amount && mouseY < amount+scrollbarHeight) {
            scrollClicked = true;
        } else scrollClicked = false;

        if ((!scrollClicked) && verticalAmount != 0) {
            vScroll -= verticalAmount * 3;

            return true;
        }

        return false;
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
        int top = sr.getScaledHeight()/2-sizeY/2+4;
        int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;
        int amount = (int) (top + (bottom - top) * ((float) scroll/maxScroll));

        if (click.x() > sr.getScaledWidth()/2+sizeX/2+2 && click.x() < sr.getScaledWidth()/2+sizeX/2+8 &&
                click.y() > amount && click.y() < amount+scrollbarHeight) {
            this.scrollClicked = true;
            return true;
        } else {
            this.scrollClicked = false;
        }

        if (this.backButton.clicked(click)) {
            this.close();
            return true;
        }

        if (click.x() < sr.getScaledWidth()/2-sizeX/2-4 || click.x() > sr.getScaledWidth()/2+sizeX/2+14 ||
                click.y() < sr.getScaledHeight()/2-sizeY/2-4 || click.y() > sr.getScaledHeight()/2+sizeY/2+4) {
            this.selectedPanel = null;
            return true;
        }

        for (ConfigPanel p : this.panels) {
            if (p.mouseInBounds(click.x(), click.y()+(int)scroll)) {
                if (this.selectedPanel != null) this.selectedPanel.unselect();

                p.mouseClicked(new Click(click.x(), click.y()+(int)scroll, click.buttonInfo()), doubled);
                this.selectedPanel = p;
                p.select();
                return true;
            }
        }

        this.selectedPanel = null;
        return false;
    }

    @Override
    public boolean charTyped(CharInput input) {
        if (input.codepoint() == GLFW.GLFW_KEY_ESCAPE) { //exit the gui
            this.close();
            return true;
        }

        if (this.selectedPanel != null) {
            this.selectedPanel.charTyped(input);
            return true;
        }

        return false;
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (input.getKeycode() == GLFW.GLFW_KEY_ESCAPE) {
            if (this.selectedPanel != null) {
                this.selectedPanel.unselect();
                this.selectedPanel = null;
            }
            else this.close();
            return true;
        }

        if (this.selectedPanel != null) {
            this.selectedPanel.keyPressed(input);
            return true;
        }

        return false;
    }

    @Override
    public void removed() {
        for (ConfigPanel p : this.panels) p.save();
        this.updatePanels();
    }

    class SubButton {
        String text;
        int x, y;
        int sizeX = 80;
        int sizeY = 15;

        SubButton(String text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }

        void draw(DrawContext context, int mouseX, int mouseY) {
            boolean mouseDown = (mouseX > x && mouseX < x + sizeX && mouseY > y && mouseY < y + sizeY);
            GuiUtils.drawRoundedRect(context, x, y, x+sizeX, y+sizeY, 5, mouseDown ? theme.highlight : theme.background1);
            context.drawCenteredTextWithShadow(textRenderer, this.text, x+sizeX/2, y+sizeY/2-textRenderer.fontHeight/2, 0xFFFFFFFF);
        }

        boolean clicked(Click click) {
            if (!(click.x() > x && click.x() < x+sizeX && click.y() > y && click.y() < y+sizeY && click.button() == 0)) return false;
            else return true;
        }

    }
}
