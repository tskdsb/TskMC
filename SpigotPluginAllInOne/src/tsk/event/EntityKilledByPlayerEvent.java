package tsk.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityKilledByPlayerEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }


    private Entity dead;
    private Player killer;

    public EntityKilledByPlayerEvent(Entity entity, Player player) {
        dead = entity;
        killer = player;
    }

    public Entity getDead() {
        return dead;
    }

    public Player getKiller() {
        return killer;
    }
}