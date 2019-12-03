package com.sdbros.rpgcraft.init;

import com.mojang.brigadier.CommandDispatcher;
import com.sdbros.rpgcraft.command.AbilityCommand;
import com.sdbros.rpgcraft.command.HealthCommand;
import com.sdbros.rpgcraft.command.PlaceCommand;
import net.minecraft.command.CommandSource;

public class ModCommands {

    public static void registerAll(CommandDispatcher<CommandSource> dispatcher) {
        PlaceCommand.register(dispatcher);
        HealthCommand.register(dispatcher);
        AbilityCommand.register(dispatcher);
    }
}
