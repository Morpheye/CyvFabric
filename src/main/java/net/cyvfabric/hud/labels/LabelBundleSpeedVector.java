package net.cyvfabric.hud.labels;

import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.LabelFormat;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.cyvfabric.util.math.Vec2;
import net.cyvfabric.util.math.Vec3;

public class LabelBundleSpeedVector extends LabelBundle {
    public LabelBundleSpeedVector() {
        this.labels.add(new DraggableHUDLabel<>(
                "labelIndividualSpeeds",
                "Speeds",
                false,
                new ScreenPosition(0, 214),
                () -> new Vec3(
                        ParkourTickListener.vx,
                        ParkourTickListener.vy,
                        ParkourTickListener.vz
                ),
                LabelFormat.VECTOR_3D
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelSpeedVector",
                "Speed Vector",
                false,
                new ScreenPosition(0, 223),
                () -> new Vec2(ParkourTickListener.vx, ParkourTickListener.vz),
                LabelFormat.ANGLED_VECTOR
        ));
    }
}