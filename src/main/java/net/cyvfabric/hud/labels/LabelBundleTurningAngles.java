package net.cyvfabric.hud.labels;

import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.LabelFormat;
import net.cyvfabric.hud.structure.ScreenPosition;

public class LabelBundleTurningAngles extends LabelBundle {
    public LabelBundleTurningAngles() {
        this.labels.add(new DraggableHUDLabel<>(
                "labelJumpAngle",
                "Jump Angle",
                true,
                new ScreenPosition(0, 103),
                () -> ParkourTickListener.jf,
                LabelFormat.WRAPPED_ANGLE
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelSecondTurn",
                "Second Turn",
                true,
                new ScreenPosition(0, 112),
                () -> ParkourTickListener.sf - ParkourTickListener.jf,
                LabelFormat.ANGLE
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelPreturn",
                "Preturn",
                true,
                new ScreenPosition(0, 121),
                () -> ParkourTickListener.pf,
                LabelFormat.ANGLE
        ));
    }
}
