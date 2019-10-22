package com.fabriccommunity.spookytime.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;

@Mixin(IronGolemEntityModel.class)

public abstract class IronGolemModelMxn implements ModelWithHead {

    private Cuboid headwear;

    @Shadow
    @Final
    private Cuboid field_3415;

    @Shadow
    @Final
    private Cuboid field_3413; 

    @SuppressWarnings({"unchecked"})
    @Inject(at = @At("RETURN"), method = "<init>")
    public void init(CallbackInfo ci) {
        float float_1 = 0f;
        this.headwear = (new Cuboid((IronGolemEntityModel)(Object)this)).setTextureSize(128, 128);
        this.headwear.setRotationPoint(0.0F, -10.0F, -3.5F);
        this.headwear.setTextureOffset(24, 0).addBox(-1.0F, -4.0F, -7.5F, 2, 4, 2, float_1);

    }

    @Inject(at = @At("RETURN"), method = "method_17096")
    public void render3(IronGolemEntity ironGolemEntity, float float_1, float float_2, float float_3, float float_4,
            float float_5, float float_6, CallbackInfo ci) {
        if(((DefaultedList<ItemStack>)ironGolemEntity.getArmorItems()).get(3).isEmpty())
        {
            field_3415.visible = true;
        }
        else
        {
            field_3415.visible = false;
            this.headwear.render(float_6);
        }
    }

    @Inject(at = @At("RETURN"), method = "method_17097")
    public void setRotation(CallbackInfo ci) {
        headwear.pitch = field_3415.pitch;
        headwear.roll = field_3415.roll;
        headwear.yaw = field_3415.yaw;
    }

    @Override
    public Cuboid getHead() {
        return this.headwear;
    }

} 