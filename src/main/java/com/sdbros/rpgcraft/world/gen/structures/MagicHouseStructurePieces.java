package com.sdbros.rpgcraft.world.gen.structures;

import com.google.common.collect.ImmutableMap;
import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.init.ModFeatures;
import net.minecraft.nbt.CompoundNBT;
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
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.Random;

@ParametersAreNonnullByDefault
public class MagicHouseStructurePieces {

    private static final ResourceLocation MAGIC_HOUSE_RESOURCE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "magic_house");
    private static final Map<ResourceLocation, BlockPos> RESOURCE_LOCATION_BLOCK_POS = ImmutableMap.of(MAGIC_HOUSE_RESOURCE, BlockPos.ZERO);


    public static void init(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> structurePieces) {
        structurePieces.add(new Piece(templateManager, MAGIC_HOUSE_RESOURCE, pos, rotation, 0, ModFeatures.MAGIC_HOUSE));
    }

    public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation resourceLocation;
        private final Rotation rotation;

        Piece(TemplateManager templateManager, ResourceLocation resourceLocation, BlockPos pos, Rotation rotation, int yOffset, IStructurePieceType piece) {
            super(piece, 0);
            this.resourceLocation = resourceLocation;
            BlockPos blockpos = RESOURCE_LOCATION_BLOCK_POS.get(resourceLocation);
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
                    .setCenterOffset(RESOURCE_LOCATION_BLOCK_POS.get(this.resourceLocation))
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

        protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
            //todo add chest loot tables here
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
         * the end, it adds Fences...
         */
        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPosIn) {
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(RESOURCE_LOCATION_BLOCK_POS.get(this.resourceLocation)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            BlockPos blockpos = RESOURCE_LOCATION_BLOCK_POS.get(this.resourceLocation);
            BlockPos blockpos1 = this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(blockpos.getX(), 0, blockpos.getZ())));
            int i;
            if (BiomeDictionary.hasType(worldIn.getBiome(blockpos1), BiomeDictionary.Type.WATER)) {
                i = worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, blockpos1.getX(), blockpos1.getZ());
            } else {
                i = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
            }
            BlockPos blockpos2 = this.templatePosition;
            this.templatePosition = this.templatePosition.add(0, i - 90, 0);
            boolean flag = super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn, chunkPosIn);


            this.templatePosition = blockpos2;
            return flag;
        }
    }

}
