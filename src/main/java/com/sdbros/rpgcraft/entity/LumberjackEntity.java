package com.sdbros.rpgcraft.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreakBlockGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Random;

import static net.minecraft.world.biome.Biomes.FOREST;

public class LumberjackEntity extends MonsterEntity {
    public LumberjackEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
    }

    //todo create new goals
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AttackWoodGoal(this, 1.0D, 3));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    //todo fix axe not spawning
    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_AXE));
    }

    public static boolean canSpawnAt(EntityType<LumberjackEntity> type, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getBiome(pos) == BiomeDictionary.getTypes(FOREST);
    }

    //todo create new method for harvesting wood
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
            return 1.14D;
        }
    }
}
