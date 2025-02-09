package moddedmite.sitmod.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import moddedmite.sitmod.util.SitUtil;
import net.minecraft.Block;
import net.minecraft.EntityPlayer;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public class BlockMixin {
    @ModifyReturnValue(method = "onBlockActivated", at = @At("RETURN"))
    private boolean onBlockActivated(boolean original,
                                     @Local(argsOnly = true) World world,
                                     @Local(argsOnly = true, ordinal = 0) int x,
                                     @Local(argsOnly = true, ordinal = 1) int y,
                                     @Local(argsOnly = true, ordinal = 2) int z,
                                     @Local(argsOnly = true) EntityPlayer player
    ) {
        if (original) return true;
        return SitUtil.consumeBlockActivation(world, x, y, z, player, (Block) (Object) this);
    }
}
