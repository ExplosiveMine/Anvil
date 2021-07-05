package me.explosivemine.anvil.core.listeners;

import me.explosivemine.anvil.Anvil;
import me.explosivemine.anvil.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {
    private final Anvil plugin;

    public PlayerInteractListener(final Anvil plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!event.getClickedBlock().getType().equals(Material.ANVIL)) return;

        final Player player = event.getPlayer();
        if (!player.hasPermission("anvil.unbreakable")) return;

        event.setCancelled(true);

        new AnvilGUI.Builder()
                .itemLeft(new ItemStack(Material.AIR))
                .itemRight(new ItemStack(Material.AIR))
                .title(plugin.getConfigData().getTitle())
                .open(event.getPlayer());

        plugin.getOpenPlayers().add(player.getUniqueId());
    }
}
