package net.cyvfabric.gui;

import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.util.CyvGui;
import net.cyvfabric.util.parkour.LandingAxis;
import net.cyvfabric.util.parkour.LandingBlock;
import net.cyvfabric.util.parkour.LandingMode;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class GuiLb extends CyvGui {
    LandingBlock lb;
    ButtonWidget landingModeButton;
    ButtonWidget axisButton;
    ButtonWidget calculateWalls;
    ButtonWidget resetWalls;

    ButtonWidget bbToggle;
    ButtonWidget condToggle;

    public GuiLb(LandingBlock b) {
        super("Landing/Momentum Gui");
        this.lb = b;
    }

    @Override
    public void init() {
        if (lb == null) this.close();

        this.landingModeButton = ButtonWidget.builder(Text.of("Landing Mode: " + lb.mode.toString()), (widget) -> {
                    landingModeButtonPressed();
                }).dimensions(this.width - 155, 5, 150, 20)
                .build();

        this.axisButton = ButtonWidget.builder(Text.of("Axis: " + lb.axis.toString()), (widget) -> {
                    axisButtonPressed();
                }).dimensions(this.width - 155, 30, 150, 20)
                .build();

        this.calculateWalls = ButtonWidget.builder(Text.of("Calculate Walls"), (widget) -> {
                    lb.calculateWalls();
                }).dimensions(this.width - 155, 105, 150, 20)
                .build();

        this.resetWalls = ButtonWidget.builder(Text.of("Reset Walls"), (widget) -> {
                    lb.xMinWall = null;
                    lb.xMaxWall = null;
                    lb.zMinWall = null;
                    lb.zMaxWall = null;
                }).dimensions(this.width - 155, 130, 150, 20)
                .build();

        this.bbToggle = ButtonWidget.builder(Text.of("BB Visible: " + CyvClientConfig.getBoolean("highlightLanding", false)), (widget) -> {
                    CyvClientConfig.set("highlightLanding", !CyvClientConfig.getBoolean("highlightLanding", false));
                    bbToggle.setMessage(Text.of("BB Visible: " + CyvClientConfig.getBoolean("highlightLanding", false)));
                }).dimensions(this.width - 155, 55, 150, 20)
                .build();

        this.condToggle = ButtonWidget.builder(Text.of("Cond Visible: " + CyvClientConfig.getBoolean("highlightLandingCond", false)), (widget) -> {
                    CyvClientConfig.set("highlightLandingCond", !CyvClientConfig.getBoolean("highlightLandingCond", false));
                    bbToggle.setMessage(Text.of("Cond Visible: " + CyvClientConfig.getBoolean("highlightLandingCond", false)));
                }).dimensions(this.width - 155, 80, 150, 20)
                .build();

        if (lb.axis.equals(LandingAxis.both)) {
            lb.axis = LandingAxis.both;
            this.axisButton.setMessage(Text.of("Axis: Both"));
        } else if (lb.axis.equals(LandingAxis.z)) {
            lb.axis = LandingAxis.z;
            this.axisButton.setMessage(Text.of("Axis: Z"));
        } else {
            lb.axis = LandingAxis.x;
            this.axisButton.setMessage(Text.of("Axis: X"));
        }

        if (lb.mode.equals(LandingMode.landing)) {
            this.landingModeButton.setMessage(Text.of("Landing Mode: Landing"));
        } else if (lb.mode.equals(LandingMode.hit)) {
            this.landingModeButton.setMessage(Text.of("Landing Mode: Hit"));
        } else if (lb.mode.equals(LandingMode.z_neo)) {
            this.landingModeButton.setMessage(Text.of("Landing Mode: Z Neo"));
        } else {
            this.landingModeButton.setMessage(Text.of("Landing Mode: Enter"));
        }

    }

    void landingModeButtonPressed() {
        LandingMode mode = lb.mode;
        if (mode.equals(LandingMode.landing)) {
            lb.mode = LandingMode.hit;
            this.landingModeButton.setMessage(Text.of("Landing Mode: Hit"));
        } else if (mode.equals(LandingMode.hit)) {
            lb.mode = LandingMode.z_neo;
            this.landingModeButton.setMessage(Text.of("Landing Mode: Z Neo"));
        } else if (mode.equals(LandingMode.z_neo)) {
            lb.mode = LandingMode.enter;
            this.landingModeButton.setMessage(Text.of("Landing Mode: Enter"));
        } else {
            lb.mode = LandingMode.landing;
            this.landingModeButton.setMessage(Text.of("Landing Mode: Landing"));
        }
    }

    void axisButtonPressed() {
        LandingAxis mode = lb.axis;
        if (mode.equals(LandingAxis.both)) {
            lb.axis = LandingAxis.z;
            this.axisButton.setMessage(Text.of("Axis: Z"));
        } else if (mode.equals(LandingAxis.z)) {
            lb.axis = LandingAxis.x;
            this.axisButton.setMessage(Text.of("Axis: X"));
        } else {
            lb.axis = LandingAxis.both;
            this.axisButton.setMessage(Text.of("Axis: Both"));
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
        this.renderInGameBackground(context);

        landingModeButton.render(context, mouseX, mouseY, partialTicks);
        axisButton.render(context, mouseX, mouseY, partialTicks);
        calculateWalls.render(context, mouseX, mouseY, partialTicks);
        resetWalls.render(context, mouseX, mouseY, partialTicks);

        bbToggle.render(context, mouseX, mouseY, partialTicks);
        condToggle.render(context, mouseX, mouseY, partialTicks);

    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        landingModeButton.mouseClicked(click, doubled);
        axisButton.mouseClicked(click, doubled);
        calculateWalls.mouseClicked(click, doubled);
        resetWalls.mouseClicked(click, doubled);
        bbToggle.mouseClicked(click, doubled);
        condToggle.mouseClicked(click, doubled);

        return super.mouseClicked(click, doubled);
    }

    @Override
    public void tick() {
        if (lb == null) this.close();
    }

}
