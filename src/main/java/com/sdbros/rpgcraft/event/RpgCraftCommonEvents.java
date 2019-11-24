package com.sdbros.rpgcraft.event;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.capability.MobDataCapability;
import com.sdbros.rpgcraft.capability.PlayerDataCapability;
import com.sdbros.rpgcraft.network.ClientLoginMessage;
import com.sdbros.rpgcraft.network.Network;
import com.sdbros.rpgcraft.util.Level;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

@Mod.EventBusSubscriber(modid = RpgCraft.MOD_ID)
public final class RpgCraftCommonEvents {
    private RpgCraftCommonEvents() {
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayerEntity)) return;
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        World world = player.world;
        RpgCraft.LOGGER.info("Sending login packet to player {}", player);
        ClientLoginMessage msg = new ClientLoginMessage(Level.getAreaLevelMode(), (float) Level.maxValue(world));
        Network.channel.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    @SubscribeEvent
    public static void onPlayerName(PlayerEvent.NameFormat event){
        event.setDisplayname("Notch");
    }


    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (MobDataCapability.canAttachTo(entity)){
            event.addCapability(MobDataCapability.NAME, new MobDataCapability());;
            //RpgCraft.LOGGER.info("MOB " + event.getCapabilities());
        }

        if (PlayerDataCapability.canAttachTo(entity)) {
            event.addCapability(PlayerDataCapability.NAME, new PlayerDataCapability());
            //RpgCraft.LOGGER.info("PLAYER " + event.getCapabilities());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        // If not dead, player is returning from the End
        if (!event.isWasDeath()) return;

        // Player died. Copy capabilities and apply health/difficulty changes.
        PlayerEntity original = event.getOriginal();
        PlayerEntity clone = event.getEntityPlayer();
        copyCapability(PlayerDataCapability.INSTANCE, original, clone);
        clone.getCapability(PlayerDataCapability.INSTANCE).ifPresent(data -> {
            RpgCraft.LOGGER.info("Updating stats for {}", clone.getScoreboardName());
            data.updateStats(clone);
        });

    }

    private static <T> void copyCapability(Capability<T> capability, ICapabilityProvider original, ICapabilityProvider clone) {
        original.getCapability(capability).ifPresent(dataOriginal -> {
            clone.getCapability(capability).ifPresent(dataClone -> {
                INBT nbt = capability.getStorage().writeNBT(capability, dataOriginal, null);
                capability.getStorage().readNBT(capability, dataClone, null, nbt);
            });
        });
    }

}
