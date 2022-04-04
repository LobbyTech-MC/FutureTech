package net.bxx2004.futuretech.slimefun.main.blocks;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import net.bxx2004.futuretech.core.data.ConfigManager;
import net.bxx2004.futuretech.core.utils.RegisterBlock;
import net.bxx2004.futuretech.slimefun.SlimefunFactory;
import net.bxx2004.futuretech.slimefun.Tools;
import net.bxx2004.futuretech.slimefun.inventory.CraftMenu;
import net.bxx2004.futuretech.slimefun.inventory.GuideMenu;
import net.bxx2004.futuretech.slimefun.main.Machine;
import net.bxx2004.futuretech.slimefun.main.items.materials.basic.FT_ROBOTARM;
import net.bxx2004.futuretech.slimefun.main.items.materials.cpu.FT_SIRICPU;
import net.bxx2004.pandalib.bukkit.pitem.PItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
@RegisterBlock
public class FT_MAKER extends Machine {
    private static Inventory inventory = Bukkit.createInventory(Tools.setHolder("FT_MAKER"), 54,ConfigManager.blockName("FT_MAKER"));
    public FT_MAKER(){
        super();
    }
    @Override
    public void doSomeThing() {
        GuideMenu.addRecipe(new PItemStack[]{
                null,null,new PItemStack(new FT_SIRICPU().getItem().getItem()),null,null,
                new PItemStack(new FT_ROBOTARM().getItem().getItem()),new PItemStack(new FT_ROBOTARM().getItem().getItem()),new PItemStack(SlimefunItems.ANDROID_MEMORY_CORE),new PItemStack(new FT_ROBOTARM().getItem().getItem()),new PItemStack(new FT_ROBOTARM().getItem().getItem()),
                new PItemStack(new FT_ROBOTARM().getItem().getItem()),null,new PItemStack(SlimefunItems.POWER_CRYSTAL.getItem().getItem()),null,new PItemStack(new FT_ROBOTARM().getItem().getItem()),
                null,new PItemStack(SlimefunItems.ELECTRIC_MOTOR),null,new PItemStack(SlimefunItems.ELECTRIC_MOTOR),null,
                null,new PItemStack(SlimefunItems.ELECTRIC_MOTOR),null,new PItemStack(SlimefunItems.ELECTRIC_MOTOR),null

        }, SlimefunItems.GILDED_IRON.getItem());
    }

    @Override
    public ItemGroup group() {
        return SlimefunFactory.MACHINE;
    }

    @Override
    public SlimefunItemStack item() {
        return new SlimefunItemStack(getID(),new PItemStack(Material.BLAST_FURNACE,
                ConfigManager.blockName("FT_MAKER"),
                ConfigManager.blockLore("FT_MAKER")
        ));
    }

    @Override
    public ItemStack[] recipe() {
        return new ItemStack[]{
                new ItemStack(Material.TORCH),new ItemStack(Material.AMETHYST_CLUSTER),new ItemStack(Material.TORCH),
                new ItemStack(Material.COBBLESTONE_WALL),new ItemStack(Material.BLAST_FURNACE),new ItemStack(Material.COBBLESTONE_WALL),
                new ItemStack(Material.DIAMOND_BLOCK),new ItemStack(Material.SCAFFOLDING),new ItemStack(Material.DIAMOND_BLOCK)
        };
    }

    @Override
    public BlockFace face() {
        return BlockFace.SELF;
    }

    @Override
    public void onClick(Player player, Block block) {
        new CraftMenu().open(player);
    }
}
