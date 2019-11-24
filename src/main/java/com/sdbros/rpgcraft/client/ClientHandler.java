package com.sdbros.rpgcraft.client;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.network.ClientLoginMessage;
import com.sdbros.rpgcraft.network.ClientSyncMessage;
import com.sdbros.rpgcraft.util.AreaLevelMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.function.Supplier;

/**
 * Handles information synced from the server. Only information actually needed by the client should
 * be synced. This information will likely not be updated every tick.
 */
public final class ClientHandler {
    private static final Marker MARKER = MarkerManager.getMarker("ClientHandler");
    // Frequent updates (up to once per second)
    public static float areaLevelMode;
    // Infrequent updates (join server/world, travel to new dimension)
    public static AreaLevelMode areaMode = AreaLevelMode.DIMENSION_WIDE;
    public static float maxDifficultyValue;

    private ClientHandler() {}

    public static void onMessage(ClientSyncMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> handleSyncMessage(msg));
        ctx.get().setPacketHandled(true);
    }

    private static void handleSyncMessage(ClientSyncMessage msg) {
        areaLevelMode = msg.areaLevelMode;

        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;
        if (player != null) {
            //player.experienceLevel = msg.experienceLevel;
        }
    }

    public static void onLoginMessage(ClientLoginMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> handleLoginMessage(msg));
        ctx.get().setPacketHandled(true);
    }

    private static void handleLoginMessage(ClientLoginMessage msg) {
        RpgCraft.LOGGER.info(MARKER, "Processing login packet");
        areaMode = msg.areaMode;
        maxDifficultyValue = msg.maxLevelValue;
        RpgCraft.LOGGER.info(MARKER, "Server area mode: {}", areaMode.getDisplayName().getFormattedText());
        RpgCraft.LOGGER.info(MARKER, "Server max level: {}", maxDifficultyValue);
    }
}
