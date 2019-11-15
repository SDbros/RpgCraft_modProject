package com.sdbros.rpgcraft;

import com.sdbros.rpgcraft.init.*;
import com.sdbros.rpgcraft.world.OreGeneration;
import com.sdbros.rpgcraft.world.features.FeatureManager;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Feature.class, ModFeatures::registerFeatures);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(EntityType.class, ModEntities::registerTypes);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, ModEntities::registerEntitySpawnEggs);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Biome.class, ModBiomes::registerBiomes);

        // Other events
        MinecraftForge.EVENT_BUS.register(this);
        FeatureManager.init();
        ModEntities.registerEntityWorldSpawns();
        OreGeneration.generateOres();
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        RpgCraft.LOGGER.info("Setup method registered.");
    }

    private static void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private static void processIMC(final InterModProcessEvent event) {
    }

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event) {
        ModCommands.registerCommands(event.getCommandDispatcher());
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

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RpgCraftEventHandler {

        @SubscribeEvent
        public static void onDimensionModRegistry(RegistryEvent.Register<ModDimension> event) {
            event.getRegistry().register(ModDimensions.dimension);
            DimensionManager.registerDimension(RpgCraft.getId("unstable_dimension"), ModDimensions.dimension, null, true);
        }

        @SubscribeEvent
        public static void onChunkGeneratorTypeRegistry(RegistryEvent.Register<ChunkGeneratorType<?, ?>> event) {
            event.getRegistry().register(ModBiomes.generatorType.setRegistryName(RpgCraft.MOD_ID, "generator"));
        }

        @SubscribeEvent
        public static void onBiomeProviderTypeRegistry(RegistryEvent.Register<BiomeProviderType<?, ?>> event) {
            event.getRegistry().register(ModBiomes.biomeProviderType.setRegistryName(RpgCraft.MOD_ID, "generator"));
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
            RpgCraft.LOGGER.debug("RpgCraft SideProxy.Server init");
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);
        }

        private void serverSetup(FMLDedicatedServerSetupEvent event) {
            RpgCraft.LOGGER.debug("RpgCraft serverSetup");
        }
    }
}