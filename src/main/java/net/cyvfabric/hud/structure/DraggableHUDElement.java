package net.cyvfabric.hud.structure;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import java.util.LinkedHashMap;

public abstract class DraggableHUDElement implements IRenderer {
    protected final Minecraft mc;

    public ScreenPosition position;
    public boolean isVisible = true;
    public boolean isDraggable = true;
    public boolean isEnabled = true;

    public DraggableHUDElement() {
        this.mc = Minecraft.getInstance();

        setEnabled(isEnabled);
        this.position = this.getDefaultPosition();
    }

    public final int getLineOffset(ScreenPosition pos, int lineNum) {
        return pos.getAbsoluteY() + getLineOffset(lineNum);
    }

    private int getLineOffset(int lineNum) {
        return (Minecraft.getInstance().font.lineHeight + 3) * lineNum;
    }

    public abstract ScreenPosition getDefaultPosition();

    public LinkedHashMap<String, CyvClientConfig.ConfigValue<?>> getConfigFields() {
        LinkedHashMap<String, CyvClientConfig.ConfigValue<?>> fields = new LinkedHashMap<String, CyvClientConfig.ConfigValue<?>>();

        fields.put("enabled", new CyvClientConfig.ConfigValue<Boolean>(this.enabledByDefault()));
        fields.put("posx", new CyvClientConfig.ConfigValue<Integer>(this.getDefaultPosition().getAbsoluteX()));
        fields.put("posy", new CyvClientConfig.ConfigValue<Integer>(this.getDefaultPosition().getAbsoluteY()));
        fields.put("visible", new CyvClientConfig.ConfigValue<Boolean>(true));

        return fields;
    }

    public void readConfigFields() {
        try {
            this.isEnabled = CyvClientConfig.getBoolean(this.getName()+"_enabled", this.isEnabled);
            int x = CyvClientConfig.getInt(this.getName()+"_posx", this.getDefaultPosition().getAbsoluteX());
            int y = CyvClientConfig.getInt(this.getName()+"_posy", this.getDefaultPosition().getAbsoluteY());
            this.isVisible = CyvClientConfig.getBoolean(this.getName()+"_visible", this.isVisible);

            this.position = new ScreenPosition(x, y);

        } catch (Exception e) {
            CyvFabric.LOGGER.error(String.valueOf(e));
            this.position = this.getDefaultPosition();
        }
    }

    public void saveConfigFields() {
        CyvClientConfig.set(this.getName()+"_enabled", this.isEnabled);
        CyvClientConfig.set(this.getName()+"_visible", this.isVisible);
        CyvClientConfig.set(this.getName()+"_posx", this.position.getAbsoluteX());
        CyvClientConfig.set(this.getName()+"_posy", this.position.getAbsoluteY());

        return;

    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void save(ScreenPosition pos) {
        this.position = pos;
    }

    public ScreenPosition load() {
        return position;
    }

    public abstract String getName();

    public abstract String getDisplayName();

    public boolean enabledByDefault() {
        return true;
    }

    protected void drawString(GuiGraphics context, Object string, int x, int y, long color) {
        this.drawString(context, string, x, y, color, true);
    }

    protected void drawString(GuiGraphics context, Object string, int x, int y, long color, boolean shadow) {
        long drawColor = (this.isVisible) ? color : 0xFFAAAAAA;
        context.drawString(mc.font, string.toString(), x, y, ((Long) drawColor).intValue(), shadow);
    }

}
