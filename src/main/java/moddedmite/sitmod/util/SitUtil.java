package moddedmite.sitmod.util;

import moddedmite.sitmod.SitMod;
import moddedmite.sitmod.api.SitContext;
import moddedmite.sitmod.entity.EntitySit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Block;
import net.minecraft.EntityPlayer;
import net.minecraft.World;


import java.util.Optional;

public class SitUtil {
    public static boolean consumeBlockActivation(World world, int x, int y, int z, EntityPlayer player, Block block) {
        int metadata = world.getBlockMetadata(x, y, z);

        SitContext context = new SitContext(world, x, y, z, player, block, metadata);

        if (!isValidSeat(context)) return false;

        if (player.hasHeldItem()) return false;

        if (player.ridingEntity instanceof EntitySit && player.getBlockPosX() == x && player.getBlockPosY() == y && player.getBlockPosZ() == z) {
            return false;
        } // already sitting here

        if (!isOpenSpace(world, x, y, z)) return false;

        if (player.onServer()) {
            performSitting(world, x, y, z, player, block, context);
        }
        return true;
    }

    private static boolean isOpenSpace(World world, int x, int y, int z) {
        return world.getBlockId(x, y + 1, z) == 0 && world.getBlockId(x, y + 2, z) == 0;
    }

    @Environment(EnvType.SERVER)
    private static void performSitting(World world, int x, int y, int z, EntityPlayer player, Block block, SitContext context) {
        EntitySit entitySit = new EntitySit(world);

        handleRotation(player, entitySit, context);

        entitySit.setPosition((double) x + 0.5D, 0.5D + (double) y, (double) z + 0.5D);
        world.spawnEntityInWorld(entitySit);
        entitySit.forceSpawn = true;
        player.setPosition((float) x, (float) y, (float) z);
        player.mountEntity(entitySit);
    }

    public static boolean isValidSeat(SitContext context) {
        return SitMod.PLUGINS.stream().anyMatch(x -> x.canSitOn(context));
    }

    public static boolean canRotateFreely(SitContext context) {
        return SitMod.PLUGINS.stream().anyMatch(x -> x.canRotateFreely(context));
    }

    @Environment(EnvType.SERVER)
    private static void handleRotation(EntityPlayer player, EntitySit entitySit, SitContext context) {
        if (canRotateFreely(context)) {
            entitySit.rotationYaw = player.rotationYaw;
            return;
        }

        SitMod.PLUGINS.stream().map(plugin -> plugin.getRotation(context))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst().ifPresent(rotation -> {
                    player.rotationYaw = rotation % 360.0F;
                    entitySit.rotationYaw = rotation % 360.0F;
                });
    }
}
