package com.sdbros.rpgcraft.util;

import com.sdbros.rpgcraft.config.PlayerConfig;
import net.minecraft.entity.player.PlayerEntity;


public final class Players {
    private Players() {
        throw new IllegalAccessError("Utility class");
    }

    public static int startingHealth(PlayerEntity player) {
        return PlayerConfig.startingHealth.get();
        //return -10;
    }

    public static int minHealth(PlayerEntity player) {
        return PlayerConfig.minHealth.get();
        //return -19;
    }

    public static int maxHealth(PlayerEntity player) {
        return PlayerConfig.maxHealth.get();
        //return 40;
    }

    public static int clampExtraHearts(PlayerEntity player, int value) {
        //make sure heath is between min and max health
        value = value > maxHealth(player) ? maxHealth(player) : Math.max(value, minHealth(player));
        return value;
    }

}
