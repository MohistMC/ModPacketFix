package dev.neuralnexus.modpacketfix.bukkit.eventlisteners;

import dev.neuralnexus.modpacketfix.bukkit.BukkitModPacketFixPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerEventListener implements Listener {
    private final BukkitModPacketFixPlugin plugin;
    public PlayerEventListener(BukkitModPacketFixPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Remove the player from the forge users list when they disconnect
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (plugin.isForgeUser(event.getPlayer())) {
            plugin.removeForgeUser(event.getPlayer());
        }

        if (plugin.isNeoForgeUser(event.getPlayer())) {
            plugin.removeNeoForgeUser(event.getPlayer());
        }

        if (plugin.isFabricUser(event.getPlayer())) {
            plugin.removeFabricUser(event.getPlayer());
        }
    }
}
