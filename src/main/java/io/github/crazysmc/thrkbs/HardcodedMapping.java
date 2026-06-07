package io.github.crazysmc.thrkbs;

import java.util.Arrays;
import java.util.stream.Stream;

import static io.github.crazysmc.thrkbs.MappingCategory.*;
import static org.lwjgl.glfw.GLFW.*;

public enum HardcodedMapping
{
  GAME_MENU(MISC, GLFW_KEY_ESCAPE, "gameMenu"),
  NARRATOR(MISC, GLFW_KEY_B, "narrator"),

  SHIFT_1(MODIFIER, GLFW_KEY_LEFT_SHIFT, "mod.shift.1"),
  SHIFT_2(MODIFIER, GLFW_KEY_RIGHT_SHIFT, "mod.shift.2"),
  CTRL_1(MODIFIER, GLFW_KEY_LEFT_CONTROL, "mod.ctrl.1"),
  CTRL_2(MODIFIER, GLFW_KEY_RIGHT_CONTROL, "mod.ctrl.2"),

  PROFILER_0(PROFILER, GLFW_KEY_0, "profiler.up"),
  PROFILER_1(PROFILER, GLFW_KEY_1, "profiler.1"),
  PROFILER_2(PROFILER, GLFW_KEY_2, "profiler.2"),
  PROFILER_3(PROFILER, GLFW_KEY_3, "profiler.3"),
  PROFILER_4(PROFILER, GLFW_KEY_4, "profiler.4"),
  PROFILER_5(PROFILER, GLFW_KEY_5, "profiler.5"),
  PROFILER_6(PROFILER, GLFW_KEY_6, "profiler.6"),
  PROFILER_7(PROFILER, GLFW_KEY_7, "profiler.7"),
  PROFILER_8(PROFILER, GLFW_KEY_8, "profiler.8"),
  PROFILER_9(PROFILER, GLFW_KEY_9, "profiler.9"),
  ;

  private final MappingCategory category;
  private final int keyCode;
  private final String name;

  HardcodedMapping(MappingCategory category, int keyCode, String name)
  {
    this.category = category;
    this.keyCode = keyCode;
    this.name = name;
  }

  public static Stream<HardcodedMapping> getExisting()
  {
    return Arrays.stream(values());
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
