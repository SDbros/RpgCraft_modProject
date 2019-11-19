package com.sdbros.rpgcraft.capability;

import com.sdbros.rpgcraft.util.Players;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;


public interface IPlayerData {
    int getExtraHearts();

    void setExtraHearts(PlayerEntity player, int amount);

    void updateStats(PlayerEntity player);

    default void addHeart(PlayerEntity player) {
        setExtraHearts(player, getExtraHearts() + 1);
    }

    default void addHearts(PlayerEntity player, int amount) {
        setExtraHearts(player, getExtraHearts() + amount);
    }

    default int getHealthModifier(PlayerEntity player) {
        return 2 * getExtraHearts() + Players.startingHealth(player) - 20;
    }
}
