package io.github.crazysmc.thrkbs.version;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import static io.github.crazysmc.thrkbs.DynamicTextReplacer.debugHelpMessage;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F3;

public class RemappedTranslatableComponent extends TranslatableComponent
{
  private final KeyRemapping remapping;

  public RemappedTranslatableComponent(KeyRemapping remapping, String key, Object... args)
  {
    super(key, args);
    this.remapping = remapping;
  }

  @Override
  protected void decomposeTemplate(String string)
  {
    super.decomposeTemplate(string);
    if (decomposedParts.isEmpty())
      return;
    String f3 = KeyRemapping.getByDefault(GLFW_KEY_F3).getTranslatedKeyMessage();
    String key = remapping.getTranslatedKeyMessage();
    decomposedParts.set(0, new TextComponent(debugHelpMessage(decomposedParts.get(0).getContents(), f3, key)));
  }
}
