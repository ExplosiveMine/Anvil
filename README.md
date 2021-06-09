# Anvil (1.8-1.16)
![#3 Januar Download (1)](https://user-images.githubusercontent.com/63336853/120971786-1a8a2580-c77e-11eb-8d3f-b71c063cfd08.jpg)

## Features
- Open the anvil gui from anywhere - /anvil 
- Make anvils unbreakable for certain players or all of them
- Allow certain players to rename items with colour codes

## Commands and Permissions
- /anvil - (Permission: `anvil.open`) Opens the anvil gui from anywhere!
- /anvil reload - (Permission: `anvil.admin`) Reloads the config files
- Permission: `anvil.colour` If the option `anvilColours` is set to true in the config,
 players with this permission may rename items using colour codes
- Permission: `anvil.unbreakable` If the option `unbreakableAnvils` is set to true in the config,
 players with this permission will not have their anvils broken/damaged when used.

## Config.yml
```yaml
#This determines whether a sound is played when the command /anvil is run and
#the gui is opened
#Possible values: [true, false],
# true - a sound will be played
# false - a sound will not be played
playSound: true

#This determines the sound that is played (if the above setting is set to true)
#Find the list of sounds for your minecraft version.
#For 1.16.5, this is the list: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html
soundName: "ENTITY_ENDERMAN_TELEPORT"
    
#This is the message sent to the console when the command /anvil is executed
#Set the message to "none" to disable.
#Color codes and unicode characters work, but might glitch.
onConsoleExecuteCommand: "This command cannot be executed from the console!"

#This is the message sent to a player who does not have the permission to
#execute the command /anvil
#Set the message to "none" to disable.
#Color codes and unicode characters work, but might glitch.
noPermission: "Unknown command. Type \"help\" for help."

#This sets the title of the GUI (only works in 1.14+)
#Color codes and unicode characters work, but might glitch.
title: "&6Repair & Name"

#This makes it so that anvils never break or get damaged upon use. Players,
#will still be able to break them normally. When anvils fall, they will still get damaged.
#Possible values: [true, false],
# true - anvils will not break when used by players
# false - default anvil mechanics apply
# If set to true, only players with the permission \"anvil.unbreakable\" will enjoy
# having anvils that do not break. For others default, anvil mechanics apply.
unbreakableAnvils: true

#This allows items to be renamed using colour codes in anvils
#Possible values: [true, false],
# true - colour codes will work
# false - colour codes won't work
# If set to true, only players with the permission \"anvil.colour\" will enjoy
# anvil colours. For others default, the colour code simply will not work.
anvilColours: true

```
