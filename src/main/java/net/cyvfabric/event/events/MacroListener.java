package net.cyvfabric.event.events;

import net.cyvfabric.CyvFabric;
import net.cyvfabric.command.mpk.CommandMacro;
import net.cyvfabric.config.CyvClientConfig;
import net.cyvfabric.hud.RenderLayers;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;

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

    public static void onRender(DrawContext context, RenderTickCounter partialTicks) {
        if (!CyvClientConfig.getBoolean("smoothMacro", false)) return;

        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity mcPlayer = mc.player;
        GameOptions options = mc.options;

        float renderTickTime = partialTicks.getTickProgress(false);

        if (CommandMacro.macroRunning > 1) {
            try {
                if (renderTickTime - lastPartial < 0.1) return;
                int index = CommandMacro.macro.size() - CommandMacro.macroRunning;
                ArrayList<String> macro = CommandMacro.macro.get(index+1);
                double yawChange = Double.parseDouble(macro.get(7)) * (renderTickTime - lastPartial);
                double pitchChange = Double.parseDouble(macro.get(8)) * (renderTickTime - lastPartial);

                double smallestAngle = (float) (1.2 * Math.pow((0.6 * options.getMouseSensitivity().getValue() + 0.2), 3));
                yawChange = smallestAngle * Math.round(yawChange/smallestAngle);
                pitchChange = smallestAngle * Math.round(pitchChange/smallestAngle);
                mcPlayer.setYaw(mcPlayer.getYaw() + (float) yawChange);
                mcPlayer.setPitch(mcPlayer.getPitch() + (float) pitchChange);
                lastPartial = renderTickTime;
                partialYawChange.add((float) yawChange);
                partialPitchChange.add((float) pitchChange);
            } catch (Exception f) {
                CyvFabric.LOGGER.error(String.valueOf(f));
            }

        }


    }

    public static void onTick(MinecraftClient mc) {
        PlayerEntity player = mc.player;
        GameOptions options = mc.options;

        //parse json file
        if (CommandMacro.macroRunning != 0) {
            if (mc.isPaused() || !mc.world.isClient()) { //stop macro if the game is paused
                KeyBinding.unpressAll();
                macroEnded = false;
                CommandMacro.macroRunning = 0;
                return;
            }

            //stop the macro if it has reached the end
            if (CommandMacro.macroRunning == 1) {
                try {
                    KeyBinding.unpressAll();
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

                options.forwardKey.setPressed(Boolean.parseBoolean(macro.get(0)));
                options.leftKey.setPressed(Boolean.parseBoolean(macro.get(1)));
                options.backKey.setPressed(Boolean.parseBoolean(macro.get(2)));
                options.rightKey.setPressed(Boolean.parseBoolean(macro.get(3)));
                options.jumpKey.setPressed(Boolean.parseBoolean(macro.get(4)));

                options.sprintKey.setPressed(Boolean.parseBoolean(macro.get(5)));
                options.sneakKey.setPressed(Boolean.parseBoolean(macro.get(6)));

                float yawChange = Float.parseFloat(macro.get(7));
                float pitchChange = Float.parseFloat(macro.get(8));

                //undo partialtick turns
                for (int i = partialYawChange.size() - 1; i >= 0; i--) {
                    player.setYaw(player.getYaw() - partialYawChange.get(i));
                    player.setPitch(player.getPitch() - partialPitchChange.get(i));
                }

                player.setYaw(player.getYaw() + yawChange);
                player.setPitch(player.getPitch() + pitchChange);
                partialYawChange.clear(); partialPitchChange.clear(); lastPartial = 0;
                CommandMacro.macroRunning = CommandMacro.macroRunning - 1;

                if (CommandMacro.macroRunning == 0) {
                    macroEnded = true;

                    try {
                        KeyBinding.unpressAll();
                        macroEnded = false;
                    } catch (Exception f) {
                        CyvFabric.LOGGER.error(String.valueOf(f));
                    }

                }

            } catch (Exception e1) {
                CyvFabric.sendChatMessage("Error occurred in running macro.");
                CyvFabric.LOGGER.error(String.valueOf(e1));
                CommandMacro.macroRunning = 0;
                KeyBinding.unpressAll();
                macroEnded = false;
            }
        }
    }
}
