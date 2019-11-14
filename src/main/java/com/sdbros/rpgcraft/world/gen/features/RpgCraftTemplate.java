package com.sdbros.rpgcraft.world.gen.features;

import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue;

public class RpgCraftTemplate extends Template {

        private static final Logger LOGGER = LogManager.getLogger(RpgCraftTemplate.class);
        private ResourceLocation lootTable;

        @Override
        public boolean addBlocksToWorld(IWorld worldIn, BlockPos pos, PlacementSettings placementIn, int flags) {
            if (!super.addBlocksToWorld(worldIn, pos, placementIn, flags)) {
                return false;
            }
            if (lootTable != null) {
                boolean flag = false;
                List<Template.BlockInfo> blocks = getPrivateValue(Template.class, this, "field_204769_a");
                for (Template.BlockInfo b : blocks) {
                    if (b.state.getBlock() instanceof ChestBlock) {
                        TileEntity t = worldIn.getTileEntity(b.pos);
                        if (t instanceof ChestTileEntity) {
                            ((ChestTileEntity) t).setLootTable(lootTable, worldIn.getSeed());
                            flag = true;
                        }
                    }
                }
                if (!flag) {
                    LOGGER.warn("Loot Table ({}) specified but no chest found", lootTable);
                }
                return true;
            }
            return false;
        }

        public void setLootTable(ResourceLocation lootTable) {
            this.lootTable = lootTable;
        }
}

