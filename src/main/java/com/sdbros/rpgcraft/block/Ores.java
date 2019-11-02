package com.sdbros.rpgcraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.Item;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;


import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;

public enum Ores implements IStringSerializable {
    COPPER(() -> new OreBlockRC(null, 2, Block.Properties.create(Material.ROCK).hardnessAndResistance(3, 12)) {
        @Override
        public int getExpRandom() {
            return 0;
        }
    });

    private final LazyLoadBase<OreBlockRC> block;

    Ores(Supplier<OreBlockRC> blockSupplier) {
        this.block = new LazyLoadBase(blockSupplier);
    }

    public OreBlockRC getBlock() {
        return this.block.getValue();
    }

    public Block asBlock() {
        return getBlock();
    }

    public Item asItem() {
        return asBlock().asItem();
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT) + "_ore";
    }

    public static abstract class OreBlock extends OreBlockRC {

        static final BooleanProperty LIT = BlockStateProperties.LIT;

        OreBlock(IItemProvider droppedItem, int harvestLevel, Properties builder) {
            super(droppedItem, harvestLevel, builder.harvestTool(ToolType.PICKAXE));
            setDefaultState(getDefaultState().with(LIT, false));
        }

        @Override
        public int getLightValue(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
            return state.get(LIT) ? 9 : 0;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
            if (state.get(LIT)) {
                worldIn.setBlockState(pos, state.with(LIT, false), 3);
            }
        }

        @Override
        public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
            if (stateIn.get(LIT)) {
                spawnParticles(worldIn, pos);
            }
        }

        private static void spawnParticles(World world, BlockPos pos) {
            Random random = world.rand;
            for (Direction direction : Direction.values()) {
                BlockPos blockpos = pos.offset(direction);
                if (!world.getBlockState(blockpos).isOpaqueCube(world, blockpos)) {
                    Direction.Axis axis = direction.getAxis();
                    double d1 = axis == Direction.Axis.X ? 0.5D + 0.5625D * (double) direction.getXOffset() : (double) random.nextFloat();
                    double d2 = axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double) direction.getYOffset() : (double) random.nextFloat();
                    double d3 = axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double) direction.getZOffset() : (double) random.nextFloat();
                    world.addParticle(RedstoneParticleData.REDSTONE_DUST, (double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
                }
            }
        }

        @Override
        protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
            builder.add(LIT);
        }
    }
}