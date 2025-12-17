package net.cyvfabric.gui;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mojang.blaze3d.platform.Window;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.command.mpk.CommandMacro;
import net.cyvfabric.config.ColorTheme;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.event.MacroFileInit;
import net.cyvfabric.util.CyvGui;
import net.cyvfabric.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class GuiMacro extends CyvGui {
    Minecraft mc = Minecraft.getInstance();
    Window sr = mc.getWindow();
    int sizeX = sr.getGuiScaledWidth()*7/8;
    int sizeY = sr.getGuiScaledHeight()*7/8;

    SubButton addRow;
    SubButton duplicateRow;
    SubButton deleteRow;

    EditBox fileName;
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
    public void resize(Minecraft mcIn, int w, int h) {
        onClose();
    }

    @Override
    public void init() { //initialize the macro
        ArrayList<ArrayList<String>> macro;
        this.macroLines = new ArrayList<MacroLine>();

        //Keyboard.enableRepeatEvents(true);

        this.sizeX = sr.getGuiScaledWidth()*7/8;
        this.sizeY = sr.getGuiScaledHeight()*7/8;

        this.addRow = new SubButton("Add Row", (sr.getGuiScaledWidth() + sizeY +30)/2 - 75, sr.getGuiScaledHeight()/2 - sizeY/2 + 40);
        this.duplicateRow = new SubButton("Duplicate Row", (sr.getGuiScaledWidth() + sizeY +30)/2 - 75, sr.getGuiScaledHeight()/2 - sizeY/2 + 60);
        this.deleteRow = new SubButton("Delete Row", (sr.getGuiScaledWidth() + sizeY +30)/2 - 75, sr.getGuiScaledHeight()/2 - sizeY/2 + 80);

        this.fileName = new EditBox(mc.font, (sr.getGuiScaledWidth() + sizeY +30)/2 - 72,
                sr.getGuiScaledHeight()/2 - sizeY/2+mc.font.lineHeight/2 + 20, 90, mc.font.lineHeight*2,
                Component.empty());
        fileName.setBordered(false);
        fileName.setValue(CyvClientConfig.getString("currentMacro", "macro"));
        this.loadFile = new SubButton("Load", (sr.getGuiScaledWidth() + sizeY +30)/2 + 25, sr.getGuiScaledHeight()/2 - sizeY/2 + 20);
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

                        macroLine.yawField.setValue(""+Double.valueOf(line.get(7)));
                        macroLine.pitchField.setValue(""+Double.valueOf(line.get(8)));

                        macroLines.add(macroLine);

                    } catch (Exception e) {}
                }
            } catch (Exception e) {}

        }

        maxScroll = (int) Math.max(0, font.lineHeight * 2 * Math.ceil(macroLines.size()) - (sizeY-20));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float partialTicks) {
        super.renderTransparentBackground(context);

        maxScroll = (int) Math.max(0, font.lineHeight * 2 * Math.ceil(macroLines.size()) - (sizeY-20));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;

        GuiUtils.drawRoundedRect(context, sr.getGuiScaledWidth()/2 - sizeX/2 - 15, sr.getGuiScaledHeight()/2 - sizeY/2 - 4,
                sr.getGuiScaledWidth()/2 + sizeX/2 + 14, sr.getGuiScaledHeight()/2 + sizeY/2 + 4, 5, CyvFabric.theme.background1);

        this.addRow.draw(context, mouseX, mouseY);
        this.duplicateRow.draw(context, mouseX, mouseY);
        this.deleteRow.draw(context, mouseX, mouseY);

        GuiUtils.drawRoundedRect(context, (sr.getGuiScaledWidth() + sizeY +30)/2 - 75, sr.getGuiScaledHeight()/2 - sizeY/2 + 20,
                (sr.getGuiScaledWidth() + sizeY +30)/2 + 20, sr.getGuiScaledHeight()/2 - sizeY/2 + 20 + font.lineHeight*7/4,
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


        context.drawString(font, "Inputs:", sr.getGuiScaledWidth()/2 - sizeX/2 + 13, 5 + sr.getGuiScaledHeight()/2 - sizeY/2, 0xFFFFFFFF);
        context.drawString(font, "Yaw:", sr.getGuiScaledWidth()/2 - 34, 5 + sr.getGuiScaledHeight()/2 - sizeY/2, 0xFFFFFFFF);
        context.drawString(font, "Pitch:", sr.getGuiScaledWidth()/2 - 1, 5 + sr.getGuiScaledHeight()/2 - sizeY/2, 0xFFFFFFFF);

        context.enableScissor(sr.getGuiScaledWidth()/2 - ((sizeX/2 + 20)),
                sr.getGuiScaledHeight()/2 - (sizeY/2) + 3 + (font.lineHeight * 2),
                sizeX, sr.getGuiScaledHeight()/2 + sizeY/2);

        int index = 0;
        for (MacroLine l : macroLines) {
            int yHeight = (int) ((index + 1) * font.lineHeight*2 - scroll + (sr.getGuiScaledHeight()/2 - sizeY/2));
            context.drawString(font, ""+(index+1), sr.getGuiScaledWidth()/2 - sizeX/2 - 10,
                    yHeight + font.lineHeight*2/3, 0xFFFFFFFF);
            l.drawEntry(context, index, (int) scroll, mouseX, mouseY, index == this.selectedIndex, partialTicks);
            index++;
        }

        context.disableScissor();

        //draw scrollbar
        int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
        if (scroll > maxScroll) scroll = maxScroll;
        if (scroll < 0) scroll = 0;

        int top = sr.getGuiScaledHeight()/2-sizeY/2+4;
        int bottom = sr.getGuiScaledHeight()/2+sizeY/2-4 - scrollbarHeight;
        int amount = (int) (top + (bottom - top) * ((float) scroll/maxScroll));

        if (maxScroll == 0) amount = top;

        //color
        int color = CyvFabric.theme.border2;
        if (mouseX > sr.getGuiScaledWidth()/2+sizeX/2+2 && mouseX < sr.getGuiScaledWidth()/2+sizeX/2+8 &&
                mouseY > amount && mouseY < amount+scrollbarHeight) {
            color = CyvFabric.theme.border1;
        }

        GuiUtils.drawRoundedRect(context, sr.getGuiScaledWidth()/2+sizeX/2+2, amount,
                sr.getGuiScaledWidth()/2+sizeX/2+8, amount+scrollbarHeight, 3, color);

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
        int top = sr.getGuiScaledHeight()/2-sizeY/2+4;
        int bottom = sr.getGuiScaledHeight()/2+sizeY/2-4 - scrollbarHeight;
        int amount = (int) (top + (bottom - top) * ((float) scroll/maxScroll));

        if (mouseX > sr.getGuiScaledWidth()/2+sizeX/2+2 && mouseX < sr.getGuiScaledWidth()/2+sizeX/2+8 &&
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
    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        super.mouseClicked(click, doubled);

        int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
        int top = sr.getGuiScaledHeight()/2-sizeY/2+4;
        int bottom = sr.getGuiScaledHeight()/2+sizeY/2-4 - scrollbarHeight;
        int amount = (int) (top + (bottom - top) * ((float) scroll/maxScroll));

        if (click.x() > sr.getGuiScaledWidth()/2+sizeX/2+2 && click.x() < sr.getGuiScaledWidth()/2+sizeX/2+8 &&
                click.y() > amount && click.y() < amount+scrollbarHeight) {
            this.scrollClicked = true;
            return true;
        } else {
            this.scrollClicked = false;
        }

        if (this.fileName.mouseClicked(click, doubled)) {
            this.fileName.setFocused(true);
            if (this.selectedIndex > -1 && macroLines.get(selectedIndex) != null) {
                macroLines.get(selectedIndex).yawField.setFocused(false);
                macroLines.get(selectedIndex).pitchField.setFocused(false);
            }
            return true;
        } else this.fileName.setFocused(false);

        int index=0;
        for (MacroLine l : macroLines) {
            if (l.isPressed(index, click.x(), click.y(), click.button())) {

                if (this.selectedIndex == index) {
                    if (!l.pitchField.isFocused() && !l.yawField.isFocused()) {
                        if (mc.options.keyUp.matchesMouse(click)) {
                            l.w = !l.w;
                        } else if (mc.options.keyLeft.matchesMouse(click)) {
                            l.a = !l.a;
                        } else if (mc.options.keyDown.matchesMouse(click)) {
                            l.s = !l.s;
                        } else if (mc.options.keyRight.matchesMouse(click)) {
                            l.d = !l.d;
                        } else if (mc.options.keyJump.matchesMouse(click)) {
                            l.jump = !l.jump;
                        } else if (mc.options.keySprint.matchesMouse(click)) {
                            l.sprint = !l.sprint;
                        } else if (mc.options.keyShift.matchesMouse(click)) {
                            l.sneak = !l.sneak;
                        }
                    }
                } else {
                    this.selectedIndex = index;
                }
                l.mouseClicked(index, click, doubled);
                return true;
            }
            index++;
        }

        if (this.addRow.clicked(click)) {
            try {
                if (!(this.selectedIndex > -1 && this.selectedIndex < this.macroLines.size())) {
                    this.macroLines.add(new MacroLine());
                    maxScroll = (int) Math.max(0, font.lineHeight * 2 * (double) macroLines.size() - (sizeY-20));
                    this.scroll = this.maxScroll;
                    this.selectedIndex = this.macroLines.size()-1;
                } else {
                    this.macroLines.add(selectedIndex+1, new MacroLine());
                }
                return true;
            } catch (Exception e) {e.printStackTrace();}
        } else if (this.duplicateRow.clicked(click)) {
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

                newLine.yawField.setValue(oldLine.yawField.getValue());
                newLine.pitchField.setValue(oldLine.pitchField.getValue());

                this.macroLines.add(selectedIndex, newLine);
                return true;
            } catch (Exception e) {}
        } else if (this.deleteRow.clicked(click)) {
            try {
                this.macroLines.remove(selectedIndex);
            } catch (Exception e) {}
        } else if (this.loadFile.clicked(click)) {
            CyvClientConfig.set("currentMacro", this.fileName.getValue());
            mc.setScreen(new GuiMacro());
        }

        return false;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent click, double offsetX, double offsetY) {
        if (this.scrollClicked) {
            int scrollbarHeight = (int) ((sizeY - 8)/(0.01*maxScroll+1));
            int top = sr.getGuiScaledHeight()/2-sizeY/2+4;
            int bottom = sr.getGuiScaledHeight()/2+sizeY/2-4 - scrollbarHeight;

            scroll = (int) ((float) (click.y() - (sr.getGuiScaledHeight()/2-this.sizeY/2) - scrollbarHeight/2) / (bottom - top) * maxScroll);

            if (scroll > maxScroll) scroll = maxScroll;
            if (scroll < 0) scroll = 0;

            return true;
        }

        return false;
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        if (this.fileName.isFocused()) {
            this.fileName.charTyped(input);
            return true;
        }

        if (this.selectedIndex > -1 && this.selectedIndex < macroLines.size()) {
            MacroLine l = this.macroLines.get(this.selectedIndex);

            if (l.yawField.isFocused()) {
                l.yawField.charTyped(input);
                return true;
            }
            else if (l.pitchField.isFocused()) {
                l.pitchField.charTyped(input);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean keyPressed(KeyEvent input) {
        super.keyPressed(input);

        if (this.fileName.isFocused()) {
            this.fileName.keyPressed(input);
            return true;
        }

        if (this.selectedIndex > -1 && this.selectedIndex < macroLines.size()) {
            MacroLine l = this.macroLines.get(this.selectedIndex);
            if (l.yawField.isFocused()) {
                l.yawField.keyPressed(input);
                return true;
            }
            else if (l.pitchField.isFocused()) {
                l.pitchField.keyPressed(input);
                return true;
            }

            if (mc.options.keyUp.matches(input)) {
                l.w = !l.w;
            } else if (mc.options.keyLeft.matches(input)) {
                l.a = !l.a;
            } else if (mc.options.keyDown.matches(input)) {
                l.s = !l.s;
            } else if (mc.options.keyRight.matches(input)) {
                l.d = !l.d;
            } else if (mc.options.keyJump.matches(input)) {
                l.jump = !l.jump;
            } else if (mc.options.keySprint.matches(input)) {
                l.sprint = !l.sprint;
            } else if (mc.options.keyShift.matches(input)) {
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

        if (this.fileName.getValue().length() < 1 || this.fileName.getValue().length() > 32) this.loadFile.enabled = false;
        else if (this.fileName.getValue().equals(CyvClientConfig.getString("currentMacro", "macro"))) this.loadFile.enabled = false;
        else this.loadFile.enabled = true;
    }

    @Override
    public void onClose() {
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
                    macroString.add(Double.parseDouble(line.yawField.getValue()) + "");
                } catch (NumberFormatException e) {
                    macroString.add("0.0");
                }

                try {
                    macroString.add(Double.parseDouble(line.pitchField.getValue()) + "");
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

        super.onClose();
    }

    class MacroLine {
        int xStart = sr.getGuiScaledWidth()/2 - sizeX/2 + 10;
        int width = sizeX/2 + 20;
        int height = font.lineHeight*2;

        public boolean w, a, s, d, jump, sprint, sneak;

        EditBox yawField, pitchField;

        public MacroLine() {
            this.yawField = new EditBox(font, 0, 0, 33, font.lineHeight, Component.empty());
            this.pitchField = new EditBox(font, 0, 0, 33, font.lineHeight, Component.empty());
            this.yawField.setBordered(false);
            this.pitchField.setBordered(false);

            this.yawField.setValue("0.0");
            this.pitchField.setValue("0.0");
        }

        public void drawEntry(GuiGraphics context, int slotIndex, int scroll, int mouseX, int mouseY, boolean isSelected, float partialTicks) {
            int yHeight = (slotIndex + 1) * height - scroll + (sr.getGuiScaledHeight()/2 - sizeY/2);
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

            context.drawString(font, string.toString(), xStart + 4, yHeight + height/3, 0xFFFFFFFF);

            this.yawField.setY(yHeight + font.lineHeight*3/4);
            this.pitchField.setY(yHeight + font.lineHeight*3/4);
            this.yawField.setX(sr.getGuiScaledWidth()/2 - 42);
            this.pitchField.setX(sr.getGuiScaledWidth()/2 - 5);

            GuiUtils.drawRoundedRect(context, sr.getGuiScaledWidth()/2 - 46, yHeight + 2,
                    sr.getGuiScaledWidth()/2 - 41+31, yHeight + height - 2,
                    2, CyvFabric.theme.highlight);
            GuiUtils.drawRoundedRect(context, sr.getGuiScaledWidth()/2 - 8, yHeight + 2,
                    sr.getGuiScaledWidth()/2 - 3+31, yHeight + height - 2,
                    2, CyvFabric.theme.highlight);

            if (!isSelected) {
                this.yawField.setFocused(false);
                this.pitchField.setFocused(false);
            }

            this.yawField.render(context, mouseX, mouseY, partialTicks);
            this.pitchField.render(context, mouseX, mouseY, partialTicks);

        }

        public boolean isPressed(int slotIndex, double mouseX, double mouseY, int mouseEvent) {
            float yHeight = (slotIndex + 1) * height - scroll + (sr.getGuiScaledHeight()/2 - sizeY/2);
            if (mouseX > xStart && mouseX < xStart + width && mouseY > yHeight && mouseY < yHeight + height) {
                return true;
            }

            return false;
        }

        public void mouseClicked(int slotIndex, MouseButtonEvent click, boolean doubled) {
            float yHeight = (slotIndex + 1) * height - scroll + (sr.getGuiScaledHeight()/2 - sizeY/2);
            if (!(click.x() > xStart && click.x() < xStart + width && click.y() > yHeight && click.y() < yHeight + height)) {
                return;
            }

            this.yawField.setFocused(this.yawField.mouseClicked(click, doubled));
            this.pitchField.setFocused(this.pitchField.mouseClicked(click, doubled));
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

        void draw(GuiGraphics context, int mouseX, int mouseY) {
            boolean mouseDown = (mouseX > x && mouseX < x + sizeX && mouseY > y && mouseY < y + sizeY);
            ColorTheme theme = CyvFabric.theme;
            GuiUtils.drawRoundedRect(context, x, y, x+sizeX, y+sizeY, 5, enabled ? (mouseDown ? theme.main1 : theme.main2) : theme.secondary1);
            context.drawCenteredString(font, this.text, x+sizeX/2, y+sizeY/2-font.lineHeight/2, 0xFFFFFFFF);
        }

        boolean clicked(MouseButtonEvent click) {
            if (!this.enabled) return false;
            return click.x() > x && click.x() < x + sizeX && click.y() > y && click.y() < y + sizeY && click.button() == 0;
        }
    }
}
