package com.sdbros.rpgcraft.world.gen.structures;

import com.google.common.collect.ImmutableMap;
import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.init.ModFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
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
public class BrokenTowerPieces{

    private static final ResourceLocation TOWER = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "/data/rpgcraft/structures/broken_tower.nbt");
    private static final Map<ResourceLocation, BlockPos> field_207621_d = ImmutableMap.of(TOWER, new BlockPos(3, 5, 5));
    private static final Map<ResourceLocation, BlockPos> RESOURCE_LOCATION_BLOCK_POS = ImmutableMap.of(TOWER, BlockPos.ZERO);


    public static void init(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> structurePieces, Random random, NoFeatureConfig noFeatureConfig) {
        structurePieces.add(new BrokenTowerPieces.Piece(templateManager, TOWER, pos, rotation, 0));
    }

    public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation resourceLocation;
        private final Rotation rotation;

        Piece(TemplateManager templateManager, ResourceLocation resourceLocation, BlockPos pos, Rotation rotation, int yOffset) {
            super(ModFeatures.TOWER_PIECE, 0);
            this.resourceLocation = resourceLocation;
            System.out.println("resourceLocation == " + resourceLocation);
            BlockPos blockpos = BrokenTowerPieces.RESOURCE_LOCATION_BLOCK_POS.get(resourceLocation);
            this.templatePosition = pos.add(blockpos.getX(), blockpos.getY() - yOffset, blockpos.getZ());
            this.rotation = rotation;
            this.func_207614_a(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(ModFeatures.TOWER_PIECE, compoundNBT);
            System.out.println("TEMPLATE = " + template);
            this.resourceLocation = new ResourceLocation(compoundNBT.getString("Template"));
            this.rotation = Rotation.valueOf(compoundNBT.getString("Rot"));
            this.func_207614_a(templateManager);
        }

        //todo figure out what func_207614_a is.
        private void func_207614_a(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(BrokenTowerPieces.field_207621_d.get(this.resourceLocation)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
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

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
         * the end, it adds Fences...
         */
        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPosIn) {
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(BrokenTowerPieces.field_207621_d.get(this.resourceLocation)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            BlockPos blockpos = BrokenTowerPieces.RESOURCE_LOCATION_BLOCK_POS.get(this.resourceLocation);
            BlockPos blockpos1 = this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(3 - blockpos.getX(), 0, 0 - blockpos.getZ())));
            int i = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
            BlockPos blockpos2 = this.templatePosition;
            this.templatePosition = this.templatePosition.add(0, i - 90 - 1, 0);
            boolean flag = super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn, chunkPosIn);
            if (this.resourceLocation.equals(BrokenTowerPieces.TOWER)) {
                BlockPos blockpos3 = this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(3, 0, 5)));
                BlockState blockstate = worldIn.getBlockState(blockpos3.down());
                if (!blockstate.isAir() && blockstate.getBlock() != Blocks.LADDER) {
                    worldIn.setBlockState(blockpos3, Blocks.SNOW_BLOCK.getDefaultState(), 3);
                }
            }

            this.templatePosition = blockpos2;
            return flag;
        }
    }
}
