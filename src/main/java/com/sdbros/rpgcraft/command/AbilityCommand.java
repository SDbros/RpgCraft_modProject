package com.sdbros.rpgcraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sdbros.rpgcraft.capability.PlayerDataCapability;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

public class AbilityCommand extends BasicCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("rpgcraft").requires(source ->
                source.hasPermissionLevel(PERMISSION_LEVEL_CHEAT));
        builder
                .then(Commands.literal("addAbility")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.argument("ability", IntegerArgumentType.integer())
                                        .executes(
                                                AbilityCommand::addAbility
                                        )
                                )
                        )
                );
        dispatcher.register(builder);
    }

    private static int addAbility(CommandContext<CommandSource> context) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(context, "amount");
        for (ServerPlayerEntity player : EntityArgument.getPlayers(context, "targets")) {
            player.getCapability(PlayerDataCapability.INSTANCE).ifPresent(data -> {
                //int intendedExtraHearts = (amount - Players.startingHealth(player)) / 2;
                data.setExtraHearts(player, amount);
            });
        }
        return 1;
    }
}
