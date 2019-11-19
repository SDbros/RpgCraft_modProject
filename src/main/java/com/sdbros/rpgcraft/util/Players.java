package com.sdbros.rpgcraft.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;


public final class Players {
    private Players() {throw new IllegalAccessError("Utility class");}

    public static int startingHealth(PlayerEntity player) {
        return 10;
    }

    public static int clampExtraHearts(PlayerEntity player, int value) {
        return 10;
    }

}
