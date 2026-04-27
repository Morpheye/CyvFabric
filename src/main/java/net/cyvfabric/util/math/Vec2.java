package net.cyvfabric.util.math;

public record Vec2(double x, double y) {
    public double hypot() {
        return Math.hypot(x, y);
    }

    public float angle() {
        return (float) Math.toDegrees(Math.atan2(-x, y));
    }
}
