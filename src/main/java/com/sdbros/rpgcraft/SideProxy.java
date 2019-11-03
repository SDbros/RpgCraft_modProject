package com.sdbros.rpgcraft;

import com.sdbros.rpgcraft.init.ModBlocks;
import com.sdbros.rpgcraft.init.ModEntities;
import com.sdbros.rpgcraft.init.ModItems;
import com.sdbros.rpgcraft.world.ModWorldFeatures;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


class SideProxy {
    SideProxy() {
        // Life-cycle events
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, ModBlocks::registerAll);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, ModItems::registerAll);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(EntityType.class, ModEntities::registerTypes);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, ModEntities::registerEntitySpawnEggs);

        // Other events
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        RpgCraft.LOGGER.debug("commonSetup for RpgCraft");

        DeferredWorkQueue.runLater(ModWorldFeatures::addFeaturesToBiomes);
    }

    private static void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private static void processIMC(final InterModProcessEvent event) {
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

        @SubscribeEvent
        public void serverStarting(FMLServerStartingEvent event) {
            //SimpleGiveCommand.register(event.getCommandDispatcher());
        }

    }


    static class Client extends SideProxy {
        Client() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        }

        private void clientSetup(FMLClientSetupEvent event) {
            RpgCraft.LOGGER.debug("RpgCraft clientSetup");
            ModEntities.registerRenderers(event);
        }
    }


    static class Server extends SideProxy {
        Server() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Server::serverSetup);
        }

        private static void serverSetup(FMLDedicatedServerSetupEvent event) {
            RpgCraft.LOGGER.debug("RpgCraft serverSetup");
        }
    }
}