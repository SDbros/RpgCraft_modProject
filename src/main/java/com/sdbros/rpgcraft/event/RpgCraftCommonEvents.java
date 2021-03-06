package com.sdbros.rpgcraft.event;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.capabilities.MobCapability;
import com.sdbros.rpgcraft.capabilities.MobCapability.*;
import com.sdbros.rpgcraft.capabilities.PlayerCapability;
import com.sdbros.rpgcraft.capabilities.PlayerCapability.PlayerCapabilityData;
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

//    @SubscribeEvent
//    public static void onAttachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
//
//        ItemStack item = event.getObject();
//        if (ItemCapability.ItemCapabilityData.canAttachTo(item)) {
//            event.addCapability(ItemCapability.NAME, new ItemCapability.ItemCapabilityData());
//            RpgCraft.LOGGER.info("ItemHERE " + event.getCapabilities());
//        }
//
//    }

    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {

        Entity entity = event.getObject();
        if (MobCapabilityData.canAttachTo(entity)) {
            event.addCapability(MobCapability.NAME, new MobCapabilityData());
            //RpgCraft.LOGGER.info("MOB " + event.getCapabilities());
        }

        if (PlayerCapabilityData.canAttachTo(entity)) {
            event.addCapability(PlayerCapability.NAME, new PlayerCapabilityData());
            //RpgCraft.LOGGER.info("PLAYER " + event.getCapabilities());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        // If not dead, player is returning from the End
        if (!event.isWasDeath()) return;

        // Player died. Copy capabilities and apply health/difficulty changes.
        PlayerEntity original = event.getOriginal();
        PlayerEntity clone = event.getPlayer();
        copyCapability(PlayerCapability.PLAYER_INSTANCE, original, clone);
        clone.getCapability(PlayerCapability.PLAYER_INSTANCE).ifPresent(data -> {
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
