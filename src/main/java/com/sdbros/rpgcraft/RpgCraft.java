package com.sdbros.rpgcraft;

import com.sdbros.rpgcraft.init.*;
import com.sdbros.rpgcraft.world.ModWorldType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RpgCraft.MOD_ID)
public class RpgCraft {

    public static final String MOD_ID = "rpgcraft";
    public static final String RESOURCE_PREFIX = MOD_ID + ":";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.copperIngot);
        }
    };

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event) {
        ModCommands.registerCommands(event.getCommandDispatcher());
    }

    public RpgCraft() {
        DistExecutor.runForDist(
                () -> () -> new SideProxy.Client(),
                () -> () -> new SideProxy.Server()
        );
    }

    @Nonnull
    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
