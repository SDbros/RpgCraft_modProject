package com.sdbros.rpgcraft;

import com.sdbros.rpgcraft.init.*;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.UUID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RpgCraft.MOD_ID)
public class RpgCraft {

    public static final String MOD_ID = "rpgcraft";

    public static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.copperIngot);
        }
    };

    public RpgCraft() {
        // Create proxy instance. DistExecutor.runForDist also returns the created object, so you
        // could store that in a variable if you need it.
        // We cannot use method references here because it could load classes on invalid sides.
        //noinspection Convert2MethodRef
        DistExecutor.runForDist(
                () -> () -> new SideProxy.Client(),
                () -> () -> new SideProxy.Server()
        );
    }

    @Mod.EventBusSubscriber
    public static class MyForgeEventHandler {

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

    @Nonnull
    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
