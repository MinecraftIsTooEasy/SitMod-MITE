package com.github.Debris.SitMod.util;

import com.github.Debris.SitMod.entity.EntitySit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.*;

public class SitUtil {
    public static boolean consumeBlockActivation(World world, int x, int y, int z, EntityPlayer player, Block block) {
//        if (!player.isSneaking()) return false;

        if (player.ridingEntity instanceof EntitySit && player.getBlockPosX() == x && player.getBlockPosY() == y && player.getBlockPosZ() == z) {
            return false;
        } // already sitting here

        if (isSlab(block) && !isLowerSlab(world.getBlockMetadata(x, y, z))) return false;

        if (isStair(block) && isStairUpsidedown(world.getBlockMetadata(x, y, z))) return false;

        if (!isOpenSpace(world, x, y, z)) return false;

        if (player.onServer()) {
            performSitting(world, x, y, z, player, block);
        }
        return true;
    }

    private static boolean isOpenSpace(World world, int x, int y, int z) {
        return world.getBlockId(x, y + 1, z) == 0 && world.getBlockId(x, y + 2, z) == 0;
    }

    @Environment(EnvType.SERVER)
    private static void performSitting(World world, int x, int y, int z, EntityPlayer player, Block block) {
        EntitySit entitySit = new EntitySit(world);

        handleRotation(world, x, y, z, player, entitySit, block);

        entitySit.setPosition((double) x + 0.5D, 0.5D + (double) y, (double) z + 0.5D);
        world.spawnEntityInWorld(entitySit);
        entitySit.forceSpawn = true;
        player.setPosition((float) x, (float) y, (float) z);
        player.mountEntity(entitySit);
    }

    public static boolean isValidSeat(Block block) {
        return isSlab(block) || isStair(block);
    }

    public static boolean canRotateFreely(Block block) {
        return isSlab(block);
    }

    @Environment(EnvType.SERVER)
    private static void handleRotation(World world, int x, int y, int z, EntityPlayer player, EntitySit entitySit, Block block) {
        if (canRotateFreely(block)) {
            entitySit.rotationYaw = player.rotationYaw;
            return;
        }

        if (isStair(block)) {
            int metadata = world.getBlockMetadata(x, y, z);
            float rotation = getStairRotation(metadata);
            player.rotationYaw = rotation % 360.0F;
            entitySit.rotationYaw = rotation % 360.0F;
        }

    }

    private static boolean isStair(Block block) {
        return block instanceof BlockStairs;
    }

    private static boolean isStairUpsidedown(int metadata) {
        return (metadata & 4) == 4;
    }

    private static float getStairRotation(int metadata) {
        return switch (metadata) {
            case 0 -> 90.0F;
            case 1 -> -90.0F;
            case 2 -> 180.0F;
            default -> 0.0F;
        };
    }

    private static boolean isSlab(Block block) {
        return block instanceof BlockSlab;
    }

    private static boolean isLowerSlab(int metadata) {
        return BlockSlab.isBottom(metadata);
    }
}
