package com.sdbros.rpgcraft.util;

import com.sdbros.rpgcraft.config.PlayerConfig;
import net.minecraft.entity.player.PlayerEntity;


public final class Players {

    public static int startingHealth(PlayerEntity player) {
        return PlayerConfig.startingHealth.get() -20;
    }

    public static int minHealth(PlayerEntity player) {
        return PlayerConfig.minHealth.get();
    }

    public static int maxHealth(PlayerEntity player) {
        return PlayerConfig.maxHealth.get();
    }

    public static int clampExtraHearts(PlayerEntity player, int value) {
        //make sure heath is between min and max health
        value = value > maxHealth(player) ? maxHealth(player) : Math.max(value, minHealth(player));
        return value;
    }

}
