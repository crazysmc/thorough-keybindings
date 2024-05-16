package io.github.crazysmc.thrkbs;

import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PotentialKeyBindingTest
{
  @Test
  void getFoundBindingsEmpty()
  {
    assertEquals(0, PotentialKeyBinding.getFoundBindings().count());
  }

  @Test
  void getFoundBindingsFound0()
  {
    PotentialKeyBinding.found(27);
    assertEquals(0, PotentialKeyBinding.getFoundBindings().count());
  }

  @Test
  void getFoundBindingsFound1() throws NoSuchFieldException, IllegalAccessException
  {
    PotentialKeyBinding.found(GLFW.GLFW_KEY_F3);
    List<PotentialKeyBinding> list = PotentialKeyBinding.getFoundBindings().collect(Collectors.toList());
    Field found = PotentialKeyBinding.class.getDeclaredField("found");
    found.setAccessible(true);
    for (PotentialKeyBinding binding : list)
      found.setBoolean(binding, false);
    assertEquals(1, list.size());
  }
}
