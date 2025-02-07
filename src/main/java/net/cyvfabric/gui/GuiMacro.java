package net.cyvfabric.gui;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.command.mpk.CommandMacro;
import net.cyvfabric.config.ColorTheme;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.MacroFileInit;
import net.cyvfabric.util.CyvGui;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class GuiMacro extends CyvGui {
    MinecraftClient mc = MinecraftClient.getInstance();
    Window sr = mc.getWindow();
    int sizeX = sr.getScaledWidth()*7/8;
    int sizeY = sr.getScaledHeight()*7/8;

    SubButton addRow;
    SubButton duplicateRow;
    SubButton deleteRow;

    TextFieldWidget fileName;
    SubButton loadFile;

    public ArrayList<MacroLine> macroLines;
    int selectedIndex = -1;

    float vScroll = 0;
    float scroll = 0;
    int maxScroll = 0;
    boolean scrollClicked = false;

    public GuiMacro() {
        super("Macro Gui");
    }

    @Override
    public void resize(MinecraftClient mcIn, int w, int h) {
        close();
    }

    @Override
    public void init() { //initialize the macro
        ArrayList<ArrayList<String>> macro;
        this.macroLines = new ArrayList<MacroLine>();

        //Keyboard.enableRepeatEvents(true);

        this.sizeX = sr.getScaledWidth()*7/8;
        this.sizeY = sr.getScaledHeight()*7/8;

        this.addRow = new SubButton("Add Row", (sr.getScaledWidth() + sizeY +30)/2 - 75, sr.getScaledHeight()/2 - sizeY/2 + 40);
        this.duplicateRow = new SubButton("Duplicate Row", (sr.getScaledWidth() + sizeY +30)/2 - 75, sr.getScaledHeight()/2 - sizeY/2 + 60);
        this.deleteRow = new SubButton("Delete Row", (sr.getScaledWidth() + sizeY +30)/2 - 75, sr.getScaledHeight()/2 - sizeY/2 + 80);

        this.fileName = new TextFieldWidget(mc.textRenderer, (sr.getScaledWidth() + sizeY +30)/2 - 72,
                sr.getScaledHeight()/2 - sizeY/2+mc.textRenderer.fontHeight/2 + 20, 90, mc.textRenderer.fontHeight*2,
                Text.empty());
        fileName.setDrawsBackground(false);
        fileName.setText(CyvClientConfig.getString("currentMacro", "macro"));
        this.loadFile = new SubButton("Load", (sr.getScaledWidth() + sizeY +30)/2 + 25, sr.getScaledHeight()/2 - sizeY/2 + 20);
        loadFile.sizeX = 50;
        loadFile.enabled = true;

        this.macroLines.clear();
        try {
            MacroFileInit.swapFile(CyvClientConfig.getString("currentMacro", "macro"));
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(MacroFileInit.macroFile));
            macro = gson.fromJson(reader, ArrayList.class);
        } catch (Exception e) {
            macro = new ArrayList<ArrayList<String>>();
        }

        if (macro != null) {
            try {
                for (ArrayList<String> line : macro) {
                    try {
                        MacroLine macroLine = new MacroLine();

                        macroLine.w = Boolean.valueOf(line.get(0));
                        macroLine.a = Boolean.valueOf(line.get(1));
                        macroLine.s = Boolean.valueOf(line.get(2));
                        macroLine.d = Boolean.valueOf(line.get(3));
                        macroLine.jump = Boolean.valueOf(line.get(4));
                        macroLine.sprint = Boolean.valueOf(line.get(5));
                        macroLine.sneak = Boolean.valueOf(line.get(6));

                        macroLine.yawField.setText(""+Double.valueOf(line.get(7)));
                        macroLine.pitchField.setText(""+Double.valueOf(line.get(8)));

                        macroLines.add(macroLine);

                    } catch (Exception e) {}
                }
            } catch (Exception e) {}

        }

        maxScroll = (int) Math.max(0, textRenderer.fontHeight * 2 * Math.ceil(macroLines.size()) - (sizeY-20));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        super.renderInGameBackground(context);

        maxScroll = (int) Math.max(0, textRenderer.fontHeight * 2 * Math.ceil(macroLines.size()) - (sizeY-20));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;

        GuiUtils.drawRoundedRect(context, sr.getScaledWidth()/2 - sizeX/2 - 15, sr.getScaledHeight()/2 - sizeY/2 - 4,
                sr.getScaledWidth()/2 + sizeX/2 + 14, sr.getScaledHeight()/2 + sizeY/2 + 4, 5, CyvFabric.theme.background1);

        this.addRow.draw(context, mouseX, mouseY);
        this.duplicateRow.draw(context, mouseX, mouseY);
        this.deleteRow.draw(context, mouseX, mouseY);

        GuiUtils.drawRoundedRect(context, (sr.getScaledWidth() + sizeY +30)/2 - 75, sr.getScaledHeight()/2 - sizeY/2 + 20,
                (sr.getScaledWidth() + sizeY +30)/2 + 20, sr.getScaledHeight()/2 - sizeY/2 + 20 + textRenderer.fontHeight*7/4,
                3, CyvFabric.theme.shade2);
        this.fileName.render(context, mouseX, mouseY, partialTicks);
        this.loadFile.draw(context, mouseX, mouseY);

        this.addRow.enabled = true;
        if (this.selectedIndex > -1 && this.selectedIndex < this.macroLines.size()) {
            this.duplicateRow.enabled = true;
            this.deleteRow.enabled = true;
        } else {
            this.duplicateRow.enabled = false;
            this.deleteRow.enabled = false;
        }


        context.drawTextWithShadow(textRenderer, "Inputs:", sr.getScaledWidth()/2 - sizeX/2 + 13, 5 + sr.getScaledHeight()/2 - sizeY/2, 0xFFFFFFFF);
        context.drawTextWithShadow(textRenderer, "Yaw:", sr.getScaledWidth()/2 - 34, 5 + sr.getScaledHeight()/2 - sizeY/2, 0xFFFFFFFF);
        context.drawTextWithShadow(textRenderer, "Pitch:", sr.getScaledWidth()/2 - 1, 5 + sr.getScaledHeight()/2 - sizeY/2, 0xFFFFFFFF);

        context.enableScissor(sr.getScaledWidth()/2 - ((sizeX/2 + 20)),
                sr.getScaledHeight()/2 - (sizeY/2) + 3 + (textRenderer.fontHeight * 2),
                sizeX, sr.getScaledHeight()/2 + sizeY/2);

        int index = 0;
        for (MacroLine l : macroLines) {
            int yHeight = (int) ((index + 1) * textRenderer.fontHeight*2 - scroll + (sr.getScaledHeight()/2 - sizeY/2));
            context.drawTextWithShadow(textRenderer, ""+(index+1), sr.getScaledWidth()/2 - sizeX/2 - 10,
                    yHeight + textRenderer.fontHeight*2/3, 0xFFFFFFFF);
            l.drawEntry(context, index, (int) scroll, mouseX, mouseY, index == this.selectedIndex, partialTicks);
            index++;
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
        int color = CyvFabric.theme.border2;
        if (mouseX > sr.getScaledWidth()/2+sizeX/2+2 && mouseX < sr.getScaledWidth()/2+sizeX/2+8 &&
                mouseY > amount && mouseY < amount+scrollbarHeight) {
            color = CyvFabric.theme.border1;
        }

        GuiUtils.drawRoundedRect(context, sr.getScaledWidth()/2+sizeX/2+2, amount,
                sr.getScaledWidth()/2+sizeX/2+8, amount+scrollbarHeight, 3, color);

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
        int top = sr.getScaledHeight()/2-sizeY/2+4;
        int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;
        int amount = (int) (top + (bottom - top) * ((float) scroll/maxScroll));

        if (mouseX > sr.getScaledWidth()/2+sizeX/2+2 && mouseX < sr.getScaledWidth()/2+sizeX/2+8 &&
                mouseY > amount && mouseY < amount+scrollbarHeight) {
            this.scrollClicked = true;
            return true;
        } else {
            this.scrollClicked = false;
        }

        vScroll -= (float) (verticalAmount * 3);

        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseEvent) {
        super.mouseClicked(mouseX, mouseY, mouseEvent);

        int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
        int top = sr.getScaledHeight()/2-sizeY/2+4;
        int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;
        int amount = (int) (top + (bottom - top) * ((float) scroll/maxScroll));

        if (mouseX > sr.getScaledWidth()/2+sizeX/2+2 && mouseX < sr.getScaledWidth()/2+sizeX/2+8 &&
                mouseY > amount && mouseY < amount+scrollbarHeight) {
            this.scrollClicked = true;
            return true;
        } else {
            this.scrollClicked = false;
        }

        if (this.fileName.mouseClicked(mouseX, mouseY, mouseEvent)) {
            this.fileName.setFocused(true);
            if (this.selectedIndex > -1 && macroLines.get(selectedIndex) != null) {
                macroLines.get(selectedIndex).yawField.setFocused(false);
                macroLines.get(selectedIndex).pitchField.setFocused(false);
            }
            return true;
        } else this.fileName.setFocused(false);

        int index=0;
        for (MacroLine l : macroLines) {
            if (l.isPressed(index, mouseX, mouseY, mouseEvent)) {

                if (this.selectedIndex == index) {
                    if (!l.pitchField.isFocused() && !l.yawField.isFocused()) {
                        if (mc.options.forwardKey.matchesMouse(mouseEvent)) {
                            l.w = !l.w;
                        } else if (mc.options.leftKey.matchesMouse(mouseEvent)) {
                            l.a = !l.a;
                        } else if (mc.options.backKey.matchesMouse(mouseEvent)) {
                            l.s = !l.s;
                        } else if (mc.options.rightKey.matchesMouse(mouseEvent)) {
                            l.d = !l.d;
                        } else if (mc.options.jumpKey.matchesMouse(mouseEvent)) {
                            l.jump = !l.jump;
                        } else if (mc.options.sprintKey.matchesMouse(mouseEvent)) {
                            l.sprint = !l.sprint;
                        } else if (mc.options.sneakKey.matchesMouse(mouseEvent)) {
                            l.sneak = !l.sneak;
                        }
                    }
                } else {
                    this.selectedIndex = index;

                }
                l.mouseClicked(index, mouseX, mouseY, mouseEvent);
                return true;
            }
            index++;
        }

        if (this.addRow.clicked(mouseX, mouseY, mouseEvent)) {
            try {
                if (!(this.selectedIndex > -1 && this.selectedIndex < this.macroLines.size())) {
                    this.macroLines.add(new MacroLine());
                    maxScroll = (int) Math.max(0, textRenderer.fontHeight * 2 * (double) macroLines.size() - (sizeY-20));
                    this.scroll = this.maxScroll;
                    this.selectedIndex = this.macroLines.size()-1;
                } else {
                    this.macroLines.add(selectedIndex+1, new MacroLine());
                }
                return true;
            } catch (Exception e) {e.printStackTrace();}
        } else if (this.duplicateRow.clicked(mouseX, mouseY, mouseEvent)) {
            try {
                MacroLine oldLine = this.macroLines.get(this.selectedIndex);
                MacroLine newLine = new MacroLine();

                newLine.w = oldLine.w;
                newLine.a = oldLine.a;
                newLine.s = oldLine.s;
                newLine.d = oldLine.d;
                newLine.jump = oldLine.jump;
                newLine.sprint = oldLine.sprint;
                newLine.sneak = oldLine.sneak;

                newLine.yawField.setText(oldLine.yawField.getText());
                newLine.pitchField.setText(oldLine.pitchField.getText());

                this.macroLines.add(selectedIndex, newLine);
                return true;
            } catch (Exception e) {}
        } else if (this.deleteRow.clicked(mouseX, mouseY, mouseEvent)) {
            try {
                this.macroLines.remove(selectedIndex);
            } catch (Exception e) {}
        } else if (this.loadFile.clicked(mouseX, mouseY, mouseEvent)) {
            CyvClientConfig.set("currentMacro", this.fileName.getText());
            mc.setScreen(new GuiMacro());
        }

        return false;

    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.scrollClicked) {
            int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
            int top = sr.getScaledHeight()/2-sizeY/2+4;
            int bottom = sr.getScaledHeight()/2+sizeY/2-4 - scrollbarHeight;

            scroll = (int) ((float) (mouseY - (sr.getScaledHeight()/2-this.sizeY/2) - scrollbarHeight/2) /(bottom - top) * maxScroll);

            if (scroll > maxScroll) scroll = maxScroll;
            if (scroll < 0) scroll = 0;

            return true;
        }

        return false;

    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (this.fileName.isFocused()) {
            this.fileName.charTyped(chr, modifiers);
            return true;
        }

        if (this.selectedIndex > -1 && this.selectedIndex < macroLines.size()) {
            MacroLine l = this.macroLines.get(this.selectedIndex);

            if (l.yawField.isFocused()) {
                l.yawField.charTyped(chr, modifiers);
                return true;
            }
            else if (l.pitchField.isFocused()) {
                l.pitchField.charTyped(chr, modifiers);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        super.keyPressed(keyCode, scanCode, modifiers);

        if (this.fileName.isFocused()) {
            this.fileName.keyPressed(keyCode, scanCode, modifiers);
            return true;
        }

        if (this.selectedIndex > -1 && this.selectedIndex < macroLines.size()) {
            MacroLine l = this.macroLines.get(this.selectedIndex);
            if (l.yawField.isFocused()) {
                l.yawField.keyPressed(keyCode, scanCode, modifiers);
                return true;
            }
            else if (l.pitchField.isFocused()) {
                l.pitchField.keyPressed(keyCode, scanCode, modifiers);
                return true;
            }

            if (mc.options.forwardKey.matchesKey(keyCode, keyCode)) {
                l.w = !l.w;
            } else if (mc.options.leftKey.matchesKey(keyCode, keyCode)) {
                l.a = !l.a;
            } else if (mc.options.backKey.matchesKey(keyCode, keyCode)) {
                l.s = !l.s;
            } else if (mc.options.rightKey.matchesKey(keyCode, keyCode)) {
                l.d = !l.d;
            } else if (mc.options.jumpKey.matchesKey(keyCode, keyCode)) {
                l.jump = !l.jump;
            } else if (mc.options.sprintKey.matchesKey(keyCode, keyCode)) {
                l.sprint = !l.sprint;
            } else if (mc.options.sneakKey.matchesKey(keyCode, keyCode)) {
                l.sneak = !l.sneak;
            }

        }

        return false;
    }

    @Override
    public void tick() {
        if (this.selectedIndex > -1 && this.selectedIndex < macroLines.size()) {
            MacroLine l = this.macroLines.get(this.selectedIndex);

            //l.yawField.updateCursorCounter();
            //l.pitchField.updateCursorCounter();
        }

        //this.fileName.updateCursorCounter();

        //smooth scrolling
        this.scroll += this.vScroll;
        this.vScroll *= 0.75;

        if (this.fileName.getText().length() < 1 || this.fileName.getText().length() > 32) this.loadFile.enabled = false;
        else if (this.fileName.getText().equals(CyvClientConfig.getString("currentMacro", "macro"))) this.loadFile.enabled = false;
        else this.loadFile.enabled = true;
    }

    @Override
    public void close() {
        //Keyboard.enableRepeatEvents(false);

        //save macro
        try {
            FileWriter fileWriter = new FileWriter(MacroFileInit.macroFile, false);
            Gson gson = new Gson();
            ArrayList<ArrayList<String>> macroList = new ArrayList<ArrayList<String>>();

            fileWriter.write("[" + System.getProperty("line.separator"));

            for (MacroLine line : this.macroLines) {
                ArrayList<String> macroString = new ArrayList<String>();
                macroString.add(line.w ? "true" : "false");
                macroString.add(line.a ? "true" : "false");
                macroString.add(line.s ? "true" : "false");
                macroString.add(line.d ? "true" : "false");
                macroString.add(line.jump ? "true" : "false");
                macroString.add(line.sprint ? "true" : "false");
                macroString.add(line.sneak ? "true" : "false");

                try {
                    macroString.add(Double.parseDouble(line.yawField.getText()) + "");
                } catch (NumberFormatException e) {
                    macroString.add("0.0");
                }

                try {
                    macroString.add(Double.parseDouble(line.pitchField.getText()) + "");
                } catch (NumberFormatException e) {
                    macroString.add("0.0");
                }

                macroList.add(macroString);
                fileWriter.write(gson.toJson(macroString) + (macroLines.indexOf(line) == macroLines.size()-1 ? "" : ",") + System.getProperty("line.separator"));
            }

            fileWriter.write("]");

            CommandMacro.macro = macroList;
            String json = gson.toJson(macroList.toArray());
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.close();
    }

    class MacroLine {
        int xStart = sr.getScaledWidth()/2 - sizeX/2 + 10;
        int width = sizeX/2 + 20;
        int height = textRenderer.fontHeight*2;

        public boolean w, a, s, d, jump, sprint, sneak;

        TextFieldWidget yawField, pitchField;

        public MacroLine() {
            this.yawField = new TextFieldWidget(textRenderer, 0, 0, 33, textRenderer.fontHeight, Text.empty());
            this.pitchField = new TextFieldWidget(textRenderer, 0, 0, 33, textRenderer.fontHeight, Text.empty());
            this.yawField.setDrawsBackground(false);
            this.pitchField.setDrawsBackground(false);

            this.yawField.setText("0.0");
            this.pitchField.setText("0.0");
        }

        public void drawEntry(DrawContext context, int slotIndex, int scroll, int mouseX, int mouseY, boolean isSelected, float partialTicks) {
            int yHeight = (slotIndex + 1) * height - scroll + (sr.getScaledHeight()/2 - sizeY/2);
            GuiUtils.drawRoundedRect(context, xStart, yHeight + 1,
                    xStart + width, yHeight + height - 1,
                    3, isSelected ? CyvFabric.theme.shade1 : CyvFabric.theme.shade2);

            StringBuilder string = new StringBuilder();
            if (w) string.append("W ");
            if (a) string.append("A ");
            if (s) string.append("S ");
            if (d) string.append("D ");
            if (jump) string.append("Jump ");
            if (sprint) string.append("Spr ");
            if (sneak) string.append("Snk ");

            context.drawTextWithShadow(textRenderer, string.toString(), xStart + 4, yHeight + height/3, 0xFFFFFFFF);

            this.yawField.setY(yHeight + textRenderer.fontHeight*3/4);
            this.pitchField.setY(yHeight + textRenderer.fontHeight*3/4);
            this.yawField.setX(sr.getScaledWidth()/2 - 42);
            this.pitchField.setX(sr.getScaledWidth()/2 - 5);

            GuiUtils.drawRoundedRect(context, sr.getScaledWidth()/2 - 46, yHeight + 2,
                    sr.getScaledWidth()/2 - 41+31, yHeight + height - 2,
                    2, CyvFabric.theme.highlight);
            GuiUtils.drawRoundedRect(context, sr.getScaledWidth()/2 - 8, yHeight + 2,
                    sr.getScaledWidth()/2 - 3+31, yHeight + height - 2,
                    2, CyvFabric.theme.highlight);

            if (!isSelected) {
                this.yawField.setFocused(false);
                this.pitchField.setFocused(false);
            }

            this.yawField.render(context, mouseX, mouseY, partialTicks);
            this.pitchField.render(context, mouseX, mouseY, partialTicks);

        }

        public boolean isPressed(int slotIndex, double mouseX, double mouseY, int mouseEvent) {
            float yHeight = (slotIndex + 1) * height - scroll + (sr.getScaledHeight()/2 - sizeY/2);
            if (mouseX > xStart && mouseX < xStart + width && mouseY > yHeight && mouseY < yHeight + height) {
                return true;
            }

            return false;
        }

        public void mouseClicked(int slotIndex, double mouseX, double mouseY, int mouseEvent) {
            float yHeight = (slotIndex + 1) * height - scroll + (sr.getScaledHeight()/2 - sizeY/2);
            if (!(mouseX > xStart && mouseX < xStart + width && mouseY > yHeight && mouseY < yHeight + height)) {
                return;
            }

            this.yawField.setFocused(this.yawField.mouseClicked(mouseX, mouseY, mouseEvent));
            this.pitchField.setFocused(this.pitchField.mouseClicked(mouseX, mouseY, mouseEvent));

        }

    }

    class SubButton {
        boolean enabled;
        String text;
        int x, y;
        int sizeX = 150;
        int sizeY = 15;

        SubButton(String text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }

        void draw(DrawContext context, int mouseX, int mouseY) {
            boolean mouseDown = (mouseX > x && mouseX < x + sizeX && mouseY > y && mouseY < y + sizeY);
            ColorTheme theme = CyvFabric.theme;
            GuiUtils.drawRoundedRect(context, x, y, x+sizeX, y+sizeY, 5, enabled ? (mouseDown ? theme.main1 : theme.main2) : theme.secondary1);
            context.drawCenteredTextWithShadow(textRenderer, this.text, x+sizeX/2, y+sizeY/2-textRenderer.fontHeight/2, 0xFFFFFFFF);
        }

        boolean clicked(double mouseX, double mouseY, int mouseButton) {
            if (!this.enabled) return false;
            return mouseX > x && mouseX < x + sizeX && mouseY > y && mouseY < y + sizeY && mouseButton == 0;
        }

    }
}
