package com.github.Debris.SitMod.mixins;

import com.github.Debris.SitMod.entity.EntitySit;
import net.minecraft.EntityLiving;
import net.minecraft.EntityLivingBase;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLiving.class)
public abstract class EntityLivingMixin extends EntityLivingBase {
    public EntityLivingMixin(World par1World) {
        super(par1World);
    }

    @Inject(method = "getTarget", at = @At("HEAD"), cancellable = true)
    private void test(CallbackInfoReturnable<EntityLivingBase> cir) {
        if ((EntityLiving) (Object) this instanceof EntitySit) cir.setReturnValue(null);
    }
}
