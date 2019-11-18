package com.sdbros.rpgcraft.event;

import com.sdbros.rpgcraft.RpgCraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RpgCraft.MOD_ID)
public final class RpgCraftCommonEvents {
    private RpgCraftCommonEvents() {
    }

    // Sets the PlayerHealth to 10 when they respawn
    @SubscribeEvent
    public static void changePlayerHealthOnSpawn(EntityJoinWorldEvent event) {

        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (player.getHealth() > 10) {
                System.out.println("PlayerHealth set to " + player.getHealth());
                player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("MAX_HEALTH_UUID", -0.5, AttributeModifier.Operation.byId(1)));
                player.setHealth(10);
                System.out.println("PlayerHealth set to " + player.getHealth());
            }
        }
    }
}
