# Thorough Keybindings

For as many Minecraft versions as possible, this mod makes the keybindings of
usually fixed mappings rebindable.
This project branch supports releases 1.14 through 1.20.

For example, this lets you remap F3 or Esc in-game, without external software.
Also adds rebinding options for debug key combinations like F3 + Q.

## Usage

This mod uses [Fabric](https://fabricmc.net/) and requires the
[Fabric API](https://modrinth.com/mod/fabric-api) to run.

## List of Keybindings

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

| Key        | Version                                  |
|------------|------------------------------------------|
| F3 + F4    | 1.16                                     |
| F3 + L     | 1.17                                     |
| F3 + F     | removed in 1.19                          |
| F3 + S     | 1.19.4 reintroduced since removal in 1.9 |
| F3 + 1/2/3 | 1.20.2                                   |
