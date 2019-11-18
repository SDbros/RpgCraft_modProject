package com.sdbros.rpgcraft;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(RpgCraft.MOD_ID)
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
