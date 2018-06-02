package tsk.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tsk.item.OnItem;
import tsk.tskEnchantment.TskEnchantment;

import java.util.ArrayList;
import java.util.List;

public class TskPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new OnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnItem(), this);

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (command.getName()) {
            case "tskenchantment":
                if (args.length < 2) {
                    break;
                }
                ArrayList<String> enchantmentName = new ArrayList<>(Enchantment.values().length);
                for (int i = 0; i < Enchantment.values().length; i++) {
                    enchantmentName.add(Enchantment.values()[i].getName());
                }
                return TskUtil.filterByPrefix(enchantmentName, args[1]);
        }
        return super.onTabComplete(sender, command, alias, args);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName()) {

            case "tskenchantment":
                if (args.length == 3) {
                    int level = Integer.parseInt(args[2]);
                    return TskEnchantment.addUnsafeTskEnchantment(args[0], args[1], level);
                }
                break;

            case "fly":
                if (args.length == 1) {
                    Player player = Bukkit.getPlayer(args[0]);
                    if (player != null) {
                        if (player.getAllowFlight()) {
                            player.setAllowFlight(false);
                        } else {
                            player.setAllowFlight(true);
                        }
                        return true;
                    }
                }
                break;
        }

        return false;
    }
}
