package net.bxx2004.futuretech.core.world;

import net.bxx2004.pandalib.bukkit.putil.PMath;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// clone https://www.mcbbs.net/thread-811614-1-1.html
public class FutureWorldGenerator extends ChunkGenerator {
    private SimplexOctaveGenerator noise;

    @Override
    public @NotNull List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
        List<BlockPopulator> list = new ArrayList<>();
        list.add(new FutureBlockPopulator());
        list.add(new FutureOrePopulator());
        return list;
    }
    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);
        // 我们需要的噪声生成器
        if (noise == null) {
            noise = new SimplexOctaveGenerator(world.getSeed(), 1);
            // 我们需要更平缓的地形，所以需要设置 scale
            // 该值越大，地形变化更大
            // 微调即可
            noise.setScale(0.005D);
        }
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                // 方块的真实坐标
                int realX = chunkX * 16 + x;
                int realZ = chunkZ * 16 + z;
                // noise 方法返回 -1 到 1 之间的随机数
                double noiseValue = noise.noise(realX, realZ, 0.5D, 0.5D);
                // 将 noise 值放大，作为该点的高度
                int height = (int) (noiseValue * 40D + 100D);
                // 底层基岩
                chunkData.setBlock(x, 0, z, Material.BEDROCK);
                // 中间石头
                for (int y = 0; y < height - 1; y++) {
                    if (PMath.getRandomAsInt(0,10) > 7){
                        chunkData.setBlock(x, y, z, Material.MAGMA_BLOCK);
                    }else {
                        chunkData.setBlock(x, y, z, Material.SOUL_SOIL);
                    }
                }
                // 表面覆盖泥土和草方块
                chunkData.setBlock(x, height - 1, z, Material.DIRT);
                chunkData.setBlock(x, height, z, Material.PODZOL);
            }
        }
        return chunkData;
    }
}
