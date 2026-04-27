package net.cyvfabric.hud.labels;

import net.cyvfabric.event.events.ParkourTickListener;
import net.cyvfabric.hud.LabelBundle;
import net.cyvfabric.hud.structure.DraggableHUDLabel;
import net.cyvfabric.hud.structure.LabelFormat;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.ClientInput;

public class LabelBundleLasts extends LabelBundle {
    public LabelBundleLasts() {
        final Minecraft mc = Minecraft.getInstance();

        this.labels.add(new DraggableHUDLabel<>(
                "labelLast45",
                "Last 45",
                false,
                new ScreenPosition(177, 74),
                () -> ParkourTickListener.last45,
                LabelFormat.ANGLE
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelLastTurningYaw",
                "Last Turning",
                false,
                new ScreenPosition(177, 83),
                () -> ParkourTickListener.lastTurning,
                LabelFormat.ANGLE
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelLastInput",
                "Last Input",
                false,
                new ScreenPosition(177, 92),
                () -> {
                    if (mc.player == null) return "";

                    ClientInput input = mc.player.input;
                    return (input.getMoveVector().y > 0 ? "W" : "")
                            + (input.getMoveVector().x > 0 ? "A" : "")
                            + (input.getMoveVector().y < 0 ? "S" : "")
                            + (input.getMoveVector().x < 0 ? "D" : "");
                },
                LabelFormat.TEXT("WASD")
        ));

        this.labels.add(new DraggableHUDLabel<>(
                "labelLastSidestep",
                "Last Sidestep",
                false,
                new ScreenPosition(177, 101),
                () -> {
                    if (ParkourTickListener.sidestep == 0)
                        return "WAD " + ParkourTickListener.sidestepTime + " ticks";

                    if (ParkourTickListener.sidestep == 1)
                        return "WDWA";

                    return "None";
                },
                LabelFormat.TEXT("WAD")
        ));
    }
}
