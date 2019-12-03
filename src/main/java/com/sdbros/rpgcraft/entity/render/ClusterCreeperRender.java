package com.sdbros.rpgcraft.entity.render;


import com.mojang.blaze3d.platform.GlStateManager;
import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.entity.mobs.ClusterCreeperEntity;
import com.sdbros.rpgcraft.entity.mobs.RedCreeperEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;


public final class ClusterCreeperRender extends MobRenderer<ClusterCreeperEntity, CreeperModel<ClusterCreeperEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "textures/entity/cluster_creeper.png");

    public ClusterCreeperRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CreeperModel<>(), 0.5F);
    }

    public void doRender(ClusterCreeperEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.shadowSize = 0.1F * (float)entity.getClusterCreeperSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    public float prepareScale(ClusterCreeperEntity entitylivingbaseIn, float partialTicks) {
        return super.prepareScale(entitylivingbaseIn, partialTicks);
    }

    protected void preRenderCallback(ClusterCreeperEntity entitylivingbaseIn, float partialTickTime) {
        float f = (float)entitylivingbaseIn.getClusterCreeperSize()/3.5f;
        float f2 = (1.0F + f * 0.4F);
        float f3 = (1.0F + f * 0.1F);
        GlStateManager.scalef(f, f, f);

    }

    protected int getColorMultiplier(ClusterCreeperEntity entitylivingbaseIn, float lightBrightness, float partialTickTime) {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        if ((int) (f * 10.0F) % 2 == 0) {
            return 0;
        } else {
            int i = (int) (f * 0.2F * 255.0F);
            i = MathHelper.clamp(i, 0, 255);
            return i << 24 | 822083583;
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(ClusterCreeperEntity entity) {
        return TEXTURE;
    }
}
