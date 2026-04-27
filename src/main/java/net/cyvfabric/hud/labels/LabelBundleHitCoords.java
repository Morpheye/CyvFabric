package net.cyvfabric.hud.labels;

import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.LabelFormat;
import net.cyvfabric.hud.structure.ScreenPosition;

public class LabelBundleHitCoords extends LabelBundle {
    public LabelBundleHitCoords() {
        this.labels.add(new DraggableHUDLabel<>(
                "labelHitX",
                "Hit X",
                true,
                new ScreenPosition(0, 131),
                () -> ParkourTickListener.hx,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelHitY",
                "Hit Y",
                true,
                new ScreenPosition(0, 140),
                () -> ParkourTickListener.hy,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelHitZ",
                "Hit Z",
                true,
                new ScreenPosition(0, 149),
                () -> ParkourTickListener.hz,
                LabelFormat.NUMBER
        ));
    }
}
