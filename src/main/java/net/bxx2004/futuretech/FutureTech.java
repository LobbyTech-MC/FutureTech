package net.bxx2004.futuretech;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import net.bxx2004.futuretech.core.data.ConfigManager;
import net.bxx2004.futuretech.core.utils.RegisterBlock;
import net.bxx2004.futuretech.core.utils.RegisterItem;
import net.bxx2004.futuretech.slimefun.SlimefunFactory;
import net.bxx2004.futuretech.slimefun.main.Item;
import net.bxx2004.pandalib.bukkit.planguage.PLangNode;
import net.bxx2004.pandalibloader.BukkitPlugin;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;

import java.util.Set;

public class FutureTech extends BukkitPlugin implements SlimefunAddon {
    private static FutureTech plugin;
    public static PLangNode node;
    public static FutureTech instance(){
        return plugin;
    }
    @Override
    public String getPackage() {
        return "net.bxx2004.futuretech";
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        node = new PLangNode(this, ConfigManager.language());
        init();
        getLogger().info("########################################");
        getLogger().info("                FutureTech              ");
        getLogger().info("       作者: bxx2004 | Have a good time  ");
        getLogger().info("########################################");
    }
    public void init(){
        SlimefunFactory.init();
        Reflections ref = new Reflections(getPackage() + ".slimefun.main");
        Set<Class<?>> clazz = ref.getTypesAnnotatedWith(RegisterItem.class);
        for (Class c : clazz){
            try {
                c.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Set<Class<?>> clazz_Blcok = ref.getTypesAnnotatedWith(RegisterBlock.class);
        for (Class c : clazz_Blcok){
            try {
                c.getDeclaredConstructor().newInstance();
            }catch (Exception e){e.printStackTrace();}
        }
    }
    @Override
    public void onDisable() {

    }

    @NotNull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nullable
    @Override
    public String getBugTrackerURL() {
        return "null";
    }
}
