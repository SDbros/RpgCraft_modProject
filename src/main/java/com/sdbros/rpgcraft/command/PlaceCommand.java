package com.sdbros.rpgcraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.sdbros.rpgcraft.capability.PlayerDataCapability;
import com.sdbros.rpgcraft.world.features.FeatureManager;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

public class PlaceCommand extends BasicCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("rpgcraft").requires(source ->
                source.hasPermissionLevel(PERMISSION_LEVEL_ADMIN));
        builder
                .then(Commands.literal("place")
                        .then(Commands.argument("feature", StringArgumentType.word())
                                .suggests((context, suggestionsBuilder) ->  ISuggestionProvider.suggest(FeatureManager.Feature.getNames(), suggestionsBuilder))
                                .executes(context ->
                                        place(
                                                context.getSource(),
                                                context.getSource().asPlayer(),
                                                StringArgumentType.getString(context, "feature")
                                            )
                                        )
                        )
                );
        dispatcher.register(builder);
    }

    private static int place(CommandSource commandSource, ServerPlayerEntity asPlayer, String feature){
        try {
            FeatureManager.Feature f = FeatureManager.Feature.valueOf(feature);
            Template template = FeatureManager.get(f);
            if (template == null) {
                commandSource.sendErrorMessage(new TranslationTextComponent("command.rpgcraft.test.place.notloaded", f));
            }
            if (template != null) {
                template.addBlocksToWorld(asPlayer.world, asPlayer.getPosition().offset(Direction.NORTH), new PlacementSettings());
            }

        } catch (IllegalArgumentException e) {
            commandSource.sendErrorMessage(new TranslationTextComponent("command.rpgcraft.test.place.notfound", feature));
        }
        return 0;
    }
}