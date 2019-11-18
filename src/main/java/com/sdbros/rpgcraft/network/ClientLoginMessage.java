package com.sdbros.rpgcraft.network;

import io.netty.buffer.ByteBuf;

public class ClientLoginMessage {
    public float maxDifficultyValue;

    public ClientLoginMessage() {}

    public ClientLoginMessage(float maxDifficultyValue) {
        this.maxDifficultyValue = maxDifficultyValue;
    }

    public static ClientLoginMessage fromBytes(ByteBuf buf) {
        ClientLoginMessage msg = new ClientLoginMessage();
        msg.maxDifficultyValue = buf.readFloat();
        return msg;
    }

    public void toBytes(ByteBuf buf) {
        buf.writeFloat(maxDifficultyValue);
    }
}
