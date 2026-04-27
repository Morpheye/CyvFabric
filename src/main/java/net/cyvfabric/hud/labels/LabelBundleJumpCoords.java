package net.cyvfabric.hud.labels;

import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.LabelFormat;
import net.cyvfabric.hud.structure.ScreenPosition;

public class LabelBundleJumpCoords extends LabelBundle {
    public LabelBundleJumpCoords() {
        this.labels.add(new DraggableHUDLabel<>(
                "labelJumpX",
                "Jump X",
                false,
                new ScreenPosition(0, 159),
                () -> ParkourTickListener.jx,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelJumpY",
                "Jump Y",
                false,
                new ScreenPosition(0, 168),
                () -> ParkourTickListener.jy,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelJumpZ",
                "Jump Z",
                false,
                new ScreenPosition(0, 177),
                () -> ParkourTickListener.jz,
                LabelFormat.NUMBER
        ));
    }
}
