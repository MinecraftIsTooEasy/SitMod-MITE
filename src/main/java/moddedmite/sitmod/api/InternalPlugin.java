package moddedmite.sitmod.api;

import net.minecraft.Block;
import net.minecraft.BlockSlab;
import net.minecraft.BlockStairs;

import java.util.Optional;

public class InternalPlugin implements SitModPlugin {
    @Override
    public boolean canSitOn(SitContext context) {
        Block block = context.block();
        int metadata = context.metadata();
        if (isLowerSlab(block, metadata)) return true;
        if (isLowerStairs(block, metadata)) return true;
        return false;
    }

    @Override
    public boolean canRotateFreely(SitContext context) {
        Block block = context.block();
        int metadata = context.metadata();
        if (isLowerSlab(block, metadata)) return true;
        return false;
    }

    @Override
    public Optional<Float> getRotation(SitContext context) {
        Block block = context.block();
        int metadata = context.metadata();
        if (isLowerStairs(block, metadata)) return Optional.of(getStairRotation(metadata));
        return Optional.empty();
    }

    private static boolean isLowerSlab(Block block, int metadata) {
        return block instanceof BlockSlab && BlockSlab.isBottom(metadata);
    }

    private static boolean isLowerStairs(Block block, int metadata) {
        return block instanceof BlockStairs && (metadata & 4) != 4;
    }

    private static float getStairRotation(int metadata) {
        return switch (metadata) {
            case 0 -> 90.0F;
            case 1 -> -90.0F;
            case 2 -> 180.0F;
            default -> 0.0F;
        };
    }
}
