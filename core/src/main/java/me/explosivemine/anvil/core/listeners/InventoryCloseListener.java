package me.explosivemine.anvil.core.listeners;

import me.explosivemine.anvil.Anvil;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryCloseListener implements Listener {
    private final Anvil plugin;

    public InventoryCloseListener(final Anvil plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getInventory() instanceof AnvilInventory)) return;
        if (!plugin.getOpenPlayers().contains(event.getPlayer().getUniqueId())) return;

        Inventory inv = event.getInventory();
        Player player = (Player) event.getPlayer();

        addItem(player, inv.getItem(0));
        addItem(player, inv.getItem(1));

        player.updateInventory();
        plugin.getOpenPlayers().remove(player.getUniqueId());
    }

    private void addItem(HumanEntity player, ItemStack item) {
        if (item == null) return;
        player.getInventory().addItem(item).values()
                .forEach(droppedItem -> player.getWorld().dropItemNaturally(player.getLocation(), droppedItem));
    }
}
