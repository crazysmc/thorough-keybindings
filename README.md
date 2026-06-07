# Thorough Keybindings

For as many Minecraft versions as possible, this mod makes the keybindings of
usually fixed mappings rebindable.
This project branch supports releases 26.1+.
Check out the [obfuscated Fabric version](https://github.com/crazysmc/thorough-keybindings/tree/fabric)
for pre-26.1 Minecraft support!

For example, this lets you remap Escape in-game.
Also adds rebinding options for Shift and Control as well as the digits to
navigate the profiling pie chart.

## Usage

The latest release can also be found on
[Modrinth](https://modrinth.com/mod/thorough-keybindings).

This mod uses [Fabric](https://fabricmc.net/) and requires the
[Fabric API](https://modrinth.com/mod/fabric-api) to run.

## List of Keybindings

Check out the
[Mappings](src/main/java/io/github/crazysmc/thrkbs/HardcodedMapping.java)
definitions in the source.
Wherever possible we use the names of mappings later added to the base game,
thus users who upgrade their game can keep their keybindings.

The modifiers Shift and Control only apply to in-game actions like
shift-clicking item stacks and not to text editing, e.g. `Ctrl+A/X/C/V`, nor
menuing, e.g. `Shift+Tab`.

<details><summary>Modifier Keys</summary>

| Modifier | Applies to                       |
|----------|----------------------------------|
| Control  | Ctrl + B (Narrator)              |
| Control  | Ctrl + Drop                      |
| Control  | Ctrl + Pick Block                |
| Control  | Ctrl + F3 + C (Segfault Crash)   |
| Shift    | Shift + Click (Quick-Move Stack) |
| Shift    | Shift + F3 + I                   |

</details>
