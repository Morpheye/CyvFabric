package net.cyvfabric.hud;

import com.mojang.blaze3d.platform.Window;
import net.cyvfabric.command.mpk.CommandMacro;
import net.cyvfabric.gui.GuiMPK;
import net.cyvfabric.gui.GuiModConfig;
import net.cyvfabric.hud.labels.*;
import net.cyvfabric.hud.nonlabels.DirectionHUD;
import net.cyvfabric.hud.nonlabels.KeystrokesHUD;
import net.cyvfabric.hud.nonlabels.TogglesprintHUD;
import net.cyvfabric.hud.structure.DraggableHUDElement;
import net.cyvfabric.hud.structure.ScreenPosition;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;

import java.util.ArrayList;
import java.util.List;

public class HUDManager {
    public static List<DraggableHUDElement> registeredRenderers = new ArrayList<DraggableHUDElement>();
    private static Minecraft mc = Minecraft.getInstance();

    public static void init() { //initialize and create eventlistener
        HudElementRegistry.attachElementBefore(VanillaHudElements.PLAYER_LIST, RenderLayers.LABELS_LAYER, HUDManager::render);

        registeredRenderers.add(new DirectionHUD());
        registeredRenderers.add(new TogglesprintHUD());
        registeredRenderers.add(new KeystrokesHUD());

        registeredRenderers.addAll(new LabelBundleCoordinates().labels);
        registeredRenderers.addAll(new LabelBundleHitCoords().labels);
        registeredRenderers.addAll(new LabelBundleJumpCoords().labels);
        registeredRenderers.addAll(new LabelBundleLandingCoords().labels);
        registeredRenderers.addAll(new LabelBundleLandingPB().labels);
        registeredRenderers.addAll(new LabelBundleLasts().labels);
        registeredRenderers.addAll(new LabelBundleMomentumOffsets().labels);
        registeredRenderers.addAll(new LabelBundleSpeedVector().labels);
        registeredRenderers.addAll(new LabelBundleSpeeds().labels);
        registeredRenderers.addAll(new LabelBundleTickTimings().labels);
        registeredRenderers.addAll(new LabelBundleTurningAngles().labels);
        registeredRenderers.addAll(new LabelBundleHitExtras().labels);

    }

    private static void render(GuiGraphics context, DeltaTracker partialTicks) {
        if (CommandMacro.macroRunning > 0) { //macrorunning
            Window sr = mc.getWindow();
            context.drawString(mc.font, "MACRO",
                    sr.getGuiScaledWidth()/2 - mc.font.width("MACRO") / 2,
                    sr.getGuiScaledHeight()/5, 0xFFFF0000, false);
        }

        if (mc.screen == null || mc.screen instanceof ContainerScreen ||
                mc.screen instanceof ChatScreen || mc.screen instanceof GuiModConfig
                || mc.screen instanceof GuiMPK) {
            for (DraggableHUDElement renderer : registeredRenderers) {
                if (mc.screen instanceof ContainerScreen && !renderer.renderInGui()) continue;
                if (mc.screen instanceof ChatScreen && !renderer.renderInChat()) continue;

                if (mc.debugEntries.isOverlayVisible() && !renderer.renderInOverlay()) continue;

                callRenderer(renderer, context, partialTicks);
            }
        }
    }

    private static void callRenderer(DraggableHUDElement renderer, GuiGraphics context, DeltaTracker partialTicks) {
        if (!renderer.isEnabled) return;
        if (!renderer.isVisible) return;

        ScreenPosition pos = renderer.load();

        if (pos == null) {
            pos = renderer.getDefaultPosition();
        }

        renderer.render(context, pos);

    }

}
