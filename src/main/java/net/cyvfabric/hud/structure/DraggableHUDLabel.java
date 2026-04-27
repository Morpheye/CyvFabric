package net.cyvfabric.hud.structure;

import net.cyvfabric.config.CyvClientColorHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.function.Supplier;

public class DraggableHUDLabel<T> extends DraggableHUDElement {
    public static final int HEIGHT = 9;

    private final String prefix;

    private final Supplier<T> valueSupplier;
    private final LabelFormat<T> labelFormat;

    public DraggableHUDLabel(
            String name,
            String displayName,
            boolean enabledByDefault,
            ScreenPosition defaultPosition,
            Supplier<T> valueSupplier,
            LabelFormat<T> labelFormat
    ) {
        this(name, displayName, displayName, enabledByDefault, defaultPosition, valueSupplier, labelFormat);
    }

    public DraggableHUDLabel(
            String name,
            String displayName,
            String prefix,
            boolean enabledByDefault,
            ScreenPosition defaultPosition,
            Supplier<T> valueSupplier,
            LabelFormat<T> labelFormat
    ) {
        super(name, displayName, enabledByDefault, defaultPosition);

        this.prefix = prefix;

        this.valueSupplier = valueSupplier;
        this.labelFormat = labelFormat;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, ScreenPosition pos) {
        renderForValue(context, pos, valueSupplier.get());
    }
    @Override
    public void renderDummy(GuiGraphicsExtractor context, ScreenPosition pos) {
        renderForValue(context, pos, labelFormat.dummyValue);
    }

    protected void renderForValue(GuiGraphicsExtractor context, ScreenPosition pos, T value) {
        if (!this.isVisible) return;
        long color1 = CyvClientColorHelper.color1.drawColor;
        long color2 = CyvClientColorHelper.color2.drawColor;
        Font font = mc.font;

        final String prefixColon = getPrefix() + ": ";
        final String formattedValue = labelFormat.format(value);

        text(context, prefixColon, pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, color1);
        text(context, formattedValue, pos.getAbsoluteX() + 1 + font.width(prefixColon)
                , pos.getAbsoluteY() + 1, color2);
    }

    public String getPrefix() {
        return prefix;
    }

    protected String getFullString(T value) {
        return getPrefix() + ": " + labelFormat.format(value);
    }

    @Override
    public int getWidth() {
        return mc.font.width(
                getFullString(labelFormat.dummyValue)
        );
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
}
