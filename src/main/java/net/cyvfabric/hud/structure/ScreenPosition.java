package net.cyvfabric.hud.structure;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;

public class ScreenPosition {
    private static final Minecraft mc = Minecraft.getInstance();
    private int x, y;

    public ScreenPosition(double x, double y) {
        setRelative(x, y);
    }

    public ScreenPosition(int x, int y) {
        setAbsolute(x, y);
    }

    public static ScreenPosition fromRelativePosition(double x, double y) {
        return new ScreenPosition(x, y);
    }

    public static ScreenPosition fromAbsolutePosition(int x, int y) {
        return new ScreenPosition(x, y);
    }

    public int getAbsoluteX() {
        return x;
    }

    public int getAbsoluteY() {
        return y;
    }

    public double getRelativeX() {
        return (double) this.x / mc.getWindow().getGuiScaledWidth();
    }

    public double getRelativeY() {
        return (double) this.y / mc.getWindow().getGuiScaledHeight();
    }

    public void setRelative(double x, double y) {
        Window res = mc.getWindow();
        this.x = (int) (x * res.getGuiScaledWidth());
        this.y = (int) (y * res.getGuiScaledHeight());
    }

    public void setAbsolute(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
