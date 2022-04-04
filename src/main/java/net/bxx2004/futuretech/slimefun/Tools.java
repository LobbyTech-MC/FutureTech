package net.bxx2004.futuretech.slimefun;

import net.bxx2004.futuretech.FutureTech;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Tools {
    private static HashMap<String,InventoryHolder> map = new HashMap<>();
    public static NamespacedKey key(String id){
        NamespacedKey key = new NamespacedKey(FutureTech.instance(),id);
        return key;
    }
    public static InventoryHolder holder(String id){
        return map.get(id);
    }
    public static InventoryHolder setHolder(String id){
        map.put(id, new InventoryHolder() {
            @Override
            public @NotNull Inventory getInventory() {
                return null;
            }
        });
        return map.get(id);
    }
}
