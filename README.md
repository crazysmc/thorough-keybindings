# Thorough Keybindings

For as many Minecraft versions as possible, this mod makes the keybindings of
usually fixed mappings rebindable.
This project branch supports beta 1.0 through release 1.14.

For example, this lets you remap F3 or Esc in-game, without external software.
Furthermore, backports rebinding options to earlier game versions, like
rebinding F5 or hotbar slot keys.

Adding keybindings to versions before 1.7 causes the configuration menu to
overflow into the Done button.
Thus, separate submenus for categories of related options are introduced.

## Usage

This mod uses [Ornithe](https://ornithemc.net/) with the Fabric Mod Loader and
requires the [Ornithe Standard Libraries](https://modrinth.com/mod/osl) to
run.

## List of Keybindings

### before 1.13

[//]: # (TODO add list)

### since 1.13

| Category | Default       | Keybinding                                         |
|----------|---------------|----------------------------------------------------|
| misc     | ESCAPE        | gameMenu                                           |
| misc     | F1            | toggleHUD                                          |
| misc     | F3            | debugInfo                                          |
| misc     | F4            | disableShader (with debugInfo: game mode switcher) |
| debug    | 1             | debug.charts.profiler                              |
| debug    | 2             | debug.charts.fps                                   |
| debug    | 3             | debug.charts.network                               |
| debug    | A             | debug.reload_chunks                                |
| debug    | B             | debug.show_hitboxes (with ctrl: toggle narrator)   |
| debug    | C             | debug.copy_location                                |
| debug    | D             | debug.clear_chat                                   |
| debug    | F             | debug.cycle_renderdistance                         |
| debug    | G             | debug.chunk_boundaries                             |
| debug    | H             | debug.advanced_tooltips                            |
| debug    | I             | debug.inspect                                      |
| debug    | L             | debug.profiling                                    |
| debug    | N             | debug.creative_spectator                           |
| debug    | P             | debug.pause_focus                                  |
| debug    | Q             | debug.help                                         |
| debug    | S             | debug.dump_dynamic_textures                        |
| debug    | T             | debug.reload_resourcepacks                         |
| modifier | LEFT_SHIFT    | mod.shift.1                                        |
| modifier | RIGHT_SHIFT   | mod.shift.2                                        |
| modifier | LEFT_CONTROL  | mod.ctrl.1                                         |
| modifier | RIGHT_CONTROL | mod.ctrl.2                                         |
| modifier | LEFT_ALT      | mod.alt.1                                          |
| modifier | RIGHT_ALT     | mod.alt.2                                          |

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
