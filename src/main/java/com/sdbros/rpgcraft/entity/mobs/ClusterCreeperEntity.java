package com.sdbros.rpgcraft.entity.mobs;


import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Random;

public class ClusterCreeperEntity extends CreatureEntity {
    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(CreeperEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(CreeperEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(CreeperEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> CLUSTER_CREEPER_SIZE = EntityDataManager.createKey(ClusterCreeperEntity.class, DataSerializers.VARINT);
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;
    private int explosionRadius = 3;


    public ClusterCreeperEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new ClusterCreeperSwellGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1D, true));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 18.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1);
    }

    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(CLUSTER_CREEPER_SIZE, 1);
        this.dataManager.register(STATE, -1);
        this.dataManager.register(POWERED, false);
        this.dataManager.register(IGNITED, false);
    }

    protected void setClusterCreeperSize(int size, boolean resetHealth) {
        this.dataManager.set(CLUSTER_CREEPER_SIZE, size);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.recalculateSize();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((size * size));
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5 - (0.07 * size));
        if (resetHealth) {
            this.setHealth(this.getMaxHealth());
        }
        this.fuseTime = 7 * size;
        this.explosionRadius = size;
        this.experienceValue = size;
    }

    /**
     * Returns the size of the ClusterCreeper.
     */
    public int getClusterCreeperSize() {
        return this.dataManager.get(CLUSTER_CREEPER_SIZE);
    }

    /**
     * The maximum height from where the entity is allowed to jump (used in pathfinder)
     */
    public int getMaxFallHeight() {
        return this.getAttackTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
    }

    public void fall(float distance, float damageMultiplier) {
        super.fall(distance, damageMultiplier);
        this.timeSinceIgnited = (int) ((float) this.timeSinceIgnited + distance * 1.5F);
        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5;
        }

    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.dataManager.get(POWERED)) {
            compound.putBoolean("powered", true);
        }

        compound.putInt("Size", this.getClusterCreeperSize());
        compound.putShort("Fuse", (short) this.fuseTime);
        compound.putByte("ExplosionRadius", (byte) this.explosionRadius);
        compound.putBoolean("ignited", this.hasIgnited());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.dataManager.set(POWERED, compound.getBoolean("powered"));
        if (compound.contains("Fuse", 99)) {
            this.fuseTime = compound.getShort("Fuse");
        }

        if (compound.contains("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }

        if (compound.getBoolean("ignited")) {
            this.ignite();
        }
        int i = compound.getInt("Size");
        if (i < 0) {
            i = 0;
        }
        this.setClusterCreeperSize(i + 1, false);
    }


    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        if (this.isAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
            if (this.hasIgnited()) {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();
            if (i > 0 && this.timeSinceIgnited == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
        }

        super.tick();
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (CLUSTER_CREEPER_SIZE.equals(key)) {
            this.recalculateSize();
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;
            if (this.isInWater() && this.rand.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }

        super.notifyDataManagerChange(key);
    }

    @Override
    public void remove(boolean keepData) {
        int i = this.getClusterCreeperSize();
        if (i > 1) {
            int j = this.rand.nextInt(3);

            for (int k = 0; k < j; ++k) {
                float f = ((float) (k % 2) - 0.5F) * (float) i / 4.0F;
                float f1 = ((float) (k / 2) - 0.5F) * (float) i / 4.0F;
                ClusterCreeperEntity clusterCreeperEntity = (ClusterCreeperEntity) this.getType().create(this.world);
                if (this.hasCustomName()) {
                    clusterCreeperEntity.setCustomName(this.getCustomName());
                }

                if (this.isNoDespawnRequired()) {
                    clusterCreeperEntity.enablePersistence();
                }

                clusterCreeperEntity.setClusterCreeperSize(i / 2, true);
                clusterCreeperEntity.setLocationAndAngles(this.posX + (double) f, this.posY + 0.5D, this.posZ + (double) f1, this.rand.nextFloat() * 360.0F, 0.0F);
                this.world.addEntity(clusterCreeperEntity);
            }
        }

        super.remove(keepData);
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        int i = this.rand.nextInt(3);
        if (i < 2 && this.rand.nextFloat() < 0.5F * difficultyIn.getClampedAdditionalDifficulty()) {
            ++i;
        }

        int j = 1 << i;
        this.setClusterCreeperSize(j, true);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        return true;
    }

    /**
     * Returns true if the creeper is powered by a lightning bolt.
     */
    public boolean getPowered() {
        return this.dataManager.get(POWERED);
    }

    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    @OnlyIn(Dist.CLIENT)
    public float getCreeperFlashIntensity(float partialTicks) {
        return MathHelper.lerp(partialTicks, (float) this.lastActiveTime, (float) this.timeSinceIgnited) / (float) (this.fuseTime - 2);
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int getCreeperState() {
        return this.dataManager.get(STATE);
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void setCreeperState(int state) {
        this.dataManager.set(STATE, state);
    }

    protected boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                this.ignite();
                itemstack.damageItem(1, player, (p_213625_1_) -> {
                    p_213625_1_.sendBreakAnimation(hand);
                });
                return true;
            }
        }

        return super.processInteract(player, hand);
    }

    /**
     * Creates an explosion as determined by this creeper's power and explosion radius.
     */
    private void explode() {
        if (!this.world.isRemote) {
            Explosion.Mode explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
            float f = this.getPowered() ? 2.0F : 1.0F;
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, (float) this.explosionRadius * f, explosion$mode);
            this.remove(true);
            this.dead = true;
            this.spawnLingeringCloud();
        }

    }

    private void spawnLingeringCloud() {
        Collection<EffectInstance> collection = this.getActivePotionEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.posX, this.posY, this.posZ);
            areaeffectcloudentity.setRadius(2.5F);
            areaeffectcloudentity.setRadiusOnUse(-0.5F);
            areaeffectcloudentity.setWaitTime(10);
            areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
            areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());

            for (EffectInstance effectinstance : collection) {
                areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
            }

            this.world.addEntity(areaeffectcloudentity);
        }

    }

    public boolean hasIgnited() {
        return this.dataManager.get(IGNITED);
    }

    public void ignite() {
        this.dataManager.set(IGNITED, true);
    }

    public EntitySize getSize(Pose poseIn) {
        return super.getSize(poseIn).scale(0.255F * (float) this.getClusterCreeperSize());
    }

    public class ClusterCreeperSwellGoal extends Goal {
        private final ClusterCreeperEntity clusterCreeperEntity;
        private LivingEntity livingEntity;

        public ClusterCreeperSwellGoal(ClusterCreeperEntity clusterCreeperEntity) {
            this.clusterCreeperEntity = clusterCreeperEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            LivingEntity livingentity = this.clusterCreeperEntity.getAttackTarget();
            return this.clusterCreeperEntity.getCreeperState() > 0 || livingentity != null && this.clusterCreeperEntity.getDistanceSq(livingentity) < 1.0D + getClusterCreeperSize();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.clusterCreeperEntity.getNavigator().clearPath();
            this.livingEntity = this.clusterCreeperEntity.getAttackTarget();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            this.livingEntity = null;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.livingEntity == null) {
                this.clusterCreeperEntity.setCreeperState(-1);
            } else if (this.clusterCreeperEntity.getDistanceSq(this.livingEntity) > 49.0D) {
                this.clusterCreeperEntity.setCreeperState(-1);
            } else if (!this.clusterCreeperEntity.getEntitySenses().canSee(this.livingEntity)) {
                this.clusterCreeperEntity.setCreeperState(-1);
            } else {
                this.clusterCreeperEntity.setCreeperState(1);
            }
        }
    }

    public static boolean canSpawnAt(EntityType<ClusterCreeperEntity> type, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL;
    }
}

