package com.sdbros.rpgcraft.init;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.sdbros.rpgcraft.command.PlaceCommand;
import net.minecraft.command.CommandSource;

import java.util.List;

public class ModCommands {

    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        List<String> rpgcraft = Lists.newArrayList("rpgcraft");
        //rpgcraft commands
        for (String s : rpgcraft) {
            dispatcher.register(
                    LiteralArgumentBuilder.<CommandSource>literal(s)
                            .then(PlaceCommand.register()));
        }
    }
}
