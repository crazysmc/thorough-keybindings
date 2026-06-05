# Thorough Keybindings

For as many Minecraft versions as possible, this mod makes the keybindings of
usually fixed mappings rebindable.
This project branch supports releases 1.14 through 1.21.
Check out
the [Ornithe version](https://github.com/crazysmc/thorough-keybindings/tree/ornithe) for legacy and
the [unobfuscated Fabric version](https://github.com/crazysmc/thorough-keybindings/tree/fabric-unobf) for recent
Minecraft support!

For example, this lets you remap Escape and F3 in-game, even in versions
before F3 was made rebindable.
Also adds rebinding options for Shift, Control, Alt as well as the digits to
navigate the profiling pie chart.

Before 1.21.9, Minecraft only supported one action per key and if multiple
bindings were mapped to the same key, only a random single one of these would
fire.
This mod backports the functionality that duplicate bindings activate all
bound keys.

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

The modifiers Shift, Control, Alt only apply to in-game actions like
shift-clicking item stacks and not to text editing, e.g. `Ctrl+A/X/C/V`, nor
menuing, e.g. `Shift+Tab`.

<details><summary>Modifier Keys</summary>

| Modifier | Applies to                       |
|----------|----------------------------------|
| Alt      | Alt + F3 (before 23w33a)         |
| Control  | Ctrl + F3 (before 23w33a)        |
| Control  | Ctrl + B (Narrator)              |
| Control  | Ctrl + Drop                      |
| Control  | Ctrl + Pick Block                |
| Control  | Ctrl + F3 + C (Segfault Crash)   |
| Shift    | Shift + F3 (before 23w33a)       |
| Shift    | Shift + Click (Quick-Move Stack) |
| Shift    | Shift + F3 + F (before 22w12a)   |
| Shift    | Shift + F3 + I                   |

</details>

The
[history on debug hotkeys](https://minecraft.wiki/w/Debug_hotkey#History)
in the Minecraft Wiki provides more details about F3 combinations.
