package io.github.crazysmc.thrkbs;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.ClassNode;

class BytecodeInspectionTest
{
  @Test
  void acceptClassNode()
  {
    BytecodeInspection.acceptClassNode(new ClassNode());
  }

  @Test
  void acceptMinecraftClassNode()
  {
    BytecodeInspection.acceptMinecraftClassNode(new ClassNode());
  }
}
