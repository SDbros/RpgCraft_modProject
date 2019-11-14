package com.sdbros.rpgcraft.world.gen.structures;

import com.google.common.collect.ImmutableMap;
import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.init.ModFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTables;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.Random;

@ParametersAreNonnullByDefault
public abstract class BrokenTowerPieces extends TemplateStructurePiece {

    private static final ResourceLocation TOWER = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "/data/rpgcraft/structures/broken_tower.nbt");
    private static final Map<ResourceLocation, BlockPos> RESOURCE_LOCATION_BLOCK_POS = ImmutableMap.of(TOWER, new BlockPos(3, 5, 5));

    public BrokenTowerPieces(IStructurePieceType structurePieceTypeIn, int componentTypeIn) {
        super(structurePieceTypeIn, componentTypeIn);
    }

    public BrokenTowerPieces(IStructurePieceType structurePieceTypeIn, CompoundNBT nbt) {
        super(structurePieceTypeIn, nbt);
    }

    public static void init(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> structurePieces) {
        structurePieces.add(new BrokenTowerPieces.Piece(templateManager, TOWER, pos, rotation, 0));
    }

    public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation resourceLocation;
        private final Rotation rotation;

        Piece(TemplateManager templateManager, ResourceLocation resourceLocation, BlockPos pos, Rotation rotation, int yOffset) {
            super(ModFeatures.TOWER_PIECE, 0);
            if (template == null) {
            }
            this.resourceLocation = resourceLocation;
            BlockPos blockpos = BrokenTowerPieces.RESOURCE_LOCATION_BLOCK_POS.get(resourceLocation);
            this.templatePosition = pos.add(blockpos.getX(), blockpos.getY() - yOffset, blockpos.getZ());
            this.rotation = rotation;
            this.func_207614_a(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(ModFeatures.TOWER_PIECE, compoundNBT);
            this.resourceLocation = new ResourceLocation(compoundNBT.getString("Template"));
            this.rotation = Rotation.valueOf(compoundNBT.getString("Rot"));
            this.func_207614_a(templateManager);
        }

        //todo figure out what func_207614_a is.
        private void func_207614_a(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(BrokenTowerPieces.RESOURCE_LOCATION_BLOCK_POS.get(this.resourceLocation)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementsettings);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Template", this.resourceLocation.toString());
            tagCompound.putString("Rot", this.rotation.name());
        }

        protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
            if ("chest".equals(function)) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                TileEntity tileentity = worldIn.getTileEntity(pos.down());
                if (tileentity instanceof ChestTileEntity) {
                    ((ChestTileEntity) tileentity).setLootTable(LootTables.CHESTS_IGLOO_CHEST, rand.nextLong());
                }

            }
        }
    }
}
