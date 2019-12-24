package com.sdbros.rpgcraft.structures;

import com.google.common.collect.ImmutableMap;
import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.init.ModEntities;
import com.sdbros.rpgcraft.init.ModFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.Random;

@ParametersAreNonnullByDefault
public class MagicHouseStructurePiece {

    private static final ResourceLocation MAGIC_HOUSE_RESOURCE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "magic_house");


    public static void init(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> structurePieces) {
        structurePieces.add(new Piece(templateManager, MAGIC_HOUSE_RESOURCE, pos, rotation, 0, ModFeatures.MAGIC_HOUSE));
    }

    public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation resourceLocation;
        private final Rotation rotation;

        Piece(TemplateManager templateManager, ResourceLocation resourceLocation, BlockPos pos, Rotation rotation, int yOffset, IStructurePieceType piece) {
            super(piece, 0);
            this.resourceLocation = resourceLocation;
            BlockPos blockpos = BlockPos.ZERO;
            this.templatePosition = pos.add(blockpos.getX(), blockpos.getY() - yOffset, blockpos.getZ());
            this.rotation = rotation;
            this.func_207614_a(templateManager);
        }

        //Rot == Rotation
        public Piece(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(ModFeatures.MAGIC_HOUSE, compoundNBT);
            this.resourceLocation = new ResourceLocation(compoundNBT.getString("Template"));
            this.rotation = Rotation.valueOf(compoundNBT.getString("Rot"));
            this.func_207614_a(templateManager);
        }

        private void func_207614_a(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation)
                    .setMirror(Mirror.NONE)
                    .addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementsettings);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         * Rot == Rotation
         */
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Template", this.resourceLocation.toString());
            tagCompound.putString("Rot", this.rotation.name());
        }

        protected void handleDataMarker(String name, BlockPos blockPos, IWorld world, Random random, MutableBoundingBox mutableBoundingBox) {
            if (name.equals("boss")) {
                AbstractIllagerEntity abstractIllagerEntity = ModEntities.CRAZED_SUMMONER.getValue().create(world.getWorld());
                abstractIllagerEntity.enablePersistence();
                abstractIllagerEntity.moveToBlockPosAndAngles(blockPos, 0.0F, 0.0F);
                abstractIllagerEntity.onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(abstractIllagerEntity)), SpawnReason.STRUCTURE, (ILivingEntityData) null, (CompoundNBT) null);
                world.addEntity(abstractIllagerEntity);
            }
            world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 2);
        }


        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
         * the end, it adds Fences...
         */
        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPosIn) {
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
            BlockPos blockpos = this.templatePosition.add(Template.transformedBlockPos(placementsettings, BlockPos.ZERO));

            int i;
            if (BiomeDictionary.hasType(worldIn.getBiome(chunkPosIn.asBlockPos()), BiomeDictionary.Type.WATER)) {
                i = worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, blockpos.getX(), blockpos.getZ());
            } else {
                i = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos.getX(), blockpos.getZ());
            }

            this.templatePosition = this.templatePosition.add(0, i - 90, 0);
            return super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn, chunkPosIn);
        }
    }
}
