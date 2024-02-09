package io.github.crazysmc.thrkbs;

import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThoroughKeybindings implements ClientModInitializer
{
  public static final String MOD_ID = "thorough-keybindings";
  public static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings");

  @Override
  public void initClient()
  {
    CategorizedKeyBinding.initDefaultCategories();
  }

//  private boolean hasF7() throws IOException
//  {
//    ClassReader reader = new ClassReader("net.minecraft.client.Minecraft");
//    ClassNode classNode = new ClassNode();
//    reader.accept(classNode, 0);
//    for (MethodNode method : classNode.methods)
//      if ("runGame".equals(method.name))
//      {
//        for (AbstractInsnNode instruction : method.instructions)
//          if (instruction.getOpcode() == Opcodes.BIPUSH && ((IntInsnNode) instruction).operand == Keyboard.KEY_F7)
//            return true;
//        break;
//      }
//    return false;
//  }
}
