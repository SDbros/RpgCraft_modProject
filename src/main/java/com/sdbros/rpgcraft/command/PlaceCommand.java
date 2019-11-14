package com.sdbros.rpgcraft.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.sdbros.rpgcraft.world.gen.features.FeatureManager;
import com.sdbros.rpgcraft.world.gen.features.RpgCraftTemplate;
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
                .then(Commands.argument("structure", StringArgumentType.word())
                        .suggests((context, builder) -> {
                            return ISuggestionProvider.suggest(FeatureManager.Feature.getNames(), builder);
                        })
                        .executes(context -> {
                            return place(context.getSource(), context.getSource().asPlayer(), StringArgumentType.getString(context, "structure"));
                        }));
    }

    private static int place(CommandSource commandSource, ServerPlayerEntity asPlayer, String structure) {
        try {
            FeatureManager.Feature s = FeatureManager.Feature.valueOf(structure);
            RpgCraftTemplate template = FeatureManager.get(s);
            if (template == null) {
                commandSource.sendErrorMessage(new TranslationTextComponent("command.rpgcraft.test.place.notloaded", s));
            }
            template.addBlocksToWorld(asPlayer.world, asPlayer.getPosition().offset(Direction.NORTH), new PlacementSettings());

        } catch (IllegalArgumentException e) {
            commandSource.sendErrorMessage(new TranslationTextComponent("command.rpgcraft.test.place.notfound", structure));
        }
        return 0;
    }
}