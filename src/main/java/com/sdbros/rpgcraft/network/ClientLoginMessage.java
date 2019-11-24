package com.sdbros.rpgcraft.network;

import com.sdbros.rpgcraft.util.AreaLevelMode;
import io.netty.buffer.ByteBuf;

public class ClientLoginMessage {
    public AreaLevelMode areaMode;
    public float maxLevelValue;

    public ClientLoginMessage() {}

    public ClientLoginMessage(AreaLevelMode areaMode, float maxLevelValue) {
        this.areaMode = areaMode;
        this.maxLevelValue = maxLevelValue;
    }

    public static ClientLoginMessage fromBytes(ByteBuf buf) {
        ClientLoginMessage msg = new ClientLoginMessage();
        msg.areaMode = AreaLevelMode.fromOrdinal((int) buf.readByte());
        msg.maxLevelValue = buf.readFloat();
        return msg;
    }

    public void toBytes(ByteBuf buf) {
        buf.writeByte(areaMode.ordinal());
        buf.writeFloat(maxLevelValue);
    }
}
