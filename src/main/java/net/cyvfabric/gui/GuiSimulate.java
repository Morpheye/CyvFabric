package net.cyvfabric.gui;

import mcpk.Parser;
import mcpk.Player;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.util.CyvGui;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.text.DecimalFormat;
import java.util.ArrayList;

//gui for the in-game movement simulator
public class GuiSimulate extends CyvGui {
    public static ArrayList<String> chatHistory = new ArrayList<String>();

    public boolean repeatEventsEnabled;
    TextFieldWidget input;
    ButtonWidget button;
    int chatHistoryIndex = 0;

    public GuiSimulate() {
        super("Movement Simulator");
    }

    @Override
    protected void init() {
        input = new TextFieldWidget(textRenderer, width/2-width*23/80, (int) (height*0.40-10), width*23/40, 20, Text.empty());
        button = ButtonWidget.builder(Text.of("Calculate"), (widget) -> {})
                .position(width/2-50, height*3/5-10)
                .size(100, 20).build();

        input.setMaxLength(65536);
        this.chatHistoryIndex = 0;
        this.input.setFocused(true);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        Window sr = MinecraftClient.getInstance().getWindow();
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();

        int x1 = (int) (sr.getScaledWidth() * 0.20);
        int y1 = (int) (sr.getScaledHeight() * 0.30);
        int x2 = (int) (sr.getScaledWidth() * 0.80);
        int y2 = (int) (sr.getScaledHeight() * 0.50);

        super.renderInGameBackground(context); //background tint
        GuiUtils.drawRoundedRect(context, x1-2, y1-2, x2+2, y2+2, 9, 0x88000000);
        GuiUtils.drawRoundedRect(context, x1, y1, x2, y2, 7, 0x11000000);

        input.render(context, mouseX, mouseY, partialTicks);
        button.render(context, mouseX, mouseY, partialTicks);

        context.drawCenteredTextWithShadow(textRenderer, "Movement Simulator", x1+46, y1-15, 0xFFFFFFFF);
    }

    @Override
    public boolean charTyped(CharInput charInput) {
        this.input.charTyped(charInput);
        return super.charTyped(charInput);
    }

    @Override
    public boolean keyPressed(KeyInput keyInput) {
        if (keyInput.getKeycode() == GLFW.GLFW_KEY_ENTER) {
            this.close(); //close the gui
            String text = this.input.getText(); //parser shit

            if (text.equals("") || text.equals(" ")) {}
            else {
                if (chatHistory.size() == 0) {
                    chatHistory.add(text);
                } else {
                    if (!chatHistory.get(chatHistory.size()-1).equals(text)) {
                        chatHistory.add(text);
                    }
                }

                output(text);
                return true;
            }
            return true;
        } else if (keyInput.getKeycode() == GLFW.GLFW_KEY_UP) { //scroll up
            if (chatHistoryIndex < chatHistory.size()) {
                chatHistoryIndex++;
                this.input.setText(chatHistory.get(chatHistory.size()-chatHistoryIndex));
                return true;
            }

        } else if (keyInput.getKeycode() == GLFW.GLFW_KEY_DOWN) { //scroll down
            if (chatHistoryIndex > 0) {
                chatHistoryIndex--;
                if (chatHistoryIndex == 0) {
                    this.input.setText("");
                } else {
                    this.input.setText(chatHistory.get(chatHistory.size()-chatHistoryIndex));
                }
                return true;
            }

        }

        this.input.keyPressed(keyInput);

        return super.keyPressed(keyInput);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        input.mouseClicked(click, doubled);
        if (button.mouseClicked(click, doubled)) {
            this.close();
            String text = input.getText(); //parser shit

            if (text.equals("") || text.equals(" ")) {}
            else {
                if (chatHistory.size() == 0) {
                    chatHistory.add(text);
                } else {
                    if (!chatHistory.get(chatHistory.size()-1).equals(text)) {
                        chatHistory.add(text);
                    }
                }
                output(text);

            }}
        return super.mouseClicked(click, doubled);
    }

    private void output(String text) {
        new Thread(() -> {
            Player player = new Player();
            DecimalFormat df = CyvFabric.df;
            player.df = (byte) df.getMaximumFractionDigits();
            Parser parser = new Parser();

            try {
                parser.parse(player, text);
            } catch (Exception e) {
                CyvFabric.sendChatMessage("Parsing failed.");
                return;
            }

            double z = player.z;
            double vz = player.vz;
            double x = player.x;
            double vx = player.vx;

            double vector = Math.sqrt(vx*vx + vz*vz);
            double angle = Math.atan2(-vx,vz) * 180d/Math.PI;

            CyvFabric.sendChatMessage("Simulated parsed string: \247o" + text + "\n\247r"
                    + "z: " + df.format(z) + "\n"
                    + "vz: " + df.format(vz) + "\n"
                    + "x: " + df.format(x) + "\n"
                    + "vx: " + df.format(vx) + "\n"
                    + "Speed Vector: " + df.format(vector) + ", " + df.format(angle) + "\u00B0");
        }).start();

    }

}
