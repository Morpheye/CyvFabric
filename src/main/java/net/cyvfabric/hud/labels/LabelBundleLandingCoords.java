package net.cyvfabric.hud.labels;

import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.LabelFormat;
import net.cyvfabric.hud.structure.ScreenPosition;

public class LabelBundleLandingCoords extends LabelBundle {
    public LabelBundleLandingCoords() {
        this.labels.add(new DraggableHUDLabel<>(
                "labelLandingX",
                "Last Landing X",
                true,
                new ScreenPosition(0, 75),
                () -> ParkourTickListener.lx,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelLandingY",
                "Last Landing Y",
                true,
                new ScreenPosition(0, 84),
                () -> ParkourTickListener.ly,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelLandingZ",
                "Last Landing Z",
                true,
                new ScreenPosition(0, 93),
                () -> ParkourTickListener.lz,
                LabelFormat.NUMBER
        ));
    }
}
