package com.sdbros.rpgcraft.world.structures;

import com.sdbros.rpgcraft.init.ModWorld;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class BrokenTowerPieces extends StructurePiece {

    public BrokenTowerPieces(IStructurePieceType structurePiece, int id) {
        super(structurePiece, id);
    }

    public BrokenTowerPieces(IStructurePieceType structurePiece, CompoundNBT nbt) {
        super(structurePiece, nbt);
    }

    @Override
    public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
        return true;
    }

    @Override
    protected void readAdditional(CompoundNBT tagCompound) {
    }


    public static class Tower extends BrokenTowerPieces {

        public Tower() {
            super(ModWorld.StructurePieceTypes.BROKENTOWERPIECE, 0);
        }

        public Tower(TemplateManager templateManager, CompoundNBT nbt) {
            super(ModWorld.StructurePieceTypes.BROKENTOWERPIECE, nbt);
        }

        @Override
        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
            this.setBlockState(worldIn, Blocks.DIRT.getDefaultState(), 2, 1, 2, structureBoundingBoxIn);
            return true;
        }

        public void buildComponent(StructurePiece component, List<StructurePiece> listIn, Random rand) {
        }
    }
}