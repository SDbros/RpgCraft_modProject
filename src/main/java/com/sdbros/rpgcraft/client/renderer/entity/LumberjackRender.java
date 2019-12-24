package com.sdbros.rpgcraft.client.renderer.entity;

import com.sdbros.rpgcraft.RpgCraft;
import com.sdbros.rpgcraft.entity.LumberjackEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class LumberjackRender extends MobRenderer<LumberjackEntity, PlayerModel<LumberjackEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RpgCraft.RESOURCE_PREFIX + "textures/entity/lumberjack.png");

    public LumberjackRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PlayerModel(0.0F, false), 0.25F);

        this.addLayer(new HeldItemLayer(this));

    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(LumberjackEntity entity) {
        return TEXTURE;
    }

}

