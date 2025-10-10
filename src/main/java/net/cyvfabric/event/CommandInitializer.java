package net.cyvfabric.event;

import com.mojang.brigadier.tree.LiteralCommandNode;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.command.CommandGui;
import net.cyvfabric.command.CommandHelp;
import net.cyvfabric.command.CommandInertia;
import net.cyvfabric.command.calculations.*;
import net.cyvfabric.command.config.*;
import net.cyvfabric.command.mpk.*;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.argument.MessageArgumentType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandInitializer  {
    public static ArrayList<CyvCommand> cyvCommands = new ArrayList<CyvCommand>(); //all commands

    //add all commands in
    public static void addCommands() {
        CyvCommand[] e = new CyvCommand[] {};

        cyvCommands.addAll(Arrays.asList(new CyvCommand[]{ //config commands
                new CommandHelp(), new CommandColor1(), new CommandColor2(), new CommandColors(), new CommandDf(),
                new CommandConfig(), new CommandGui(), new CommandInertia()
        }));

        cyvCommands.addAll(Arrays.asList(new CyvCommand[]{ //config commands
                new CommandSetlb(), new CommandClearlb(), new CommandClearpb(), new CommandSetmm(), new CommandClearmm(),
                new CommandSetbox(), new CommandSetcond(), new CommandLb(), new CommandMm()
        }));

        cyvCommands.addAll(Arrays.asList(new CyvCommand[] { //mm commands
                new CommandAirtime(), new CommandCalculate(), new CommandDistance(), new CommandHeight(),
                new CommandSimulate(), new CommandSetSensitivity(), new CommandOptimizeSensitivity(), new CommandSimulate()
        }));

        cyvCommands.add(new CommandMacro());
    }

    @SuppressWarnings({"unchecked"})
    public static void register() {
        addCommands();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            //register the base command
            LiteralCommandNode<FabricClientCommandSource> baseCommand = dispatcher.register(
                    (ClientCommandManager.literal("cyv")
                    .requires(source -> source.hasPermissionLevel(0)))
                    .executes(context -> {
                        CyvFabric.sendChatMessage("For more info use /cyv help"); //no args
                        return 1;
                     })
                    .then(ClientCommandManager.argument("message", MessageArgumentType.message()).executes(context -> {
                        List<String> argsList = new ArrayList<>(Arrays.stream(context.getInput().split(" ")).toList());
                        argsList.remove(0);
                        String[] args = argsList.toArray(String[]::new);

                        //all arguments following /mpk
                        CyvCommand targetCommand = null;

                        Outer: for (CyvCommand cmd : cyvCommands) { //loop through subcommands
                            if (!cmd.name.toLowerCase().equals(args[0])) {
                                for (String s : cmd.aliases) {
                                    if (s.toLowerCase().equals(args[0])) {
                                        targetCommand = cmd;
                                        break Outer;
                                    }
                                }
                            } else {
                                targetCommand = cmd;
                                break Outer;
                            }
                        }

                        if (targetCommand != null) {
                            String[] newArgs = new String[]{};
                            if (args.length > 1) newArgs = Arrays.copyOfRange(args, 1, args.length);
                            targetCommand.run(context, newArgs);
                            return 1;
                        }

                        //finished looping through with no matches?
                        CyvFabric.sendChatMessage("Unknown command. For more info use /cyv help");


                return 1;
            })));

            dispatcher.register((ClientCommandManager.literal("mpk").redirect(baseCommand)).executes(context -> {
                CyvFabric.sendChatMessage("For more info use /cyv help"); //no args
                return 1;
            })); //alias for /cyv

            dispatcher.register((ClientCommandManager.literal("mm").redirect(baseCommand)).executes(context -> {
                CyvFabric.sendChatMessage("For more info use /cyv help"); //no args
                return 1;
            })); //alias for /cyv

        });
    }

}
