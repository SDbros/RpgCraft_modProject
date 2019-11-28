package com.sdbros.rpgcraft.event;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.capability.MobCapability;
import com.sdbros.rpgcraft.capability.PlayerDataCapability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@Mod.EventBusSubscriber(modid = RpgCraft.MOD_ID)
public class LevelEvents {

    public static final Marker MARKER = MarkerManager.getMarker("level");

    @SubscribeEvent
    public static void onPlayerJoinServer(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        player.getCapability(PlayerDataCapability.INSTANCE).ifPresent(data -> {
            RpgCraft.LOGGER.info("Updating stats for {}", player.getScoreboardName());
            data.updateStats(player);
        });
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.world.isRemote) return;

        // Tick mobs, which will calculate difficulty when appropriate and apply changes
        if (entity instanceof MobEntity) {
            entity.getCapability(MobCapability.INSTANCE).ifPresent(affected -> {
                affected.tick((MobEntity) entity);
            });
        }
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.getCapability(PlayerDataCapability.INSTANCE).ifPresent(affected -> {
                affected.tick((PlayerEntity) player);
            });
        }
    }

    @SubscribeEvent
    public static void onLivingExperienceDropEvent(LivingExperienceDropEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof MobEntity) {
            entity.getCapability(MobCapability.INSTANCE).ifPresent(affected -> {
                event.setDroppedExperience(event.getOriginalExperience() * affected.getLevel());
            });
        }
    }
}
