package tsk.main;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.Collection;

public class OnEvent implements Listener {

    @EventHandler
    public void showExp(PlayerExpChangeEvent event) {
        event.getPlayer().sendMessage(String.format("Exp + %s%d", ChatColor.GREEN, event.getAmount()));
    }

    @EventHandler
    public void rideAnyone(PlayerInteractAtEntityEvent event) {
        if (event.getPlayer().getEquipment().getItemInMainHand().getType().equals(Material.SADDLE)) {
            event.getRightClicked().addPassenger(event.getPlayer());
        }
    }

    @EventHandler
    public void dealDeath(PlayerDeathEvent event) {
//        event.setKeepInventory(true);
        TskUtil.healPlayer(event.getEntity());
        event.getEntity().setGameMode(GameMode.SPECTATOR);
        ((ExperienceOrb) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.EXPERIENCE_ORB)).setExperience(event.getEntity().getTotalExperience());
        event.getEntity().setTotalExperience(0);
        event.getEntity().setExp(0.0F);
        event.getEntity().setLevel(0);
    }


    @EventHandler
    public void dropPotionOnDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Collection<PotionEffect> activePotionEffects = event.getEntity().getActivePotionEffects();
            for (PotionEffect effect :
                    activePotionEffects) {

                ItemStack itemStack = new ItemStack(Material.POTION);
                PotionMeta potionMeta = ((PotionMeta) itemStack.getItemMeta());

                potionMeta.addCustomEffect(effect, true);
                potionMeta.setColor(effect.getType().getColor());
//                potionMeta.setDisplayName(ChatColor.RESET + effect.getType().getName());

                itemStack.setItemMeta(potionMeta);
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
            }
        }
    }

    @EventHandler
    public void transferBuffToMonster(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            Entity damager = ((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager();
            if (damager instanceof LivingEntity) {
                Collection<PotionEffect> potionEffects = player.getActivePotionEffects();
                ((LivingEntity) damager).addPotionEffects(potionEffects);
                for (PotionEffect potionEffect :
                        potionEffects) {
                    player.removePotionEffect(potionEffect.getType());
                }
            }
        }

    }

//    @EventHandler
//    public void move(PlayerMoveEvent event) {
//        event.getPlayer().sendMessage("PlayerMoveEvent");
//    }
//
//    @EventHandler
//    public void vectorChange(PlayerVelocityEvent event) {
//        event.getPlayer().sendMessage("PlayerVelocityEvent");
//    }

    @EventHandler
    public void dropEquipmentForPoorPlayer(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Monster) {
            Player player = ((Monster) entity).getKiller();
            if (player != null) {
                EntityEquipment playerEquipment = player.getEquipment();
                EntityEquipment entityEquipment = ((Monster) entity).getEquipment();

                if (playerEquipment.getItemInMainHand().getType().equals(Material.AIR)
//                        && !event.getDrops().contains(entityEquipment.getItemInMainHand())
                        ) {
                    event.getDrops().add(entityEquipment.getItemInMainHand());
                }
                if (playerEquipment.getItemInOffHand().getType().equals(Material.AIR)
//                        && !event.getDrops().contains(entityEquipment.getItemInOffHand())
                        ) {
                    event.getDrops().add(entityEquipment.getItemInOffHand());
                }

                if (playerEquipment.getBoots() == null
//                        && !event.getDrops().contains(entityEquipment.getBoots())
                        ) {
                    event.getDrops().add(entityEquipment.getBoots());
                }
                if (playerEquipment.getLeggings() == null
//                        && !event.getDrops().contains(entityEquipment.getLeggings())
                        ) {
                    event.getDrops().add(entityEquipment.getLeggings());
                }
                if (playerEquipment.getChestplate() == null
//                        && !event.getDrops().contains(entityEquipment.getChestplate())
                        ) {
                    event.getDrops().add(entityEquipment.getChestplate());
                }
                if (playerEquipment.getHelmet() == null
//                        && !event.getDrops().contains(entityEquipment.getHelmet())
                        ) {
                    event.getDrops().add(entityEquipment.getHelmet());
                }
            }
        }
    }
}
