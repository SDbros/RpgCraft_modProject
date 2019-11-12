package com.sdbros.rpgcraft.init;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.command.PlaceCommand;
import net.minecraft.command.CommandSource;

import java.util.List;

public class ModCommands {

    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        List<String> rpgcraft = Lists.newArrayList("rpgcraft");
        List<String> test = Lists.newArrayList("rpgcraft-test");
        if (true) {
            rpgcraft.add("r");
            test.add("rtest");
        }

        //rpgcraft commands
        for (String s : rpgcraft) {
            dispatcher.register(
                    LiteralArgumentBuilder.<CommandSource>literal(s)
            );
        }

        //Test commands
        for (String s : test) {
            dispatcher.register(
                    LiteralArgumentBuilder.<CommandSource>literal(s)
                            .then(PlaceCommand.register()));

        }
    }

}
