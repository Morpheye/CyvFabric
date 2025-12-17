package net.cyvfabric.event.events;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.command.mpk.CommandMacro;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.hud.RenderLayers;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import java.util.ArrayList;

public class MacroListener {
    static boolean macroEnded = false;

    static ArrayList<Float> partialYawChange = new ArrayList<Float>();
    static ArrayList<Float> partialPitchChange = new ArrayList<Float>();

    static double lastPartial = 0;

    public static void register() {
        ClientTickEvents.START_CLIENT_TICK.register(MacroListener::onTick);
        HudElementRegistry.addLast(RenderLayers.MACRO_LAYER, MacroListener::onRender);
    }

    public static void onRender(GuiGraphics context, DeltaTracker partialTicks) {
        if (!CyvClientConfig.getBoolean("smoothMacro", false)) return;

        Minecraft mc = Minecraft.getInstance();
        Player mcPlayer = mc.player;
        Options options = mc.options;

        float renderTickTime = partialTicks.getGameTimeDeltaPartialTick(false);

        if (CommandMacro.macroRunning > 1) {
            try {
                if (renderTickTime - lastPartial < 0.1) return;
                int index = CommandMacro.macro.size() - CommandMacro.macroRunning;
                ArrayList<String> macro = CommandMacro.macro.get(index+1);
                double yawChange = Double.parseDouble(macro.get(7)) * (renderTickTime - lastPartial);
                double pitchChange = Double.parseDouble(macro.get(8)) * (renderTickTime - lastPartial);

                double smallestAngle = (float) (1.2 * Math.pow((0.6 * options.sensitivity().get() + 0.2), 3));
                yawChange = smallestAngle * Math.round(yawChange/smallestAngle);
                pitchChange = smallestAngle * Math.round(pitchChange/smallestAngle);
                mcPlayer.setYRot(mcPlayer.getYRot() + (float) yawChange);
                mcPlayer.setXRot(mcPlayer.getXRot() + (float) pitchChange);
                lastPartial = renderTickTime;
                partialYawChange.add((float) yawChange);
                partialPitchChange.add((float) pitchChange);
            } catch (Exception f) {
                CyvFabric.LOGGER.error(String.valueOf(f));
            }

        }


    }

    public static void onTick(Minecraft mc) {
        Player player = mc.player;
        Options options = mc.options;

        //parse json file
        if (CommandMacro.macroRunning != 0) {
            if (mc.isPaused() || !mc.level.isClientSide()) { //stop macro if the game is paused
                KeyMapping.releaseAll();
                macroEnded = false;
                CommandMacro.macroRunning = 0;
                return;
            }

            //stop the macro if it has reached the end
            if (CommandMacro.macroRunning == 1) {
                try {
                    KeyMapping.releaseAll();
                } catch (Exception f) {
                    CyvFabric.LOGGER.error(String.valueOf(f));
                }
                macroEnded = false;
                CommandMacro.macroRunning = 0;
                return;
            }

            try {
                macroEnded = false;
                int index = CommandMacro.macro.size() - CommandMacro.macroRunning;
                ArrayList<String> macro = CommandMacro.macro.get(index + 1);

                //index starts at 1 and works its way to the length of the macro
                //macro.get(index)[x], x = 0: w, 1: a, 2: s, 3: d, 4: jump, 5: sprint, 6: sneak, 7/8: yaw/pitch

                options.keyUp.setDown(Boolean.parseBoolean(macro.get(0)));
                options.keyLeft.setDown(Boolean.parseBoolean(macro.get(1)));
                options.keyDown.setDown(Boolean.parseBoolean(macro.get(2)));
                options.keyRight.setDown(Boolean.parseBoolean(macro.get(3)));
                options.keyJump.setDown(Boolean.parseBoolean(macro.get(4)));

                options.keySprint.setDown(Boolean.parseBoolean(macro.get(5)));
                options.keyShift.setDown(Boolean.parseBoolean(macro.get(6)));

                float yawChange = Float.parseFloat(macro.get(7));
                float pitchChange = Float.parseFloat(macro.get(8));

                //undo partialtick turns
                for (int i = partialYawChange.size() - 1; i >= 0; i--) {
                    player.setYRot(player.getYRot() - partialYawChange.get(i));
                    player.setXRot(player.getXRot() - partialPitchChange.get(i));
                }

                player.setYRot(player.getYRot() + yawChange);
                player.setXRot(player.getXRot() + pitchChange);
                partialYawChange.clear(); partialPitchChange.clear(); lastPartial = 0;
                CommandMacro.macroRunning = CommandMacro.macroRunning - 1;

                if (CommandMacro.macroRunning == 0) {
                    macroEnded = true;

                    try {
                        KeyMapping.releaseAll();
                        macroEnded = false;
                    } catch (Exception f) {
                        CyvFabric.LOGGER.error(String.valueOf(f));
                    }

                }

            } catch (Exception e1) {
                CyvFabric.sendChatMessage("Error occurred in running macro.");
                CyvFabric.LOGGER.error(String.valueOf(e1));
                CommandMacro.macroRunning = 0;
                KeyMapping.releaseAll();
                macroEnded = false;
            }
        }
    }
}
