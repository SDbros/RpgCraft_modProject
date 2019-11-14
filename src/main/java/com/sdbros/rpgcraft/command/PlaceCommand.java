package com.sdbros.rpgcraft.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.sdbros.rpgcraft.world.features.FeatureManager;
import com.sdbros.rpgcraft.world.features.RpgCraftTemplate;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.template.PlacementSettings;

public class PlaceCommand extends BasicCommand {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("place")
                .requires(context -> context.hasPermissionLevel(PERMISSION_LEVEL_ADMIN))
                .then(Commands.argument("feature", StringArgumentType.word())
                        .suggests((context, builder) -> {
                            return ISuggestionProvider.suggest(FeatureManager.Feature.getNames(), builder);
                        })
                        .executes(context -> {
                            return place(context.getSource(), context.getSource().asPlayer(), StringArgumentType.getString(context, "feature"));
                        }));
    }

    private static int place(CommandSource commandSource, ServerPlayerEntity asPlayer, String feature) {
        try {
            FeatureManager.Feature f = FeatureManager.Feature.valueOf(feature);
            RpgCraftTemplate template = FeatureManager.get(f);
            if (template == null) {
                commandSource.sendErrorMessage(new TranslationTextComponent("command.rpgcraft.test.place.notloaded", f));
            }
            template.addBlocksToWorld(asPlayer.world, asPlayer.getPosition().offset(Direction.NORTH), new PlacementSettings());

        } catch (IllegalArgumentException e) {
            commandSource.sendErrorMessage(new TranslationTextComponent("command.rpgcraft.test.place.notfound", feature));
        }
        return 0;
    }
}