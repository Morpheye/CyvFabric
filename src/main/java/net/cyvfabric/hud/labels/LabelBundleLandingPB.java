package net.cyvfabric.hud.labels;

import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.LabelFormat;
import net.cyvfabric.hud.structure.ScreenPosition;

public class LabelBundleLandingPB extends LabelBundle {
    public LabelBundleLandingPB() {
        this.labels.add(new DraggableHUDLabel<>(
                "labelPB",
                "PB",
                true,
                new ScreenPosition(106, 0),
                () -> ParkourTickListener.landingBlock == null || ParkourTickListener.landingBlock.pb == null
                        ? Double.NaN
                        : ParkourTickListener.landingBlock.pb,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelXOffset",
                "X Offset",
                true,
                new ScreenPosition(106, 9),
                () -> ParkourTickListener.landingBlock == null || ParkourTickListener.landingBlock.lastOffsetX == null
                        ? Double.NaN
                        : ParkourTickListener.landingBlock.lastOffsetX,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelZOffset",
                "Z Offset",
                true,
                new ScreenPosition(106, 18),
                () -> ParkourTickListener.landingBlock == null || ParkourTickListener.landingBlock.lastOffsetZ == null
                        ? Double.NaN
                        : ParkourTickListener.landingBlock.lastOffsetZ,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelTotalOffset",
                "Total Offset",
                false,
                new ScreenPosition(106, 18),
                () -> ParkourTickListener.landingBlock == null || ParkourTickListener.landingBlock.lastPb == null
                        ? Double.NaN
                        : ParkourTickListener.landingBlock.lastPb,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelXPB",
                "X PB",
                false,
                new ScreenPosition(106, 27),
                () -> ParkourTickListener.landingBlock == null || ParkourTickListener.landingBlock.pbX == null
                        ? Double.NaN
                        : ParkourTickListener.landingBlock.pbX,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelZPB",
                "Z PB",
                false,
                new ScreenPosition(106, 36),
                () -> ParkourTickListener.landingBlock == null || ParkourTickListener.landingBlock.pbZ == null
                        ? Double.NaN
                        : ParkourTickListener.landingBlock.pbZ,
                LabelFormat.NUMBER
        ));
    }
}
