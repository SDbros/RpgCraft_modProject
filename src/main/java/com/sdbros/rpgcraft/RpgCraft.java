package com.sdbros.rpgcraft;

import com.sdbros.rpgcraft.blocks.*;
import com.sdbros.rpgcraft.items.FirstItem;
import com.sdbros.rpgcraft.setup.ClientProxy;
import com.sdbros.rpgcraft.setup.IProxy;
import com.sdbros.rpgcraft.setup.ModSetup;
import com.sdbros.rpgcraft.setup.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("rpgcraft")
public class RpgCraft {

    public static final String MODID = "rpgcraft" ;
    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(),() -> () -> new ServerProxy());

    public static ModSetup setup = new ModSetup();

    private static final Logger LOGGER = LogManager.getLogger();

    public RpgCraft() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        setup.init();
        proxy.init();
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)

    @Mod.EventBusSubscriber
    public static class MyForgeEventHandler {

        protected static final UUID MAX_HEALTH_UUID = UUID.randomUUID();

        @SubscribeEvent
        public static void pickupItem(PlayerPickupXpEvent event) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("MAX_HEALTH_UUID",-0.5, AttributeModifier.Operation.byId(1)));
            System.out.println(player.getHealth());
            System.out.println("Item picked up!");
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new FirstBlock());
           event.getRegistry().register(new BackPack());
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            Item.Properties properties = new Item.Properties()
                    .group(setup.itemGroup);
            event.getRegistry().register(new BlockItem(ModBlocks.FIRSTBLOCK, properties).setRegistryName("firstblock"));
            event.getRegistry().register(new BlockItem(ModBlocks.BACKPACK, properties).setRegistryName("backpack"));
            event.getRegistry().register(new FirstItem());
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event){
            event.getRegistry().register(TileEntityType.Builder.create(() -> new BackPackTile(),ModBlocks.BACKPACK).build(null).setRegistryName("backpack"));
        }

        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new BackPackContainer(windowId, RpgCraft.proxy.getClientWorld(),  pos, inv, RpgCraft.proxy.getClientPlayer());
            }).setRegistryName("backpack"));
        }
     }

}
