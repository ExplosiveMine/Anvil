package me.explosivemine.anvil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.TreeMap;

public class Util {
    private static final String PREFIX = "[Anvil] ";

    public static void info(String text) {
        Bukkit.getServer().getLogger().info(PREFIX + text);
    }

    public static void severe(String text) {
        Bukkit.getServer().getLogger().severe(PREFIX + text);
    }

    public static String chat(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
