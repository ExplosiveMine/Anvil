package me.explosivemine.anvil.core;

import me.explosivemine.anvil.Anvil;
import me.explosivemine.anvil.Util;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class AnvilCmd implements CommandExecutor {
    private final Anvil plugin;

    public AnvilCmd() {
        this.plugin = Anvil.getINSTANCE();
        plugin.getCommand("anvil").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // /anvil reload
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage("Reloading config...");
            plugin.getConfigData().load();
        } else {
            if (!(sender instanceof Player)) {
                String msg = plugin.getConfigData().getOnConsoleExecuteCommand();
                if (msg == null) return true;
                sender.sendMessage(msg);
                return true;
            }

            Player player = (Player) sender;
            if (!player.hasPermission("anvil.open")) {
                String msg = plugin.getConfigData().getNoPermission();
                if (msg != null) player.sendMessage(msg);
                return true;
            }

            if (plugin.getConfigData().isPlaySound()) {
                player.playSound(player.getLocation(), plugin.getConfigData().getSound(), 0.5F, 0.5F);
            }

            new AnvilGUI.Builder()
                    .text(Util.chat(plugin.getConfigData().getText()))
                    .onComplete((p, text) -> AnvilGUI.Response.close())
                    .itemRight(new ItemStack(Material.GOLD_BLOCK))
                    .itemLeft(new ItemStack(Material.AIR))
                    .title(Util.chat(plugin.getConfigData().getTitle()))
                    .plugin(plugin)
                    .open(player);
        }
        return true;
    }
}