package net.cyvfabric.hud.labels;

import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.LabelFormat;
import net.cyvfabric.hud.structure.ScreenPosition;

public class LabelBundleTickTimings extends LabelBundle {
    public LabelBundleTickTimings() {
        this.labels.add(new DraggableHUDLabel<>(
                "labelLastTiming",
                "Last Timing",
                true,
                new ScreenPosition(0, 56),
                () -> ParkourTickListener.lastTiming,
                LabelFormat.TEXT("[Timing]")
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelAirtime",
                "Airtime",
                true,
                new ScreenPosition(0, 65),
                () -> ParkourTickListener.lastAirtime,
                LabelFormat.INTEGER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelTier",
                "Tier",
                false,
                new ScreenPosition(177, 137),
                () -> 12 - ParkourTickListener.lastAirtime,
                LabelFormat.INTEGER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelRuntime",
                "Runtime",
                false,
                new ScreenPosition(400, 409),
                () -> ParkourTickListener.lastRuntime,
                LabelFormat.INTEGER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelStoptime",
                "Stoptime",
                false,
                new ScreenPosition(400, 418),
                () -> ParkourTickListener.lastStopTime,
                LabelFormat.INTEGER
        ));
    }
}
