package net.bxx2004.futuretech;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import net.bxx2004.futuretech.core.data.ConfigManager;
import net.bxx2004.futuretech.core.utils.RegisterBlock;
import net.bxx2004.futuretech.core.utils.RegisterItem;
import net.bxx2004.futuretech.core.utils.RegisterMenu;
import net.bxx2004.futuretech.core.world.FutureWorld;
import net.bxx2004.futuretech.core.world.FutureWorldGenerator;
import net.bxx2004.futuretech.slimefun.SlimefunFactory;
import net.bxx2004.futuretech.slimefun.Tools;
import net.bxx2004.pandalib.bukkit.planguage.PLangNode;
import net.bxx2004.pandalib.bukkit.ptask.depend.Depend;
import net.bxx2004.pandalib.bukkit.ptask.depend.MultiPluginDependTask;
import net.bxx2004.pandalib.bukkit.putil.PCooldown;
import net.bxx2004.pandalibloader.BukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
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
    public static PCooldown cooldown;
    @Override
    public String getPackage() {
        return "net.bxx2004.futuretech";
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        cooldown = new PCooldown(this);
        cooldown.addKey("FT_SIRIROBOT");
        node = new PLangNode(this, ConfigManager.language());
        init();
        getLogger().info("########################################");
        getLogger().info("                FutureTech              ");
        getLogger().info("       作者: bxx2004 | Have a good time  ");
        getLogger().info("########################################");
    }
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        if (worldName.equalsIgnoreCase("ft_World")){
            return new FutureWorldGenerator();
        }
        return null;
    }
    public void init(){
        saveResource("scripts/FT_SIRIROBOT.yml",false);
        SlimefunFactory.init();
        Reflections ref = new Reflections(getPackage() + ".slimefun");
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
        Set<Class<?>> clazz_Menu = ref.getTypesAnnotatedWith(RegisterMenu.class);
        for (Class c : clazz_Menu){
            try {
                c.getDeclaredConstructor().newInstance();
            }catch (Exception e){e.printStackTrace();}
        }
        if (Bukkit.getWorld("ft_world") == null){
            new FutureWorld().createWorld();
            new MultiPluginDependTask(){
                @Depend(name = "Multiverse-Core",version = "all",asynchronous = false)
                public void mu(){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mv import ft_world normal");
                }
                @Depend(name = "MyWorlds",version = "all",asynchronous = false)
                public void my(){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"world load ft_world");
                }
            };
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
