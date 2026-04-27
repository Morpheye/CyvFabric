package net.cyvfabric.hud.structure;

import mcpk.utils.MathHelper;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.util.math.Vec2;
import net.cyvfabric.util.math.Vec3;

import java.util.function.Function;

public final class LabelFormat<T> {
    public static final LabelFormat<Integer> INTEGER = INTEGER(0);
    public static final LabelFormat<Number> NUMBER = new LabelFormat<>(0, CyvFabric.df::format);
    public static final LabelFormat<Float> ANGLE = new LabelFormat<>(0.0F, f -> NUMBER.format(f) + "°");
    public static final LabelFormat<Float> WRAPPED_ANGLE = new LabelFormat<>(0.0F, f -> ANGLE.format(MathHelper.wrapDegrees(f)));
    public static final LabelFormat<Vec2> ANGLED_VECTOR = new LabelFormat<>(
            new Vec2(0.0D, 0.0D),
            v -> NUMBER.format(v.hypot())
                    + "/" + WRAPPED_ANGLE.format(v.angle())
    );
    public static final LabelFormat<Vec3> VECTOR_3D = new LabelFormat<>(
            new Vec3(0.0D, 0.0D, 0.0D),
            v -> NUMBER.format(v.x())
                    + "/" + NUMBER.format(v.y())
                    + "/" + NUMBER.format(v.z())
    );

    public static LabelFormat<String> TEXT(String defaultValue) {
        return new LabelFormat<>(defaultValue, s -> s);
    }
    public static LabelFormat<Integer> INTEGER(int defaultValue) {
        return new LabelFormat<>(defaultValue, String::valueOf);
    }

    public final T dummyValue;
    private final Function<T, String> formatFunction;

    private LabelFormat(T dummyValue, Function<T, String> formatFunction) {
        this.dummyValue = dummyValue;
        this.formatFunction = formatFunction;
    }

    public String format(T value) {
        return formatFunction.apply(value);
    }
}
