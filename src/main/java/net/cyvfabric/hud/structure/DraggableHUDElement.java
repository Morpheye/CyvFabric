package net.cyvfabric.hud.structure;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.config.CyvClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.LinkedHashMap;

public abstract class DraggableHUDElement implements IRenderer {
    protected final Minecraft mc = Minecraft.getInstance();

    private final String name;
    private final String displayName;
    private final boolean enabledByDefault;
    private final ScreenPosition defaultPosition;

    public ScreenPosition position;
    public boolean isVisible = true;
    public boolean isDraggable = true;
    public boolean isEnabled = true;

    public DraggableHUDElement(String name, String displayName, boolean enabledByDefault, ScreenPosition defaultPosition) {
        this.name = name;
        this.displayName = displayName;
        this.enabledByDefault = enabledByDefault;
        this.defaultPosition = defaultPosition;

        setEnabled(isEnabled);
        this.position = defaultPosition;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean enabledByDefault() {
        return enabledByDefault;
    }

    public ScreenPosition getDefaultPosition() {
        return defaultPosition;
    }

    public final int getLineOffset(ScreenPosition pos, int lineNum) {
        return pos.getAbsoluteY() + getLineOffset(lineNum);
    }

    private int getLineOffset(int lineNum) {
        return (Minecraft.getInstance().font.lineHeight + 3) * lineNum;
    }

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

    protected void text(GuiGraphicsExtractor context, Object string, int x, int y, long color) {
        this.text(context, string, x, y, color, true);
    }

    protected void text(GuiGraphicsExtractor context, Object string, int x, int y, long color, boolean shadow) {
        long drawColor = (this.isVisible) ? color : 0xFFAAAAAA;
        context.text(mc.font, string.toString(), x, y, ((Long) drawColor).intValue(), shadow);
    }
}
