package com.sdbros.rpgcraft.world.gen.structures;

import com.google.common.collect.ImmutableMap;
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
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.NoFeatureConfig;
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

    private static final ResourceLocation TOWER_PIECE = new ResourceLocation("broken_tower");
    private static final Map<ResourceLocation, BlockPos> RESOURCE_LOCATION_BLOCK_POS = ImmutableMap.of(TOWER_PIECE, new BlockPos(3, 5, 5));

    public BrokenTowerPieces(IStructurePieceType structurePieceTypeIn, int componentTypeIn) {
        super(structurePieceTypeIn, componentTypeIn);
    }

    public BrokenTowerPieces(IStructurePieceType structurePieceTypeIn, CompoundNBT nbt) {
        super(structurePieceTypeIn, nbt);
    }

    public static void init(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> structurePieces, Random random, NoFeatureConfig noFeatureConfig) {
//        if (random.nextDouble() < 0.5D) {
//            int i = random.nextInt(8) + 4;
//            structurePieces.add(new IglooPieces.Piece(templateManager, field_202594_g, pos, rotation, i * 3));
//
//            for(int j = 0; j < i - 1; ++j) {
//                structurePieces.add(new IglooPieces.Piece(templateManager, field_202593_f, pos, rotation, j * 3));
//            }
//        }

        structurePieces.add(new BrokenTowerPieces.Piece(templateManager, TOWER_PIECE, pos, rotation, 0));
    }

    public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation resourceLocation;
        private final Rotation rotation;

        public Piece(TemplateManager templateManager, ResourceLocation resourceLocation, BlockPos pos, Rotation rotation, int p_i49313_5_) {
            super(ModFeatures.TOWER, 0);
            this.resourceLocation = resourceLocation;
            BlockPos blockpos = BrokenTowerPieces.RESOURCE_LOCATION_BLOCK_POS.get(resourceLocation);
            this.templatePosition = pos.add(blockpos.getX(), blockpos.getY() - p_i49313_5_, blockpos.getZ());
            this.rotation = rotation;
            this.func_207614_a(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(ModFeatures.TOWER, compoundNBT);
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
                    ((ChestTileEntity)tileentity).setLootTable(LootTables.CHESTS_IGLOO_CHEST, rand.nextLong());
                }

            }
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
         * the end, it adds Fences...
         */
//        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPosIn) {
//            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(BrokenTowerPieces.field_207621_d.get(this.resourceLocation)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
//            BlockPos blockpos = BrokenTowerPieces.field_207621_d.get(this.resourceLocation);
//            BlockPos blockpos1 = this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(3 - blockpos.getX(), 0, 0 - blockpos.getZ())));
//            int i = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
//            BlockPos blockpos2 = this.templatePosition;
//            this.templatePosition = this.templatePosition.add(0, i - 90 - 1, 0);
//            boolean flag = super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn, chunkPosIn);
//            if (this.resourceLocation.equals(BrokenTowerPieces.field_207621_d)) {
//                BlockPos blockpos3 = this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(3, 0, 5)));
//                BlockState blockstate = worldIn.getBlockState(blockpos3.down());
//                if (!blockstate.isAir() && blockstate.getBlock() != Blocks.LADDER) {
//                    worldIn.setBlockState(blockpos3, Blocks.SNOW_BLOCK.getDefaultState(), 3);
//                }
//            }
//
//            this.templatePosition = blockpos2;
//            return flag;
//        }
    }
}
