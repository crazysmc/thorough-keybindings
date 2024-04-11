# Thorough Keybindings

For as many Minecraft versions as possible, this mod makes the keybindings of
usually fixed mappings rebindable.
This project branch supports releases 1.3 through 1.13.

For example, this lets you remap F3 or Esc in-game, without external software.
Also adds rebinding options to earlier game versions, like rebinding F5 or
hotbar slot keys.

Adding keybindings to versions before 1.7 causes the configuration menu to
overflow into the Done button.
Thus, separate submenus for categories of related options are introduced.

## Building & Usage

Edit the version in `settings.gradle` (adapt the feather/nest build if
necessary) and run `./gradlew build` or choose a preset setting with
`-Ppreset=NUM`.

This mod uses [Ornithe](https://ornithemc.net/) and requires the
[Ornithe Standard Libraries](https://modrinth.com/mod/osl) to run.

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
|        | 1.11 renames `en_US.lang` to `en_us.lang`   |
| F3 + C | 1.13, without hold                          |
| F3 + I | 1.13                                        |
