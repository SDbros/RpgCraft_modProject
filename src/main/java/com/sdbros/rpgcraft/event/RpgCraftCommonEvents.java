package com.sdbros.rpgcraft.event;


import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.capability.PlayerDataCapability;
import com.sdbros.rpgcraft.util.ModifierHandler;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = RpgCraft.MOD_ID)
public final class RpgCraftCommonEvents {
    private RpgCraftCommonEvents() {
    }

    // Sets the PlayerHealth when they respawn
    @SubscribeEvent
    public static void onPlayerRespawn(EntityJoinWorldEvent event) {

        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            ModifierHandler.addMaxHealth(player, -10, AttributeModifier.Operation.ADDITION);

        }
    }
    

}
