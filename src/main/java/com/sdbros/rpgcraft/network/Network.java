package net.silentchaos512.scalinghealth.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.silentchaos512.scalinghealth.ScalingHealth;
import net.silentchaos512.scalinghealth.client.ClientHandler;

import java.util.Objects;

public final class Network {
    private static final ResourceLocation NAME = ScalingHealth.getId("network");

    public static SimpleChannel channel;

    static {
        channel = NetworkRegistry.ChannelBuilder.named(NAME)
                .clientAcceptedVersions(s -> Objects.equals(s, "1"))
                .serverAcceptedVersions(s -> Objects.equals(s, "1"))
                .networkProtocolVersion(() -> "1")
                .simpleChannel();

        channel.messageBuilder(ClientSyncMessage.class, 1)
                .decoder(ClientSyncMessage::fromBytes)
                .encoder(ClientSyncMessage::toBytes)
                .consumer(ClientHandler::onMessage)
                .add();
        channel.messageBuilder(ClientLoginMessage.class, 2)
                .decoder(ClientLoginMessage::fromBytes)
                .encoder(ClientLoginMessage::toBytes)
                .consumer(ClientHandler::onLoginMessage)
                .add();
        channel.messageBuilder(SpawnBlightFirePacket.class, 3)
                .decoder(SpawnBlightFirePacket::fromBytes)
                .encoder(SpawnBlightFirePacket::toBytes)
                .consumer(SpawnBlightFirePacket::handle)
                .add();
    }

    private Network() {}

    public static void init() { }
}
