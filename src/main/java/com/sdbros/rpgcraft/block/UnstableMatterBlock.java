package com.sdbros.rpgcraft.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class UnstableMatterBlock extends Block {

    UnstableMatterBlock(Block.Properties properties) {
        super(properties);
    }

    private void explode(World worldIn, BlockPos pos, PlayerEntity player) {
        worldIn.createExplosion(null, pos.getX(), pos.getY() + (double) (player.getHeight() / 16.0F), pos.getZ(), 4.0F, Explosion.Mode.BREAK);
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
     * this block
     */
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote() && !player.isCreative()) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                IFluidState ifluidstate = worldIn.getFluidState(pos.offset(direction));
                if (!ifluidstate.isTagged(FluidTags.WATER)) {
                    explode(worldIn, pos, player);
                } else System.out.println("Unstable Matter in Water");
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

}


