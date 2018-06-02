package tsk.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import tsk.main.TskUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class OnItem implements Listener {

    @EventHandler
    public void milkRemoveEnchantment(InventoryClickEvent event) {
        if (event.isRightClick()
                && event.getCursor().getType().equals(Material.MILK_BUCKET)
                && event.getWhoClicked() instanceof Player) {
            Map<Enchantment, Integer> enchantments = event.getCurrentItem().getEnchantments();
            if (!enchantments.isEmpty()) {
                event.setCancelled(true);
                event.getWhoClicked().getOpenInventory().setCursor(new ItemStack(Material.BUCKET));
                for (Map.Entry<Enchantment, Integer> entry :
                        enchantments.entrySet()) {
                    int bookIndex = event.getClickedInventory().first(Material.BOOK);
                    if (bookIndex != -1) {
                        ItemStack book = event.getClickedInventory().getItem(bookIndex);
                        book.setAmount(book.getAmount() - 1);
                        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
                        itemStack.addUnsafeEnchantment(entry.getKey(), entry.getValue());
                        TskUtil.giveItemToPlayer((Player) event.getWhoClicked(), itemStack);
                    } else {
                        HashMap<Integer, ? extends ItemStack> ebooks = event.getClickedInventory().all(Material.ENCHANTED_BOOK);
                        if (!ebooks.isEmpty()) {
                            TskUtil.randInt(0, ebooks.size());
                            ItemStack ebook = Collections.min(ebooks.values(), new Comparator<ItemStack>() {
                                @Override
                                public int compare(ItemStack o1, ItemStack o2) {
                                    return o1.getEnchantments().size() - o2.getEnchantments().size();
                                }
                            });
                            ebook.addUnsafeEnchantment(entry.getKey(), entry.getValue());
                        }
                    }
                    event.getCurrentItem().removeEnchantment(entry.getKey());
                }
            }
        }
    }

    @EventHandler
    public void upgradeEnchantment(InventoryClickEvent event) {
        if (event.isRightClick()
                && event.getCursor().getType().equals(Material.EMERALD)) {

        }
    }

//    @EventHandler
//    public void EnchantItemEvent(EnchantItemEvent event) {
//        TskUtil.logInfo(event);
//    }
//
//    @EventHandler
//    public void PrepareItemEnchantEvent(PrepareItemEnchantEvent event) {
//        TskUtil.logInfo(event);
//    }

    @EventHandler
    public void overMaxEnchLevel(PrepareAnvilEvent event) {
        AnvilInventory anvilInventory = event.getInventory();
        ItemStack itemLeft = anvilInventory.getContents()[0];
        ItemStack itemRight = anvilInventory.getContents()[1];
        if (itemLeft == null || itemRight == null) {
            return;
        }
        if (itemLeft.getEnchantments().isEmpty() && itemRight.getEnchantments().isEmpty()) {
            return;
        }

        ItemStack itemResult;
        if (itemLeft.getType().equals(itemRight.getType())) {
            itemResult = new ItemStack(itemLeft.getType());
        } else {
            if (itemLeft.getType().equals(Material.ENCHANTED_BOOK)) {
                itemResult = new ItemStack(itemRight.getType());
            } else {
                if (itemRight.getType().equals(Material.ENCHANTED_BOOK)) {
                    itemResult = new ItemStack(itemLeft.getType());
                } else {
                    return;
                }
            }
        }

        itemResult.addUnsafeEnchantments(ItemInfo.unsafeEnchCombine(itemLeft.getEnchantments(), itemRight.getEnchantments()));
        event.setResult(itemResult);
    }
}
