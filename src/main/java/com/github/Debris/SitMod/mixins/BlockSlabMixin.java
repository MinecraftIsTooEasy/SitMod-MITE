package com.github.Debris.SitMod.mixins;

import com.github.Debris.SitMod.util.SitUtil;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockSlab.class)
public abstract class BlockSlabMixin extends Block {
    protected BlockSlabMixin(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float offset_x, float offset_y, float offset_z) {
        return SitUtil.consumeBlockActivation(world, x, y, z, player, this);
    }

}
