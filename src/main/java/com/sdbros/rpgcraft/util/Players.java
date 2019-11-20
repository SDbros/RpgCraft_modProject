package com.sdbros.rpgcraft.util;

import net.minecraft.entity.player.PlayerEntity;



public final class Players {
    private Players() {
        throw new IllegalAccessError("Utility class");
    }

    public static int startingHealth(PlayerEntity player) {
        return 10;
    }

    public static int minHealth(PlayerEntity player) {
        return 1;
    }

    public static int maxHealth(PlayerEntity player) {
        int value = 30;
        return value; //<= 0 ? Integer.MAX_VALUE : value;
    }

    public static int clampExtraHearts(PlayerEntity player, int value) {
        value = value > maxHealth(player) ? maxHealth(player) : Math.max(value, minHealth(player));
        return value;
    }

}
