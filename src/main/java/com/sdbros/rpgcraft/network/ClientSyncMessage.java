package com.sdbros.rpgcraft.network;

import net.minecraft.network.PacketBuffer;

public class ClientSyncMessage {
    public float playerDifficulty;
    public float worldDifficulty;
    public float areaDifficulty;
    public int regenTimer;
    public int locationMultiPercent;
    public int experienceLevel;

    public ClientSyncMessage() {}

    public ClientSyncMessage(float playerDifficulty, float worldDifficulty, float areaDifficulty, int regenTimer, double locationMultiplier, int xp) {
        this.playerDifficulty = playerDifficulty;
        this.worldDifficulty = worldDifficulty;
        this.areaDifficulty = areaDifficulty;
        this.regenTimer = regenTimer;
        this.locationMultiPercent = (int) (100 * locationMultiplier);
        this.experienceLevel = xp;
    }

    public static ClientSyncMessage fromBytes(PacketBuffer buf) {
        ClientSyncMessage msg = new ClientSyncMessage();
        msg.playerDifficulty = buf.readFloat();
        msg.worldDifficulty = buf.readFloat();
        msg.areaDifficulty = buf.readFloat();
        msg.regenTimer = buf.readVarInt();
        msg.locationMultiPercent = buf.readVarInt();
        return msg;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(playerDifficulty);
        buf.writeFloat(worldDifficulty);
        buf.writeFloat(areaDifficulty);
        buf.writeVarInt(regenTimer);
        buf.writeVarInt(locationMultiPercent);
    }
}
