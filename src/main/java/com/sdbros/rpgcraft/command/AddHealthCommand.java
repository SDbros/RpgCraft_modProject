package com.sdbros.rpgcraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sdbros.rpgcraft.capability.PlayerDataCapability;
import com.sdbros.rpgcraft.world.features.FeatureManager;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

public class AddHealthCommand extends BasicCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("rpgcraft").requires(source ->
                source.hasPermissionLevel(PERMISSION_LEVEL_CHEAT));
        builder
                .then(Commands.literal("addhealth")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(
                                                AddHealthCommand::AddHealth
                                        )
                                )
                        )
                );
        dispatcher.register(builder);
    }

    private static int AddHealth(CommandContext<CommandSource> context) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(context, "amount");
        for (ServerPlayerEntity player : EntityArgument.getPlayers(context, "targets")) {
            player.getCapability(PlayerDataCapability.INSTANCE).ifPresent(data -> {
                data.addHearts(player, amount);
            });
        }
        return 1;
    }
}
