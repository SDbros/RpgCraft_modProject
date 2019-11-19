package com.sdbros.rpgcraft.init;

import com.mojang.brigadier.CommandDispatcher;
import com.sdbros.rpgcraft.command.AddHealthCommand;
import com.sdbros.rpgcraft.command.PlaceCommand;
import net.minecraft.command.CommandSource;

public class ModCommands {

    public static void registerAll(CommandDispatcher<CommandSource> dispatcher) {
        PlaceCommand.register(dispatcher);
        AddHealthCommand.register(dispatcher);
    }
}
