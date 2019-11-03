package com.sdbros.rpgcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OreGenerationConfig {

    public static ForgeConfigSpec.IntValue spawn_chance;
    public static ForgeConfigSpec.BooleanValue generate_overworld;

    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {
        server.comment("OreGeneration Config");

        spawn_chance = server
                .comment("Maximum number of ore veins of the tutorial ore that can spawn in one chunk.")
                .defineInRange("OreGeneration.tutorial_chance", 100, 1, 1000000);

        generate_overworld = server
                .comment("Decide if you want Tutorial Mod ores to spawn in the overworld")
                .define("OreGeneration.generate_overworld", true);
    }
}
