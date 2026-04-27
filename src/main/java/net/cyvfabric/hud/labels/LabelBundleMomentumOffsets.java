package net.cyvfabric.hud.labels;

import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.LabelFormat;
import net.cyvfabric.hud.structure.ScreenPosition;

public class LabelBundleMomentumOffsets extends LabelBundle {
    public LabelBundleMomentumOffsets() {
        this.labels.add(new DraggableHUDLabel<>(
                "labelMMXOffset",
                "MM X Offset",
                false,
                new ScreenPosition(177, 110),
                () -> ParkourTickListener.momentumBlock == null || ParkourTickListener.momentumBlock.lastOffsetX == null
                        ? Double.NaN
                        : ParkourTickListener.momentumBlock.lastOffsetX,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelMMZOffset",
                "MM Z Offset",
                false,
                new ScreenPosition(177, 119),
                () -> ParkourTickListener.momentumBlock == null || ParkourTickListener.momentumBlock.lastOffsetZ == null
                        ? Double.NaN
                        : ParkourTickListener.momentumBlock.lastOffsetZ,
                LabelFormat.NUMBER
        ));
    }
}
