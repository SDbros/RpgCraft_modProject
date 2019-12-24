package com.sdbros.rpgcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class DimensionConfig {

    public static BooleanValue grass_enable;
    public static BooleanValue day;
    public static IntValue world_height;
    public static IntValue overworldId;
    public static IntValue maxLevel;
    public static IntValue searchRadius;

    public static void init(ForgeConfigSpec.Builder builder, ForgeConfigSpec.Builder client) {
        builder.comment("Dimension Config");

        maxLevel = builder
                .comment("The maximum level mobs will scale to.")
                .defineInRange("max_level", 50, 0, 1000);
        searchRadius = builder
                .comment("Distance to look for difficulty sources (players) when calculating area difficulty.")
                .defineInRange("search_radius", 64, 16, 512);

        //for work in progress dimension
        grass_enable = builder
                .comment("Should the layers top of the world be dirt and grass.")
                .define("grass_enable", true);
        day = builder
                .comment("Should it always be day.")
                .define("day", true);
        world_height = builder
                .comment("Height of the world.")
                .defineInRange("world_height", 70, 5, 256);
        overworldId = builder
                .comment("Overworld dim ID.")
                .defineInRange("overworldId", 0, -1000, 1000);
    }
}