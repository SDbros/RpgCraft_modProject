package com.sdbros.rpgcraft.network;

import net.minecraft.network.PacketBuffer;

public class ClientSyncMessage {
    public float areaLevelMode;
    public int experienceLevel;

    public ClientSyncMessage() {}

    public ClientSyncMessage(float areaLevelMode, int xp) {
        this.areaLevelMode = areaLevelMode;
        this.experienceLevel = xp;
    }

    public static ClientSyncMessage fromBytes(PacketBuffer buf) {
        ClientSyncMessage msg = new ClientSyncMessage();
        msg.areaLevelMode = buf.readFloat();
        return msg;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(areaLevelMode);
    }
}
