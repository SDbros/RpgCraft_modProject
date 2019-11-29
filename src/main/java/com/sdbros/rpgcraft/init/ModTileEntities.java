package com.sdbros.rpgcraft.init;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.block.BlocksRC;
import com.sdbros.rpgcraft.block.radioTower.RadioTowerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@ObjectHolder(RpgCraft.MOD_ID)
public class ModTileEntities {

//    public static @Nonnull
//    <T> T getNull() {
//        return null;
//    }

    public static final TileEntityType<RadioTowerTileEntity> RADIO_TOWER_TILE = null;

    public static void registerTiles(RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(create("radio_tower", RadioTowerTileEntity::new, BlocksRC.RADIO_TOWER));
    }

    private static <T extends TileEntity> TileEntityType<?> create(String id, Supplier<? extends T> factoryIn, Block... blocks) {
        return TileEntityType.Builder.create(factoryIn, blocks).build(null).setRegistryName(RpgCraft.MOD_ID, id);
    }
}