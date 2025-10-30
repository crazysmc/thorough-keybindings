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

| Category | Default        | Keybinding                                     |
|----------|----------------|------------------------------------------------|
| misc     | ESCAPE         | gameMenu                                       |
| misc     | F1             | toggleHUD                                      |
| misc     | F3             | debugInfo                                      |
| misc     | F4             | gameMode\_disableShader                        |
| misc     | F6 (F5)        | debugOptionsScreen                             |
| debug    | 1              | debug.charts.profiler                          |
| debug    | 2              | debug.charts.fps                               |
| debug    | 3              | debug.charts.network                           |
| debug    | A              | debug.reload\_chunks                           |
| debug    | B              | debug.show\_hitboxes (+ ctrl: toggle narrator) |
| debug    | C              | debug.copy\_location                           |
| debug    | D              | debug.clear\_chat                              |
| debug    | F              | debug.cycle\_renderdistance                    |
| debug    | G              | debug.chunk\_boundaries                        |
| debug    | H              | debug.advanced\_tooltips                       |
| debug    | I              | debug.inspect                                  |
| debug    | L              | debug.profiling                                |
| debug    | N              | debug.creative\_spectator                      |
| debug    | P              | debug.pause\_focus                             |
| debug    | Q              | debug.help                                     |
| debug    | S              | debug.dump\_dynamic\_textures                  |
| debug    | T              | debug.reload\_resourcepacks                    |
| debug    | V              | debug.client\_version                          |
| modifier | LEFT\_SHIFT    | mod.shift.1                                    |
| modifier | RIGHT\_SHIFT   | mod.shift.2                                    |
| modifier | LEFT\_CONTROL  | mod.ctrl.1                                     |
| modifier | RIGHT\_CONTROL | mod.ctrl.2                                     |
| modifier | LEFT\_ALT      | mod.alt.1                                      |
| modifier | RIGHT\_ALT     | mod.alt.2                                      |

## Some History

| Key        | Version                                  |
|------------|------------------------------------------|
| F3 + F4    | 1.16                                     |
| F3 + L     | 1.17                                     |
| F3 + F     | removed in 1.19                          |
| F3 + S     | 1.19.4 reintroduced since removal in 1.9 |
| F3 + 1/2/3 | 1.20.2                                   |
| F3 + V     | 1.21.6                                   |
| F3 + F6    | 1.21.9                                   |
