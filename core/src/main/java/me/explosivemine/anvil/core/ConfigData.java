package me.explosivemine.anvil.core;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigData {
    private final Anvil plugin;
    public boolean cont = true;

    // lang
    private String onConsoleExecuteCommand;
    private String noPermission;
    private String text;
    private String title;

    //sound
    @Getter
    private boolean playSound;
    private String sound;

    public ConfigData() {
        plugin = Anvil.getINSTANCE();
        load();
    }

    public Sound getSound() {
        return XSound.matchXSound(sound).map(XSound :: parseSound).orElse(null);
    }

    public String getOnConsoleExecuteCommand() {
        if (onConsoleExecuteCommand.equalsIgnoreCase("none")) return null;
        return Util.chat(onConsoleExecuteCommand);
    }

    public String getNoPermission() {
        if (noPermission.equalsIgnoreCase("none")) return null;
        return Util.chat(noPermission);
    }

    public String getText() {
        if (text.equalsIgnoreCase("none")) return null;
        return Util.chat(text);
    }

    public String getTitle() {
        return Util.chat(title);
    }

    public void checkConfigForMissingOptions() {
        FileConfiguration config = plugin.getConfig();

        if (!config.isSet("playSound")) {
            appendConfig("#This determines whether a sound is played when the command /anvil is run and",
                    "#the gui is opened",
                    "#Possible values: [true, false],",
                    "# true - a sound will be played",
                    "# false - a sound will not be played",
                    "playSound: true");
        }

        if (!config.isSet("soundName")) {
            appendConfig("#This determines the sound that is played (if the above setting is set to true)",
                    "#Find the list of sounds for your minecraft version.",
                    "#For 1.16.5, this is the list: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html",
                    "soundName: \"ENTITY_ENDERMAN_TELEPORT\"");
        }

        if (!config.isSet("onConsoleExecuteCommand")) {
            appendConfig("#This is the message sent to the console when the command /anvil is executed",
                    "#Set the message to \"none\" to disable.",
                    "#Color codes and unicode characters work, but might glitch.",
                    "onConsoleExecuteCommand: \"This command cannot be executed from the console!\"");
        }

        if (!config.isSet("noPermission")) {
            appendConfig("#This is the message sent to a player who does not have the permission to",
                    "#execute the command /anvil",
                    "#Set the message to \"none\" to disable.",
                    "#Color codes and unicode characters work, but might glitch.",
                    "noPermission: \"Unknown command. Type \\\"help\\\" for help.\"");
        }

        if (!config.isSet("text")) {
            appendConfig("#This is the text the anvil gui starts with.",
                    "#Color codes and unicode characters work, but might glitch.",
                    "#Set the message to \"none\" to disable.",
                    "text: \" \"");
        }

        if (!config.isSet("title")) {
            appendConfig("#This sets the title of the GUI (only works in 1.14+)",
                    "#Color codes and unicode characters work, but might glitch.",
                    "title: \"&6Repair & Name\"");
        }
    }

    private void appendConfig(String... lines) {
        File dataDir = plugin.getDataFolder();
        if (!dataDir.exists()) dataDir.mkdirs();

        File file = new File(dataDir, "config.yml");

        try {
            FileWriter writer = new FileWriter(file, true);
            String newLine = "\r\n";
            writer.append(newLine);
            for (String line : lines) {
                writer.append(line);
                writer.append(newLine);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        plugin.reloadConfig();

        checkConfigForMissingOptions();

        try {
            FileConfiguration config = plugin.getConfig();

            onConsoleExecuteCommand = config.getString("onConsoleExecuteCommand", "This command cannot be executed from the console!");
            noPermission = config.getString("noPermission", "Unknown command. Type \"help\" for help.");
            text = config.getString("text", " ");
            title = config.getString("title", "&6Repair & Name");

            playSound = config.getBoolean("playSound", true);
            sound = config.getString("soundName", "ENTITY_ENDERMAN_TELEPORT");

            Util.info("Config successfully loaded.");
        } catch (Exception e) {
            Util.severe("The config could not be loaded");
            Util.severe("Please ensure that it is configured properly.");
            cont = false;
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        }
    }
}