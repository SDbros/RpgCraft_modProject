package com.sdbros.rpgcraft.entity.mobs;

import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreakBlockGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class LumberjackEntity extends ZombieEntity {
    public LumberjackEntity(EntityType<? extends ZombieEntity> type, World worldIn) {
        super(type, worldIn);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(10, new AttackWoodGoal(this, 3.0D, 15));
        super.registerGoals();
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(45.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.3F);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
    }

    @Override
    protected void applyEntityAI() {
        super.applyEntityAI();
        this.goalSelector.addGoal(10, new AttackWoodGoal(this, 3.0D, 15));
    }

    @Override
    protected boolean shouldBurnInDay() {
        return false;
    }

    public static boolean canSpawnAt(EntityType<RedCreeperEntity> type, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL;
    }

    static class AttackWoodGoal extends BreakBlockGoal {
        AttackWoodGoal(CreatureEntity creatureIn, double speed, int yMax) {
            super(Blocks.OAK_LOG, creatureIn, speed, yMax);
        }

        public void playBreakingSound(IWorld worldIn, BlockPos pos) {
            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.HOSTILE, 0.5F, 0.9F + 0.1F);
        }

        public void playBrokenSound(World worldIn, BlockPos pos) {
            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + worldIn.rand.nextFloat() * 0.2F);
        }

        public double getTargetDistanceSq() {
            return 1.7D;
        }
    }
}
