package com.sdbros.rpgcraft.event;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.capability.MobDataCapability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@Mod.EventBusSubscriber(modid = RpgCraft.MOD_ID)
public class LevelEvents {

    public static final Marker MARKER = MarkerManager.getMarker("Level");

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.world.isRemote) return;

        // Tick mobs, which will calculate difficulty when appropriate and apply changes
        if (entity instanceof MobEntity) {
            entity.getCapability(MobDataCapability.INSTANCE).ifPresent(affected -> {
                affected.tick((MobEntity) entity);
            });
        }
    }
}
