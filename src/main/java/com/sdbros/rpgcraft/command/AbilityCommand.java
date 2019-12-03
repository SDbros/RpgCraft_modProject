package com.sdbros.rpgcraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.capability.Abilities;
import com.sdbros.rpgcraft.capability.EntityAbilityData;
import com.sdbros.rpgcraft.capability.MobCapability;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;


public class AbilityCommand extends BasicCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("rpgcraft").requires(source ->
                source.hasPermissionLevel(PERMISSION_LEVEL_CHEAT));
        builder
                .then(Commands.literal("addability")
                        .then(Commands.argument("targets", EntityArgument.entities())
                                .then(Commands.argument("ability", StringArgumentType.word())
                                        .suggests((context, suggestionsBuilder) -> ISuggestionProvider.suggestIterable(Abilities.ENTITY_ABILITY_REGISTRY.getKeys(), suggestionsBuilder))
                                        .executes(
                                                AbilityCommand::addAbility
                                        )
                                )
                        )
                )
        .then(Commands.literal("removeability")
                .then(Commands.argument("targets", EntityArgument.entities())
                        .then(Commands.argument("ability", StringArgumentType.word())
                                .suggests((context, suggestionsBuilder) -> ISuggestionProvider.suggestIterable(Abilities.ENTITY_ABILITY_REGISTRY.getKeys(), suggestionsBuilder))
                                .executes(
                                        AbilityCommand::removeAbility
                                )
                        )
                )
        );
        dispatcher.register(builder);
    }

    private static int addAbility(CommandContext<CommandSource> context) throws CommandSyntaxException {

        try {
            ResourceLocation rl = RpgCraft.getId(StringArgumentType.getString(context, "ability"));

            EntityAbilityData a = Abilities.ENTITY_ABILITY_REGISTRY.getValue(rl);
            for (Entity entity : EntityArgument.getEntities(context, "targets")) {
                entity.getCapability(MobCapability.INSTANCE).ifPresent(affected -> {
                    affected.addAbility(a.getData());
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

            EntityAbilityData a = Abilities.ENTITY_ABILITY_REGISTRY.getValue(rl);
            for (Entity entity : EntityArgument.getEntities(context, "targets")) {
                entity.getCapability(MobCapability.INSTANCE).ifPresent(affected -> {
                    affected.removeAbility(a.getData());
                });
            }
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
