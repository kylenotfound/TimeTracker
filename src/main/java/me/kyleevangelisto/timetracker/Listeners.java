package me.kyleevangelisto.timetracker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class Listeners implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        UUID uuid = event.getPlayer().getUniqueId(); //on player join get unique id
        PlayerManager.getInstance().trackNewPlayer(uuid); // get instance of player joins -- do this once
        //get there uuid by passing it in to the method.
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        UUID uuid = event.getPlayer().getUniqueId(); //on player join get unique id
        PlayerManager.getInstance().onPlayerLeave(uuid); //get instance of when player leaves -- do this once
        //get there uuid by passing it in to the method.
    }

}
