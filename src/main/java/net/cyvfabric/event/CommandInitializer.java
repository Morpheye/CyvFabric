package net.cyvfabric.event;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.cyvfabric.CyvFabric;
import net.cyvfabric.command.CommandHelp;
import net.cyvfabric.command.calculations.*;
import net.cyvfabric.util.CyvCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandInitializer  {
    public static ArrayList<CyvCommand> cyvCommands = new ArrayList<CyvCommand>(); //all commands

    //add all commands in
    public static void addCommands() {
        cyvCommands.add(new CommandHelp());

        CyvCommand[] e = new CyvCommand[] {};

        cyvCommands.addAll(Arrays.asList(new CyvCommand[] {
                new CommandAirtime(), new CommandSimulate(), new CommandDistance(), new CommandHeight(),
                new CommandSimulate(), new CommandSetSensitivity(), new CommandOptimizeSensitivity()
        }));
    }

    @SuppressWarnings({"unchecked"})
    public static void register() {
        addCommands();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            //register the base command
            LiteralCommandNode<ServerCommandSource> baseCommand = dispatcher.register(
                    (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("cyv")
                    .requires(source -> source.hasPermissionLevel(2)))
                    .executes(context -> {
                        CyvFabric.sendMessage("For more info use /cyv help"); //no args
                        return 1;
                     })
                    .then(CommandManager.argument("message", MessageArgumentType.message()).executes(context -> {
                MessageArgumentType.getSignedMessage(context, "message", message -> {
                    String[] args = message.getSignedContent().split(" "); //all arguments following /mpk
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
                        return;
                    }

                    //finished looping through with no matches?
                    CyvFabric.sendMessage("Unknown command. For more info use /cyv help");

                });

                return 1;
            })));

            dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)
                    CommandManager.literal("mpk").redirect(baseCommand)).executes(context -> {
                CyvFabric.sendMessage("For more info use /cyv help"); //no args
                return 1;
            })); //alias for /cyv

        });
    }

}