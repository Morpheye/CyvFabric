package net.cyvfabric.hud.labels;

import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.LabelFormat;
import net.cyvfabric.hud.structure.ScreenPosition;

public class LabelBundleSpeeds extends LabelBundle {
    public LabelBundleSpeeds() {
        this.labels.add(new DraggableHUDLabel<>(
                "labelXSpeed",
                "X Speed",
                false,
                new ScreenPosition(0, 187),
                () -> ParkourTickListener.vx,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelYSpeed",
                "Y Speed",
                false,
                new ScreenPosition(0, 196),
                () -> ParkourTickListener.vy,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelZSpeed",
                "Z Speed",
                false,
                new ScreenPosition(0, 205),
                () -> ParkourTickListener.vz,
                LabelFormat.NUMBER
        ));
    }
}
