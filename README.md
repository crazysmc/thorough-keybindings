# Thorough Keybindings

For as many Minecraft versions as possible, this mod makes the keybindings of
usually fixed mappings rebindable.
This project branch supports releases 1.14 through 1.21.
Check out the Ornithe version for legacy support!

For example, this lets you remap F3 or Esc in-game, without external software.
Also adds rebinding options for debug key combinations like F3 + Q.

Before 1.21.9, Minecraft only supported one action per key and if multiple
bindings were mapped to the same key, only a random single one of these would
fire.
This mod backports the functionality that duplicate bindings activate all
bound keys.

## Usage

The latest release can also be found
on [Modrinth](https://modrinth.com/mod/thorough-keybindings).

This mod uses [Fabric](https://fabricmc.net/) and requires the
[Fabric API](https://modrinth.com/mod/fabric-api) to run.

## List of Keybindings

Check out the
[Mappings](src/main/java/io/github/crazysmc/thrkbs/HardcodedMapping.java)
definitions in the source.
Wherever possible we use the names of mappings later added to the base game,
thus users who upgrade their game can keep their keybindings.

The
[history on debug hotkeys](https://minecraft.wiki/w/Debug_hotkey#History)
in the Minecraft Wiki provides more details about F3 combinations.
