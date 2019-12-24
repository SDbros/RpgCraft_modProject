package com.sdbros.rpgcraft;

import com.sdbros.rpgcraft.capabilities.ItemCapability;
import com.sdbros.rpgcraft.capabilities.MobCapability;
import com.sdbros.rpgcraft.capabilities.PlayerCapability;
import com.sdbros.rpgcraft.client.renderer.entity.*;
import com.sdbros.rpgcraft.entity.*;
import com.sdbros.rpgcraft.entity.boss.CrazedSummonerEntity;
import com.sdbros.rpgcraft.init.*;
import com.sdbros.rpgcraft.world.features.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nullable;

class SideProxy {
    SideProxy() {
        // Life-cycle events
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        MinecraftForge.EVENT_BUS.addListener(this::serverAboutToStart);

        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, ModBlocks::registerAll);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, ModItems::registerAll);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Feature.class, ModFeatures::registerFeatures);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(EntityType.class, ModEntities::registerEntityTypes);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, ModTileEntities::registerTiles);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, ModEntities::registerEntitySpawnEggs);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Biome.class, ModBiomes::registerBiomes);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        RpgCraft.LOGGER.info("Setup method registered.");
        MobCapability.register();
        PlayerCapability.register();
        ItemCapability.register();
        RpgCraftBiomeFeatures.init();
        FeatureManager.init();

    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }

    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        ModCommands.registerAll(event.getServer().getCommandManager().getDispatcher());
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

            //Mobs
            RenderingRegistry.registerEntityRenderingHandler(MutantZombieEntity.class, MutantZombieRender::new);
            RenderingRegistry.registerEntityRenderingHandler(RedCreeperEntity.class, RedCreeperRender::new);
            RenderingRegistry.registerEntityRenderingHandler(ClusterCreeperEntity.class, ClusterCreeperRender::new);
            RenderingRegistry.registerEntityRenderingHandler(LumberjackEntity.class, LumberjackRender::new);
            RenderingRegistry.registerEntityRenderingHandler(CrazedSummonerEntity.class, CrazedSummonerRender::new);
        }

        @Nullable
        public PlayerEntity getClientPlayer() {
            return Minecraft.getInstance().player;
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