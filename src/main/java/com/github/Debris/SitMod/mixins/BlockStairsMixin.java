package com.github.Debris.SitMod.mixins;

import com.github.Debris.SitMod.util.SitUtil;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockStairs.class)
public abstract class BlockStairsMixin extends Block {
    protected BlockStairsMixin(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    @Redirect(method = "hidesAdjacentSide", at = @At(value = "INVOKE", target = "Lnet/minecraft/EnumFace;getOpposite()Lnet/minecraft/EnumFace;"))
    private EnumFace redirectFaceGet(EnumFace instance, IBlockAccess block_access, int x, int y, int z, Block neighbor, int side) {
        return EnumFace.get(MathHelper.clamp_int(side, 0, 5));
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float offset_x, float offset_y, float offset_z) {
        return SitUtil.consumeBlockActivation(world, x, y, z, player, this);
    }

}
