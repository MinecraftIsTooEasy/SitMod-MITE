package moddedmite.sitmod.entity;

import moddedmite.sitmod.api.SitContext;
import moddedmite.sitmod.util.SitUtil;
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
        int blockPosX = this.getBlockPosX();
        int blockPosY = this.getBlockPosY();
        int blockPosZ = this.getBlockPosZ();
        Block block = this.worldObj.getBlock(blockPosX, blockPosY, blockPosZ);
        int metadata = this.worldObj.getBlockMetadata(blockPosX, blockPosY, blockPosZ);
        SitContext context = new SitContext(this.worldObj, blockPosX, blockPosY, blockPosZ, this.riddenByEntity, block, metadata);
        if (!(SitUtil.isValidSeat(context))) {
            this.setDead();
        }
        if (SitUtil.canRotateFreely(context)) {
            this.rotationYaw = this.riddenByEntity.rotationYaw;
        }
    }

    @Override
    public boolean canCatchFire() {
        return false;
    }
}
