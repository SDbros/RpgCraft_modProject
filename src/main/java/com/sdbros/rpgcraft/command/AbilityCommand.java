package com.sdbros.rpgcraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.capability.Abilities;
import com.sdbros.rpgcraft.capability.AbilityData;
import com.sdbros.rpgcraft.capability.MobCapability;
import com.sdbros.rpgcraft.capability.PlayerCapability;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static com.sdbros.rpgcraft.capability.Abilities.ABILITY_REGISTRY;


public class AbilityCommand extends BasicCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("rpgcraft").requires(source ->
                source.hasPermissionLevel(PERMISSION_LEVEL_CHEAT));
        builder
                .then(Commands.literal("ability")
                        .then(Commands.literal("add")
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .then(Commands.argument("ability", StringArgumentType.word())
                                                .suggests((context, suggestionsBuilder) -> ISuggestionProvider.suggest(Abilities.Ability.getNames(), suggestionsBuilder))
                                                .then(Commands.argument("amplifier", IntegerArgumentType.integer())
                                                        .executes(
                                                                AbilityCommand::addAbility
                                                        )
                                                )
                                        )
                                )
                        )
                        .then(Commands.literal("remove")
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .then(Commands.argument("ability", StringArgumentType.word())
                                                .suggests((context, suggestionsBuilder) -> ISuggestionProvider.suggest(Abilities.Ability.getNames(), suggestionsBuilder))
                                                .executes(
                                                        AbilityCommand::removeAbility
                                                )
                                        )
                                )
                        )
                        .then(Commands.literal("clear")
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(
                                                AbilityCommand::clearAbility
                                        )
                                )
                        )

                );
        dispatcher.register(builder);
    }

    private static int clearAbility(CommandContext<CommandSource> context) throws CommandSyntaxException {
        try {
            for (Entity entity : EntityArgument.getEntities(context, "targets")) {
                entity.getCapability(MobCapability.MOB_INSTANCE).ifPresent(MobCapability.IMobCapabilityHandler::clearAbilities);
            }
            for (Entity entity : EntityArgument.getEntities(context, "targets")) {
                entity.getCapability(PlayerCapability.PLAYER_INSTANCE).ifPresent(PlayerCapability.IPlayerCapabilityHandler::clearAbilities);
            }

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static int addAbility(CommandContext<CommandSource> context) throws CommandSyntaxException {

        try {
            ResourceLocation rl = RpgCraft.getId(StringArgumentType.getString(context, "ability"));
            AbilityData a = ABILITY_REGISTRY.getValue(rl);

            for (Entity entity : EntityArgument.getEntities(context, "targets")) {
                entity.getCapability(MobCapability.MOB_INSTANCE).ifPresent(affected -> {
                    affected.addAbility(a.getData().setAmplifier(IntegerArgumentType.getInteger(context, "amplifier")));
                });
            }

            for (Entity entity : EntityArgument.getEntities(context, "targets")) {
                entity.getCapability(PlayerCapability.PLAYER_INSTANCE).ifPresent(affected -> {
                    affected.addAbility(a.getData().setAmplifier(IntegerArgumentType.getInteger(context, "amplifier")));
                });
            }

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static int removeAbility(CommandContext<CommandSource> context) throws CommandSyntaxException {
        try {
            ResourceLocation rl = RpgCraft.getId(StringArgumentType.getString(context, "ability"));
            AbilityData a = ABILITY_REGISTRY.getValue(rl);

            for (Entity entity : EntityArgument.getEntities(context, "targets")) {
                entity.getCapability(MobCapability.MOB_INSTANCE).ifPresent(affected -> {
                    affected.removeAbility(a.getData());
                });
            }

            for (Entity entity : EntityArgument.getEntities(context, "targets")) {
                entity.getCapability(PlayerCapability.PLAYER_INSTANCE).ifPresent(affected -> {
                    affected.removeAbility(a.getData());
                });
            }

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
