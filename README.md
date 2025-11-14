# Thorough Keybindings

For as many Minecraft versions as possible, this mod makes the keybindings of
usually fixed mappings rebindable.
This project branch supports releases 1.14 and earlier.
Check out the Fabric version for latest game support!

For example, this lets you remap Escape and F3 in-game, as well as F5 and
hotbar slot keys, where applicable.
Also adds rebinding options for Shift, Control and Alt.

Before 1.21.9, Minecraft only supported one action per key and if multiple
bindings were mapped to the same key, only a random single one of these would
fire.
This mod backports the functionality that duplicate bindings activate all
bound keys.

## Usage

The latest release can also be found on
[Modrinth](https://modrinth.com/mod/thorough-keybindings).

This mod uses [Ornithe](https://ornithemc.net/) and requires the
[Ornithe Standard Libraries](https://modrinth.com/mod/osl) to run.

## List of Keybindings

Check out the
[Mappings](src/main/java/io/github/crazysmc/thrkbs/HardcodedMapping.java)
definitions in the source.
Wherever possible we use the names of mappings later added to the base game,
thus users who upgrade their game can keep their keybindings.

The
[history on debug hotkeys](https://minecraft.wiki/w/Debug_hotkey#History)
in the Minecraft Wiki provides more details about F3 combinations.
