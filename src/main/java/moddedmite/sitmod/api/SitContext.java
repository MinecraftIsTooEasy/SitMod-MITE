package moddedmite.sitmod.api;

import net.minecraft.Block;
import net.minecraft.Entity;
import net.minecraft.World;

public record SitContext(World world, int x, int y, int z, Entity passenger, Block block, int metadata) {
}
