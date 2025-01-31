package com.github.Debris.SitMod.entity;

import com.github.Debris.SitMod.util.SitUtil;
import net.minecraft.Block;
import net.minecraft.EntityLiving;
import net.minecraft.IAnimals;
import net.minecraft.World;

/**
 * // IAnimals are handled automatically at packet 23
 */
public class EntitySit extends EntityLiving implements IAnimals {
    public EntitySit(World par1World) {
        super(par1World);
        this.setSize(0.0F, 0.0F);
    }

    @Override
    public void onUpdate() {
        if (this.riddenByEntity == null) {
            this.setDead();
            return;
        }
        Block block = this.worldObj.getBlock(this.getBlockPosX(), this.getBlockPosY(), this.getBlockPosZ());
        if (!(SitUtil.isValidSeat(block))) {
            this.setDead();
        }
        if (SitUtil.canRotateFreely(block)) {
            this.rotationYaw = this.riddenByEntity.rotationYaw;
        }
    }

    @Override
    public boolean canCatchFire() {
        return false;
    }
}
