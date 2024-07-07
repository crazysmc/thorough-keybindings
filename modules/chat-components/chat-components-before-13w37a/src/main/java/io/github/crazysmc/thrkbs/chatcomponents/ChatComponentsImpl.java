package io.github.crazysmc.thrkbs.chatcomponents;

import io.github.crazysmc.thrkbs.core.api.ChatComponents;
import net.minecraft.text.Formatting;
import net.minecraft.text.Text;

public class ChatComponentsImpl implements ChatComponents
{
  @Override
  public Text literal(String string)
  {
    return Text.literal(string);
  }

  @Override
  public Text translatable(String string, Object... objects)
  {
    return Text.translatable(string, objects);
  }

  @Override
  public Text translatableWithStyle(Formatting style, String string, Object... objects)
  {
    return Text.translatable(string, objects).setColor(style);
  }
}
