package com.sdbros.rpgcraft;

import com.sdbros.rpgcraft.init.ModBlocks;
import com.sdbros.rpgcraft.init.ModItems;
import com.sdbros.rpgcraft.world.ModWorldFeatures;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
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

    @SubscribeEvent
    public void serverStarting(FMLServerStartingEvent event) {
        //SimpleGiveCommand.register(event.getCommandDispatcher());
    }

    static class Client extends SideProxy {
        Client() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Client::clientSetup);
        }

        private static void clientSetup(FMLClientSetupEvent event) {
        }
    }

    static class Server extends SideProxy {
        Server() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Server::serverSetup);
        }

        private static void serverSetup(FMLDedicatedServerSetupEvent event) {
        }
    }
}