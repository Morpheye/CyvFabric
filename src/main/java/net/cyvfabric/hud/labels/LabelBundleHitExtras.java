package net.cyvfabric.hud.labels;

import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.LabelFormat;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.cyvfabric.util.math.Vec2;

public class LabelBundleHitExtras extends LabelBundle {
    public LabelBundleHitExtras() {
        this.labels.add(new DraggableHUDLabel<>(
                "labelHitAngle",
                "Hit Angle",
                true,
                new ScreenPosition(177, 146),
                () -> ParkourTickListener.hf,
                LabelFormat.WRAPPED_ANGLE
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelHitVector",
                "Hit Vector",
                true,
                new ScreenPosition(177, 146),
                () -> new Vec2(ParkourTickListener.hvx, ParkourTickListener.hvz),
                LabelFormat.ANGLED_VECTOR
        ));
    }
}
