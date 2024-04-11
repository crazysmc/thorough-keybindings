# Thorough Keybindings

For as many Minecraft versions as possible, this mod makes the keybindings of
usually fixed mappings rebindable.
This project branch supports releases 1.14 through 1.20.

For example, this lets you remap F3 or Esc in-game, without external software.
Also adds rebinding options to earlier game versions, like rebinding F5 or
hotbar slot keys.

## Building & Usage

Edit the version in `settings.gradle` (adapt the feather/nest build if
necessary) and run `./gradlew build` or choose a preset setting with
`-Ppreset=NUM`.

This mod uses [Fabric](https://fabricmc.net/) and requires the
[Fabric API](https://modrinth.com/mod/fabric-api) to run.

## Some History

| Key     | Version |
|---------|---------|
| F3 + F4 | 1.16    |

<!--## Some Missing Identifiers TODO

| Original Key | New Identifier             |
|--------------|----------------------------|
| (F3 +) F4    | key.debug.gameModeSwitcher |
| (F3 +) 1     | key.debug.profiler         |
| (F3 +) 2     | key.debug.timeGraph        |
| (F3 +) 3     | key.debug.networkGraph     |
| (F3 +) I     | key.debug.inspect          |
| (F3 +) L     | key.debug.dumpProfiler     |
| (F3 +) S     | key.debug.dumpTextures     |-->
