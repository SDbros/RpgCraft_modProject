package com.sdbros.rpgcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class PlayerConfig {

    public static IntValue startingHealth;
    public static IntValue minHealth;
    public static IntValue maxHealth;

    public static void init(ForgeConfigSpec.Builder builder, ForgeConfigSpec.Builder client) {
        builder.comment("Player Config");


        startingHealth = builder
                .comment("The amount of half hearts a player starts with")
                .defineInRange("startingHealth", -10, -19, 60);
        minHealth = builder
                .comment("The minimum amount of  half hearts a player can have")
                .defineInRange("minHealth", -10, -19, 60);
        maxHealth = builder
                .comment("The maximum amount of half hearts a player can have")
                .defineInRange("maxHealth", 20, 2, 60);
    }
}