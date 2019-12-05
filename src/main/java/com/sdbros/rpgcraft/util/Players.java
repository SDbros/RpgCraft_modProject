package com.sdbros.rpgcraft.util;

import net.minecraft.entity.player.PlayerEntity;


public final class Players {
    private Players() {
        throw new IllegalAccessError("Utility class");
    }

    //todo use Config
    public static int startingHealth(PlayerEntity player) {
        return -10;
    }

    public static int minHealth(PlayerEntity player) {
        return -19;
    }

    //todo use Config
    public static int maxHealth(PlayerEntity player) {
        return 40;
    }

    public static int clampExtraHearts(PlayerEntity player, int value) {
        //make sure heath is between min and max health
        value = value > maxHealth(player) ? maxHealth(player) : Math.max(value, minHealth(player));
        return value;
    }

}
