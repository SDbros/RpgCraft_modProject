package com.sdbros.rpgcraft.event;


import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.capability.IPlayerData;
import com.sdbros.rpgcraft.capability.PlayerDataCapability;
import com.sdbros.rpgcraft.util.ModifierHandler;
import com.sdbros.rpgcraft.util.Players;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

import java.util.function.Supplier;


@Mod.EventBusSubscriber(modid = RpgCraft.MOD_ID)
public final class RpgCraftCommonEvents {
    private RpgCraftCommonEvents() {
    }

    @SubscribeEvent
    public static void onPlayerJoinServer(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        player.getCapability(PlayerDataCapability.INSTANCE).ifPresent(data -> {
            RpgCraft.LOGGER.info("Updating stats for {}", player.getScoreboardName());
            data.updateStats(player);
        });
    }

    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (PlayerDataCapability.canAttachTo(entity)) {
            //RpgCraft.LOGGER.debug("Attaching player data capability to " + entity);
            event.addCapability(PlayerDataCapability.NAME, new PlayerDataCapability());
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
