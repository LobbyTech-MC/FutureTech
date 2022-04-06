package net.bxx2004.futuretech.core.world;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import net.bxx2004.futuretech.slimefun.main.items.materials.basic.FT_ENERGYORE;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.block.data.Directional;
import org.bukkit.generator.BlockPopulator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class FutureOrePopulator extends BlockPopulator {

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int x = random.nextInt(16);
        int y = random.nextInt(15) + 1;
        int z = random.nextInt(16);
        while (random.nextDouble() < 0.8D) {
            spawnOre(new FT_ENERGYORE().getItem(),chunk.getBlock(x,y,z));
        }
    }
    private static void spawnOre(SlimefunItem item, Block block){
        block.setType(Material.DEEPSLATE_LAPIS_ORE);
        BlockStorage.store(block, item.getItem());
    }
}
