package net.bxx2004.futuretech.core.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockState;

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

    /*
    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);
        if (noise == null) {
            noise = new SimplexOctaveGenerator(world.getSeed(), 1);
            noise.setScale(0.005D);
        }
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int realX = chunkX * 16 + x;
                int realZ = chunkZ * 16 + z;
                double noiseValue = noise.noise(realX, realZ, 0.5D, 0.5D);
                int height = (int) (noiseValue * 40D + 100D);
                for (int y = 0; y < height - 1; y++) {
                    if (PMath.getRandomAsInt(0, 10) > 7) {
                        chunkData.setBlock(x, y, z, Material.MAGMA_BLOCK);
                    } else {
                        chunkData.setBlock(x, y, z, Material.SOUL_SOIL);
                    }
                }
                chunkData.setBlock(x, 0, z, Material.BEDROCK);
                chunkData.setBlock(x, height - 1, z, Material.DIRT);
                chunkData.setBlock(x, height, z, Material.PODZOL);
            }
        }
        return chunkData;
    }
    */
    
    @Override
    public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ, @NotNull BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);
        
        try (EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder()
                .world(BukkitAdapter.adapt(world))
                .allowedRegionsEverywhere()
                .limitUnlimited()
                .changeSetNull()  // 不记录历史
                .fastMode(true)   // 禁用物理效果
                .build()) {
            
            if (noise == null) {
                noise = new SimplexOctaveGenerator(world.getSeed(), 1);
                noise.setScale(0.005D);
            }
            
            int startX = chunkX * 16;
            int startZ = chunkZ * 16;
            
            // 缓存 BlockState
            BlockState bedrock = BukkitAdapter.adapt(Material.BEDROCK.createBlockData());
            BlockState magma = BukkitAdapter.adapt(Material.MAGMA_BLOCK.createBlockData());
            BlockState soulSoil = BukkitAdapter.adapt(Material.SOUL_SOIL.createBlockData());
            BlockState dirt = BukkitAdapter.adapt(Material.DIRT.createBlockData());
            BlockState podzol = BukkitAdapter.adapt(Material.PODZOL.createBlockData());
            
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int worldX = startX + x;
                    int worldZ = startZ + z;
                    
                    double noiseValue = noise.noise(worldX, worldZ, 0.5D, 0.5D);
                    int height = (int) (noiseValue * 40D + 100D);
                    height = Math.max(2, Math.min(height, world.getMaxHeight() - 1));
                    
                    // 基岩
                    editSession.setBlock(BlockVector3.at(worldX, 0, worldZ), bedrock);
                    
                    // 主体
                    for (int y = 1; y < height - 1; y++) {
                        BlockState state = random.nextInt(10) > 7 ? magma : soulSoil;
                        editSession.setBlock(BlockVector3.at(worldX, y, worldZ), state);
                    }
                    
                    // 顶部
                    editSession.setBlock(BlockVector3.at(worldX, height - 1, worldZ), dirt);
                    editSession.setBlock(BlockVector3.at(worldX, height, worldZ), podzol);
                }
            }
            
            // EditSession 会在 try-with-resources 结束时自动调用 flushQueue()
            editSession.flushQueue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return chunkData;
    }
}
