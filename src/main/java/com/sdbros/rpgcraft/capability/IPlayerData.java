package com.sdbros.rpgcraft.capability;

import com.sdbros.rpgcraft.network.ClientSyncMessage;
import com.sdbros.rpgcraft.network.Network;
import com.sdbros.rpgcraft.util.Level;
import com.sdbros.rpgcraft.util.Players;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.network.NetworkDirection;

public interface IPlayerData {
    int getExtraHearts();

    void setExtraHearts(PlayerEntity player, int amount);

    default void addHearts(PlayerEntity player, int amount) {
        setExtraHearts(player, getExtraHearts() + amount);
    }

    default int getHealthModifier(PlayerEntity player) {
        return getExtraHearts() + Players.startingHealth(player);
    }

    void updateStats(PlayerEntity player);

    void tick(PlayerEntity player);

    static void sendUpdatePacketTo(PlayerEntity player) {
        World world = player.world;
        BlockPos pos = player.getPosition();
        ClientSyncMessage msg = new ClientSyncMessage(
                (float) Level.areaLevel(world, pos),
                player.experienceLevel
        );
        ServerPlayerEntity playerMP = (ServerPlayerEntity) player;
        Network.channel.sendTo(msg, playerMP.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }
}
