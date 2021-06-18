package me.explosivemine.anvil.core;

import lombok.Getter;
import me.explosivemine.anvil.Anvil;
import me.explosivemine.anvil.Util;
import me.explosivemine.anvil.XSound;
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
    private String title;

    //sound
    @Getter
    private boolean playSound;
    private String sound;

    @Getter
    private boolean unbreakableAnvils, anvilColours;

    public ConfigData(final Anvil plugin) {
        this.plugin = plugin;
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

        if (!config.isSet("title")) {
            appendConfig("#This sets the title of the GUI (only works in 1.14+)",
                    "#Color codes and unicode characters work, but might glitch.",
                    "title: \"&6Repair & Name\"");
        }

        if (!config.isSet("unbreakableAnvils")) {
            appendConfig("#This makes it so that anvils never break or get damaged upon use. Players",
                    "#will still be able to break them normally. When anvils fall, they will still get damaged.",
                    "#Possible values: [true, false],",
                    "# true - anvils will not break when used by players",
                    "# false - default anvil mechanics apply",
                    "# If set to true, only players with the permission \"anvil.unbreakable\" will enjoy",
                    "# having anvils that do not break. For others default, anvil mechanics apply.",
                    "unbreakableAnvils: true");
        }

        if (!config.isSet("anvilColours")) {
            appendConfig("#This allows items to be renamed using colour codes in anvils",
                    "#Possible values: [true, false],",
                    "# true - colour codes will work",
                    "# false - colour codes won't work",
                    "# If set to true, only players with the permission \"anvil.colour\" will enjoy",
                    "# anvil colours. For others default, the colour code simply will not work.",
                    "anvilColours: true");
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

        // config.yml
        try {
            FileConfiguration config = plugin.getConfig();

            onConsoleExecuteCommand = config.getString("onConsoleExecuteCommand", "This command cannot be executed from the console!");
            noPermission = config.getString("noPermission", "Unknown command. Type \"help\" for help.");
            title = config.getString("title", "&6Repair & Name");

            playSound = config.getBoolean("playSound", true);
            sound = config.getString("soundName", "ENTITY_ENDERMAN_TELEPORT");

            unbreakableAnvils = config.getBoolean("unbreakableAnvils", true);
            anvilColours = config.getBoolean("anvilColours", true);

            Util.info("Config successfully loaded.");
        } catch (Exception e) {
            Util.severe("The config.yml could not be loaded");
            Util.severe("Please ensure that it is properly configured .");
            cont = false;
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        }
    }
}