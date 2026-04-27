package net.cyvfabric.hud.labels;

import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.LabelFormat;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.minecraft.client.MinecraftClient;

public class LabelBundleCoordinates extends LabelBundle {
    public LabelBundleCoordinates() {
        final MinecraftClient mc = MinecraftClient.getInstance();

        this.labels.add(new DraggableHUDLabel<>(
                "labelFPS",
                "FPS",
                true,
                new ScreenPosition(0, 1),
                mc::getCurrentFps,
                LabelFormat.INTEGER(360)
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelX",
                "X Coord",
                "X",
                true,
                new ScreenPosition(0, 10),
                () -> ParkourTickListener.x,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelY",
                "Y Coord",
                "Y",
                true,
                new ScreenPosition(0, 19),
                () -> ParkourTickListener.y,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelZ",
                "Z Coord",
                "Z",
                true,
                new ScreenPosition(0, 28),
                () -> ParkourTickListener.z,
                LabelFormat.NUMBER
        ));

        this.labels.add(new DraggableHUDLabel<>(  // TODO: add frame based angles + facing axis options
                "labelYaw",
                "Yaw",
                "F",
                true,
                new ScreenPosition(0, 37),
                () -> (ParkourTickListener.lastTick == null)
                        ? 0
                        : ParkourTickListener.lastTick.f,
                LabelFormat.WRAPPED_ANGLE
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelPitch",
                "Pitch",
                true,
                new ScreenPosition(0, 46),
                () -> (ParkourTickListener.lastTick == null)
                        ? 0
                        : ParkourTickListener.lastTick.p,
                LabelFormat.ANGLE
        ));
    }
}
