package com.sdbros.rpgcraft.util;

import net.minecraft.entity.player.PlayerEntity;


public final class Players {
    private Players() {
        throw new IllegalAccessError("Utility class");
    }

    public static int startingHealth(PlayerEntity player) {
        return -10;
    }

    public static int minHealth(PlayerEntity player) {
        return -19;
    }

    public static int maxHealth(PlayerEntity player) {
        int value = 50;
        return value; //<= 0 ? Integer.MAX_VALUE : value;
    }

    public static int clampExtraHearts(PlayerEntity player, int value) {
        //make sure heath is between min and max health
        value = value > maxHealth(player) ? maxHealth(player) : Math.max(value, minHealth(player));
        return value;
    }

}
