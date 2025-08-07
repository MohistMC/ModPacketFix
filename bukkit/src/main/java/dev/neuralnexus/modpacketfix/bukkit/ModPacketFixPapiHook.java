package dev.neuralnexus.modpacketfix.bukkit;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ModPacketFixPapiHook extends PlaceholderExpansion {

    private static final String hook_name = "youer";

    private final BukkitModPacketFixPlugin plugin;

    public ModPacketFixPapiHook(BukkitModPacketFixPlugin plugin) {
        this.plugin = plugin;
    }

    public static String replace(OfflinePlayer player, String x) {
        return PlaceholderAPI.setPlaceholders(player, x.replace("&", "§"));
    }

    @Override
    public String onPlaceholderRequest(Player p, @NotNull String i) {
        if (p == null) {
            return null;
        }
        if (i.equalsIgnoreCase("client_name")) {
            if (plugin.isForgeUser(p)) {
                return "Forge";
            } else if (plugin.isNeoForgeUser(p)) {
                return "NeoForge";
            } else if (plugin.isFabricUser(p)) {
                return "Fabric";
            } else {
                return "Vanilla";
            }
        }

        return null;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return hook_name;
    }

    @Override
    public @NotNull String getAuthor() {
        return "MohistMC";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.21.1";
    }
}
