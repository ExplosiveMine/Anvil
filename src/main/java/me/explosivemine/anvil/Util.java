package me.explosivemine.anvil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Util {
    private static final String prefix = "[Anvil] ";

    public static void info(String text) {
        Bukkit.getServer().getLogger().info(prefix + text);
    }

    public static void severe(String text) {
        Bukkit.getServer().getLogger().severe(prefix + text);
    }

    public static String chat(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
