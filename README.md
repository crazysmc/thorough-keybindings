# Thorough Keybindings

For as many Minecraft versions as possible, this mod makes the keybindings of
usually fixed mappings rebindable.

For example, this lets you remap F3 or Esc in-game, without external software.
Also adds rebinding options to earlier game versions, like rebinding F5 or
hotbar slot keys.

Adding keybindings to versions before 1.7 causes the configuration menu to
overflow into the Done button.
Thus, separate submenus for categories of related options are introduced.

## Building

Edit the version in `settings.gradle` (adapt the feather/nest build if
necessary) and run `./gradlew build`.

## Some History

| Key    | Version                                     |
|--------|---------------------------------------------|
| 1 .. 9 | 1.4.2 adds hovering exchange in inventories |
| F3 + C | 1.4.2                                       |
| F3 + H | 1.4.2                                       |
| F3 + P | 1.4.2                                       |
| F3 + B | 1.4.4                                       |
| F4     | 1.7.2                                       |
| F3 + S | same as F3 + T since 1.8, removed in 1.9    |
| F3 + D | 1.8                                         |
| F3 + N | 1.9                                         |
| F3 + Q | 1.9                                         |
| F3 + G | 1.10                                        |
|        | 1.11 renames en_US.lang to en_us.lang       |

## Some Missing Identifiers TODO

| Original Key | New Identifier             |
|--------------|----------------------------|
| (F3 +) F4    | key.debug.gameModeSwitcher |
| (F3 +) 1     | key.debug.profiler         |
| (F3 +) 2     | key.debug.timeGraph        |
| (F3 +) 3     | key.debug.networkGraph     |
| (F3 +) I     | key.debug.copyNbt          |
| (F3 +) L     | key.debug.dumpProfiler     |
| (F3 +) S     | key.debug.dumpTextures     |
