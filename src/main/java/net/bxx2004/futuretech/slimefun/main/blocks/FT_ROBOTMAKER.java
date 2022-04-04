package net.bxx2004.futuretech.slimefun.main.blocks;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import net.bxx2004.futuretech.core.utils.RegisterBlock;
import net.bxx2004.futuretech.slimefun.SlimefunFactory;
import net.bxx2004.futuretech.slimefun.main.Machine;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
@RegisterBlock
public class FT_ROBOTMAKER extends Machine {
    public FT_ROBOTMAKER(){
        super();
    }
    @Override
    public void doSomeThing() {
        getMachine().addRecipe(new ItemStack[]{
                SlimefunItems.GILDED_IRON
        },new ItemStack(Material.IRON_INGOT));
    }

    @Override
    public ItemGroup group() {
        return SlimefunFactory.MACHINE;
    }

    @Override
    public SlimefunItemStack item() {
        return new SlimefunItemStack(getID(),new ItemStack(Material.ANVIL));
    }

    @Override
    public ItemStack[] recipe() {
        return new ItemStack[]{
                null,null,null,
                null,null,null,
                new ItemStack(Material.DIAMOND_BLOCK),new ItemStack(Material.DIAMOND_BLOCK),new ItemStack(Material.DIAMOND_BLOCK)
        };
    }

    @Override
    public BlockFace face() {
        return BlockFace.UP;
    }

    @Override
    public void onClick(Player player, Block block) {
        player.sendMessage("哈哈哈");
    }
}
