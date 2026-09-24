package io.github.crazysmc.thrkbs;

import java.util.Arrays;
import java.util.stream.Stream;

import static io.github.crazysmc.thrkbs.InputConstants.*;
import static io.github.crazysmc.thrkbs.MappingCategory.*;
import static io.github.crazysmc.thrkbs.ThoroughKeybindings.HAS_GETDIGIT;

public enum HardcodedMapping
{
  GAME_MENU(MISC, KEY_ESCAPE, "gameMenu"),
  NARRATOR(MISC, KEY_B, "narrator"),

  SHIFT_1(MODIFIER, KEY_LSHIFT, "mod.shift.1"),
  SHIFT_2(MODIFIER, KEY_RSHIFT, "mod.shift.2"),
  CTRL_1(MODIFIER, KEY_LCONTROL, "mod.ctrl.1"),
  CTRL_2(MODIFIER, KEY_RCONTROL, "mod.ctrl.2"),

  PROFILER_0(PROFILER, KEY_0, "profiler.up", HAS_GETDIGIT),
  PROFILER_1(PROFILER, KEY_1, "profiler.1", HAS_GETDIGIT),
  PROFILER_2(PROFILER, KEY_2, "profiler.2", HAS_GETDIGIT),
  PROFILER_3(PROFILER, KEY_3, "profiler.3", HAS_GETDIGIT),
  PROFILER_4(PROFILER, KEY_4, "profiler.4", HAS_GETDIGIT),
  PROFILER_5(PROFILER, KEY_5, "profiler.5", HAS_GETDIGIT),
  PROFILER_6(PROFILER, KEY_6, "profiler.6", HAS_GETDIGIT),
  PROFILER_7(PROFILER, KEY_7, "profiler.7", HAS_GETDIGIT),
  PROFILER_8(PROFILER, KEY_8, "profiler.8", HAS_GETDIGIT),
  PROFILER_9(PROFILER, KEY_9, "profiler.9", HAS_GETDIGIT),
  ;

  private final MappingCategory category;
  private final int keyCode;
  private final String name;
  private final boolean exists;

  HardcodedMapping(MappingCategory category, int keyCode, String name)
  {
    this(category, keyCode, name, true);
  }

  HardcodedMapping(MappingCategory category, int keyCode, String name, boolean exists)
  {
    this.category = category;
    this.keyCode = keyCode;
    this.name = name;
    this.exists = exists;
  }

  public static Stream<HardcodedMapping> getExisting()
  {
    return Arrays.stream(values()).filter(mapping -> mapping.exists);
  }

  public MappingCategory getCategory()
  {
    return category;
  }

  public int getKeyCode()
  {
    return keyCode;
  }

  public String getId()
  {
    return String.format("key.%s", name);
  }
}
