package tsk.tskEnchantment;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TskEnchantment {

    public static boolean addUnsafeTskEnchantment(String playerName, String enchantmentName, int level) {

        Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            return false;
        }

        Enchantment enchantment = Enchantment.getByName(enchantmentName);
        if (enchantment == null) {
            return false;
        }

        ItemStack itemInMainHand = player.getEquipment().getItemInMainHand();
        if (itemInMainHand == null) {
            return false;
        }

        itemInMainHand.addUnsafeEnchantment(enchantment, level);

        return true;
    }
}
