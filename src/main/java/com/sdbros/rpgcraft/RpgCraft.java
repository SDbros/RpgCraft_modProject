package com.sdbros.rpgcraft;

import com.sdbros.rpgcraft.capability.EntityAbilityData;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.sdbros.rpgcraft.RpgCraft.MOD_ID;
import static com.sdbros.rpgcraft.capability.Abilities.*;
import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;


@Mod(MOD_ID)
@Mod.EventBusSubscriber(bus = MOD, modid = MOD_ID)
public class RpgCraft {
    public static final String MOD_ID = "rpgcraft";
    public static final String MOD_NAME = "RpgCraft";
    public static final String VERSION = "0.1.0";
    public static final String RESOURCE_PREFIX = MOD_ID + ":";

    private static RpgCraft INSTANCE;
    public static SideProxy PROXY;

    public static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Blocks.PUMPKIN);
        }
    };

    public RpgCraft() {
        INSTANCE = this;
        PROXY = DistExecutor.runForDist(
                () -> SideProxy.Client::new,
                () -> SideProxy.Server::new
        );
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
