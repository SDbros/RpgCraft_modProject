package com.sdbros.rpgcraft.entity.boss;

import com.sdbros.rpgcraft.capabilities.MobCapability;
import com.sdbros.rpgcraft.entity.ClusterCreeperEntity;
import com.sdbros.rpgcraft.entity.MutantZombieEntity;
import com.sdbros.rpgcraft.init.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static com.sdbros.rpgcraft.capabilities.Abilities.*;

public class CrazedSummonerEntity extends SpellcastingIllagerEntity implements IRangedAttackMob {

    private final ServerBossInfo bossInfo = (ServerBossInfo) (new ServerBossInfo(this.getDisplayName(), BossInfo.Color.GREEN, BossInfo.Overlay.PROGRESS)).setCreateFog(true);


    public CrazedSummonerEntity(EntityType<? extends SpellcastingIllagerEntity> type, World worldIn) {
        super(type, worldIn);
        this.experienceValue = 25;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new CrazedSummonerEntity.CastingSpellGoal());
        this.goalSelector.addGoal(4, new CrazedSummonerEntity.SummonSpellGoal());
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, PlayerEntity.class, 6.0F, 0.6D, 1.0D));
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, IronGolemEntity.class, 6.0F, 0.5D, 0.7D));
        this.goalSelector.addGoal(6, new RangedBowAttackGoal<>(this, 0.5D, 15, 15.0F));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(9, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true)).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false)).setUnseenMemoryTicks(300));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(18.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
    }

    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BOW));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    protected void registerData() {
        super.registerData();
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
    }

    public EntitySize getSize(Pose poseIn) {
        return super.getSize(poseIn).scale(0.255F * 6);
    }

    public void setCustomName(@Nullable ITextComponent name) {
        super.setCustomName(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    protected void updateAITasks() {
        super.updateAITasks();
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

    }

    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in order
     * to view its associated boss bar.
     */
    public void addTrackingPlayer(ServerPlayerEntity player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
     * more information on tracking.
     */
    public void removeTrackingPlayer(ServerPlayerEntity player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        ItemEntity itementity = this.entityDropItem(Items.NETHER_STAR);
        if (itementity != null) {
            itementity.setNoDespawn();
        }

    }

    /**
     * Returns whether this Entity is on the same team as the given Entity.
     */
    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    public SoundEvent getRaidLossSound() {
        return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ILLUSIONER_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ILLUSIONER_HURT;
    }

    protected SoundEvent getSpellSound() {
        return SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL;
    }

    public void func_213660_a(int p_213660_1_, boolean p_213660_2_) {
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        ItemStack itemstack = this.findAmmo(this.getHeldItem(ProjectileHelper.getHandWith(this, Items.BOW)));
        AbstractArrowEntity abstractarrowentity = ProjectileHelper.fireArrow(this, itemstack, distanceFactor);
        if (this.getHeldItemMainhand().getItem() instanceof net.minecraft.item.BowItem)
            abstractarrowentity = ((net.minecraft.item.BowItem) this.getHeldItemMainhand().getItem()).customeArrow(abstractarrowentity);
        abstractarrowentity.setDamage(10);
        double d0 = target.posX - this.posX;
        double d1 = target.getBoundingBox().minY + (double) (target.getHeight() / 3.0F) - abstractarrowentity.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        abstractarrowentity.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - this.world.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.addEntity(abstractarrowentity);
    }

    @OnlyIn(Dist.CLIENT)
    public AbstractIllagerEntity.ArmPose getArmPose() {
        if (this.isSpellcasting()) {
            return AbstractIllagerEntity.ArmPose.SPELLCASTING;
        } else {
            return this.isAggressive() ? AbstractIllagerEntity.ArmPose.BOW_AND_ARROW : AbstractIllagerEntity.ArmPose.CROSSED;
        }
    }

    class CastingSpellGoal extends SpellcastingIllagerEntity.CastingASpellGoal {

        private CastingSpellGoal() {
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (CrazedSummonerEntity.this.getAttackTarget() != null) {
                CrazedSummonerEntity.this.getLookController().setLookPositionWithEntity(CrazedSummonerEntity.this.getAttackTarget(), (float) CrazedSummonerEntity.this.getHorizontalFaceSpeed(), (float) CrazedSummonerEntity.this.getVerticalFaceSpeed());
            }
        }
    }

    class SummonSpellGoal extends SpellcastingIllagerEntity.UseSpellGoal {
        private final EntityPredicate predicate = (new EntityPredicate()).setDistance(16.0D).setLineOfSiteRequired().setUseInvisibilityCheck().allowInvulnerable().allowFriendlyFire();

        private SummonSpellGoal() {
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            if (!super.shouldExecute()) {
                return false;
            } else {
                int i = CrazedSummonerEntity.this.world.getTargettableEntitiesWithinAABB(LivingEntity.class, this.predicate, CrazedSummonerEntity.this, CrazedSummonerEntity.this.getBoundingBox().grow(16.0D)).size();
                return CrazedSummonerEntity.this.rand.nextInt(8) + 1 > i;
            }
        }

        protected int getCastingTime() {
            return 50;
        }

        protected int getCastingInterval() {
            return 200;
        }

        protected void castSpell() {
            for (int i = 0; i < rand.nextInt(3) + 1; ++i) {
                BlockPos blockpos = (new BlockPos(CrazedSummonerEntity.this)).add(-2 + CrazedSummonerEntity.this.rand.nextInt(5), 1, -2 + CrazedSummonerEntity.this.rand.nextInt(5));

                switch (rand.nextInt(5)) {
                    case 0:
                        ClusterCreeperEntity clusterCreeperEntity = ModEntities.CLUSTER_CREEPER.getValue().create(CrazedSummonerEntity.this.world);
                        clusterCreeperEntity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                        clusterCreeperEntity.onInitialSpawn(CrazedSummonerEntity.this.world, CrazedSummonerEntity.this.world.getDifficultyForLocation(blockpos), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
                        CrazedSummonerEntity.this.world.addEntity(clusterCreeperEntity);
                        break;

                    case 1:
                        MutantZombieEntity mutantZombieEntity = ModEntities.MUTANT_ZOMBIE.getValue().create(CrazedSummonerEntity.this.world);
                        mutantZombieEntity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                        mutantZombieEntity.onInitialSpawn(CrazedSummonerEntity.this.world, CrazedSummonerEntity.this.world.getDifficultyForLocation(blockpos), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
                        CrazedSummonerEntity.this.world.addEntity(mutantZombieEntity);
                        break;

                    case 2:
                        ZombieEntity zombieEntity = EntityType.ZOMBIE.create(CrazedSummonerEntity.this.world);
                        zombieEntity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                        zombieEntity.onInitialSpawn(CrazedSummonerEntity.this.world, CrazedSummonerEntity.this.world.getDifficultyForLocation(blockpos), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
                        CrazedSummonerEntity.this.world.addEntity(zombieEntity);

                        zombieEntity.getCapability(MobCapability.MOB_INSTANCE).ifPresent(affected -> {
                            int r = rand.nextInt(3);
                            if (!affected.getAbilities().contains(INVISIBILITY) && r > 1) {
                                affected.addAbility(INVISIBILITY);
                            } else if (!affected.getAbilities().contains(SPEED_BOOST) && r < 1) {
                                affected.addAbility(SPEED_BOOST);
                                affected.addAbility(SPEED_BOOST.setAmplifier(1));
                            }
                        });
                        break;

                    case 3:
                        SkeletonEntity skeletonEntity = EntityType.SKELETON.create(CrazedSummonerEntity.this.world);
                        skeletonEntity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                        skeletonEntity.onInitialSpawn(CrazedSummonerEntity.this.world, CrazedSummonerEntity.this.world.getDifficultyForLocation(blockpos), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
                        CrazedSummonerEntity.this.world.addEntity(skeletonEntity);

                        skeletonEntity.getCapability(MobCapability.MOB_INSTANCE).ifPresent(affected -> {
                            int r = rand.nextInt(3);
                            if (!affected.getAbilities().contains(JUMP_BOOST) && r > 1) {
                                affected.addAbility(JUMP_BOOST);
                                affected.addAbility(JUMP_BOOST.setAmplifier(2));
                            } else if (!affected.getAbilities().contains(ABSORPTION) && r < 1) {
                                affected.addAbility(ABSORPTION);
                            }
                        });
                        break;

                    case 4:
                        SheepEntity sheepEntity = EntityType.SHEEP.create(CrazedSummonerEntity.this.world);
                        sheepEntity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                        sheepEntity.onInitialSpawn(CrazedSummonerEntity.this.world, CrazedSummonerEntity.this.world.getDifficultyForLocation(blockpos), SpawnReason.MOB_SUMMONED, (ILivingEntityData) null, (CompoundNBT) null);
                        CrazedSummonerEntity.this.world.addEntity(sheepEntity);
                        break;
                }
            }
        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
        }

        protected SpellcastingIllagerEntity.SpellType getSpellType() {
            return SpellType.SUMMON_VEX;
        }
    }

}
