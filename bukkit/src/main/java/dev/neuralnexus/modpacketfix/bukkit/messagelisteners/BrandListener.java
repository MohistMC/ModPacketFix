package dev.neuralnexus.modpacketfix.bukkit.messagelisteners;

import dev.neuralnexus.modpacketfix.bukkit.BukkitModPacketFixPlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

/**
 * Listens for the brand plugin message and registers the player as a forge user if they are using forge
 */
public class BrandListener implements PluginMessageListener {
    private final BukkitModPacketFixPlugin plugin;
    public BrandListener(BukkitModPacketFixPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] message) {
        if (channel.equals("MC|Brand") || channel.equals("minecraft:brand")) {
            String clientName = new String(message).trim().toLowerCase();
            if (Brand.brand(clientName) == Brand.FORGE) {
                plugin.addForgeUser(player);
            } else if (Brand.brand(clientName) == Brand.NEOFORGE) {
                plugin.addNeoForgeUser(player);
            } else if (Brand.brand(clientName) == Brand.FABRIC) {
                plugin.addFabricUser(player);
            } else {
                plugin.removeForgeUser(player);
                plugin.removeNeoForgeUser(player);
                plugin.removeFabricUser(player);
            }
        }
    }

    public enum Brand {
        FORGE("forge"),
        NEOFORGE("neoforge"),
        FABRIC("fabric"),
        QUILT("quilt"),
        VANILLA("vanilla");

        private final String name;

        Brand(String name) {
            this.name = name;
        }

        public static Brand brand(String name) {
            for (Brand brand : values()) {
                if (brand.name.equals(name)) {
                    return brand;
                }
            }
            return Brand.VANILLA;
        }
    }
}
