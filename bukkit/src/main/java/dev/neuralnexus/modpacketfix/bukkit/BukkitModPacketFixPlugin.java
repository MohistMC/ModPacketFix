package dev.neuralnexus.modpacketfix.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dev.neuralnexus.modpacketfix.bukkit.config.ConfigLoader;
import dev.neuralnexus.modpacketfix.bukkit.eventlisteners.PlayerEventListener;
import dev.neuralnexus.modpacketfix.bukkit.messagelisteners.BrandListener;
import dev.neuralnexus.modpacketfix.bukkit.packetlisteners.server.RECIPES_SPacketRecipeBook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import java.util.ArrayList;

/**
 * The ModPacketFix Bukkit plugin.
 */
public class BukkitModPacketFixPlugin extends JavaPlugin {

    private BukkitModPacketFixPlugin plugin;

    private final ArrayList<String> forgeUsers = new ArrayList<>();
    private final ArrayList<String> neoforgeUsers = new ArrayList<>();
    private final ArrayList<String> fabricUsers = new ArrayList<>();

    public BukkitModPacketFixPlugin api(){
        return plugin;
    }

    /**
     * If the user is using forge
     * @param player The player to check
     * @return If the user is using forge
     */
    public boolean isForgeUser(Player player) {
        return forgeUsers.contains(player.getName());
    }

    /**
     * Add a user to the forge users list
     * @param player The player to add
     */
    public void addForgeUser(Player player) {
        if (!isForgeUser(player)) forgeUsers.add(player.getName());
    }

    /**
     * Remove a user from the forge users list
     * @param player The player to remove
     */
    public void removeForgeUser(Player player) {
        forgeUsers.remove(player.getName());
    }

    /**
     * If the user is using neoforge
     * @param player The player to check
     * @return If the user is using neoforge
     */
    public boolean isNeoForgeUser(Player player) {
        return neoforgeUsers.contains(player.getName());
    }

    /**
     * Add a user to the neoforge users list
     * @param player The player to add
     */
    public void addNeoForgeUser(Player player) {
        if (!isNeoForgeUser(player)) neoforgeUsers.add(player.getName());
    }

    /**
     * Remove a user from the neoforge users list
     * @param player The player to remove
     */
    public void removeNeoForgeUser(Player player) {
        neoforgeUsers.remove(player.getName());
    }

    /**
     * If the user is using fabric
     * @param player The player to check
     * @return If the user is using fabric
     */
    public boolean isFabricUser(Player player) {
        return fabricUsers.contains(player.getName());
    }

    /**
     * Add a user to the fabric users list
     * @param player The player to add
     */
    public void addFabricUser(Player player) {
        if (!isFabricUser(player)) fabricUsers.add(player.getName());
    }

    /**
     * Remove a user from the fabric users list
     * @param player The player to remove
     */
    public void removeFabricUser(Player player) {
        fabricUsers.remove(player.getName());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onEnable() {
        plugin = this;
        ConfigLoader.loadConfig();

        Messenger messenger = getServer().getMessenger();
        PluginManager pluginManager = getServer().getPluginManager();
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        // Clear the forge users list every 5 minutes
        getServer().getScheduler().scheduleSyncRepeatingTask(this, forgeUsers::clear, 0L, 6000L);

        // Register the plugin message listener
//        messenger.registerIncomingPluginChannel(this, "MC|Brand", new BrandListener(this));
        messenger.registerIncomingPluginChannel(this, "minecraft:brand", new BrandListener(this));

        // Register player event listener
        pluginManager.registerEvents(new PlayerEventListener(this), this);

        // Fixes the recipe book packet being too large for the client to handle.
        manager.addPacketListener(new RECIPES_SPacketRecipeBook(this));

        // Test packet listener
//        manager.addPacketListener(new TestPacketListener(this, PacketType.Login.Client.CUSTOM_PAYLOAD, false));
        new ModPacketFixPapiHook(this).register();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onDisable() {
        getLogger().info("Clearing forge users list");
        forgeUsers.clear();
        neoforgeUsers.clear();
        fabricUsers.clear();
        new ModPacketFixPapiHook(this).unregister();
    }
}
