package com.sdbros.rpgcraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class UnstableMatterBlock extends Block {

    public UnstableMatterBlock(Block.Properties properties) {
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
            explode(worldIn, pos, player);
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    /**
     * Return whether this block can drop from an explosion.
     */
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

}


