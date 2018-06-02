package tsk.item;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import tsk.main.TskUtil;

import java.util.HashMap;
import java.util.Map;

public class ItemInfo {
    public static void upgradeEnchLevel(ItemStack itemStack) {

    }

    public static Map<Enchantment, Integer> unsafeEnchCombine(Map<Enchantment, Integer> e1, Map<Enchantment, Integer> e2) {
        Map<Enchantment, Integer> result = new HashMap<>();
        result.putAll(e2);
        for (Map.Entry<Enchantment, Integer> ench1 :
                e1.entrySet()) {
            if (e2.containsKey(ench1.getKey())) {
                if (ench1.getValue().equals(e2.get(ench1.getKey()))) {
                    int newLevel = ench1.getValue() + 1;
                    if (newLevel > 10) {
                        newLevel = 10;
                    }
                    result.put(ench1.getKey(), newLevel);
                } else {
                    result.put(ench1.getKey(), ench1.getValue() > e2.get(ench1.getKey()) ? ench1.getValue() : e2.get(ench1.getKey()));
                }
            } else {
                result.put(ench1.getKey(), ench1.getValue());
            }
        }

        return result;
    }
}
