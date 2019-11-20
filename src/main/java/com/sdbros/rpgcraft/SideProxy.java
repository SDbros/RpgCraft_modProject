package com.sdbros.rpgcraft;

import com.sdbros.rpgcraft.capability.PlayerDataCapability;
import com.sdbros.rpgcraft.init.*;
import com.sdbros.rpgcraft.world.OreGeneration;
import com.sdbros.rpgcraft.world.features.FeatureManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nullable;

class SideProxy {
    SideProxy() {
        FeatureManager.init();

        // Life-cycle events
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        MinecraftForge.EVENT_BUS.addListener(this::serverAboutToStart);

        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, ModBlocks::registerAll);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, ModItems::registerAll);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Feature.class, ModFeatures::registerFeatures);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(EntityType.class, ModEntities::registerTypes);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, ModEntities::registerEntitySpawnEggs);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Biome.class, ModBiomes::registerBiomes);


    }

    private void commonSetup(FMLCommonSetupEvent event) {
        RpgCraft.LOGGER.info("Setup method registered.");
        PlayerDataCapability.register();
        ModEntities.registerSpawns();
        OreGeneration.register();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }

    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        ModCommands.registerAll(event.getServer().getCommandManager().getDispatcher());
    }

    @Nullable
    public PlayerEntity getClientPlayer() {
        return null;
    }

    static class Client extends SideProxy {
        Client() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        }

        private void clientSetup(FMLClientSetupEvent event) {
            RpgCraft.LOGGER.debug("RpgCraft clientSetup");
            ModEntities.registerRenderers(event);
        }

        @Nullable
        @Override
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