# Thorough Keybindings

For as many Minecraft versions as possible, this mod makes the keybindings of
usually fixed mappings rebindable.
This project branch supports releases 1.14.4 through 1.20.

For example, this lets you remap F3 or Esc in-game, without external software.
Also adds rebinding options for debug key combinations like F3 + Q.

## Usage

This mod uses [Fabric](https://fabricmc.net/) and requires the
[Fabric API](https://modrinth.com/mod/fabric-api) to run.

## Building

Edit the version in `settings.gradle` (adapt the fabric-api version if
necessary) and run `./gradlew build` or choose a preset setting with
`-Ppreset=NUM`.

## Some History

| Key        | Version                                  |
|------------|------------------------------------------|
| F3 + F4    | 1.16                                     |
| F3 + L     | 1.17                                     |
| F3 + F     | removed in 1.19                          |
| F3 + S     | 1.19.4 reintroduced since removal in 1.9 |
| F3 + 1/2/3 | 1.20.2                                   |
