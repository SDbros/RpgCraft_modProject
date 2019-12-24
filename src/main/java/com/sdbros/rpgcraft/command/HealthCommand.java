package com.sdbros.rpgcraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sdbros.rpgcraft.capabilities.PlayerCapability;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;


public class HealthCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("rpgcraft").requires(source ->
                source.hasPermissionLevel(2));
        builder
                .then(Commands.literal("health")
                        .then(Commands.literal("set")
                                .then(Commands.argument("targets", EntityArgument.players())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                                .executes(
                                                        HealthCommand::setHealth
                                                )
                                        )
                                )
                        )
                );
        dispatcher.register(builder);
    }

    private static int setHealth(CommandContext<CommandSource> context) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(context, "amount");
        for (ServerPlayerEntity player : EntityArgument.getPlayers(context, "targets")) {
            player.getCapability(PlayerCapability.PLAYER_INSTANCE).ifPresent(data -> {
                data.setExtraHearts(player, amount);
            });
        }
        return 1;
    }
}
