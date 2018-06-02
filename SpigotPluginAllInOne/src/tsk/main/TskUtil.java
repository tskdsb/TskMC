package tsk.main;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TskUtil {

    public static List<String> filterByPrefix(List<String> list, String prefix) {
        List<String> c = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).startsWith(prefix)) {
                c.add(list.get(i));
            }
        }
        return c;
    }

    public static void logInfo(Object info) {
        Bukkit.getLogger().info(info.toString());
    }

    public static void healPlayer(Player player) {
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
    }

    public static void giveItemToPlayer(Player player, ItemStack... itemStacks) {
        HashMap<Integer, ItemStack> returnItem = player.getInventory().addItem(itemStacks);
        if (!returnItem.isEmpty()) {
            for (ItemStack item :
                    returnItem.values()) {
                player.getWorld().dropItem(player.getLocation(), item);
            }
        }
    }

    // [min,max)
    public static int randInt(int min, int max) {
        return (int) (min + Math.random() * max);
    }
}
