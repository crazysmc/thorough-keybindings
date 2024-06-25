package io.github.crazysmc.thrkbs.chatcomponents;

import io.github.crazysmc.thrkbs.core.api.ChatComponents;
import net.minecraft.text.Formatting;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ChatComponentsImpl implements ChatComponents
{
  @Override
  public Text literal(String string)
  {
    return new LiteralText(string);
  }

  @Override
  public Text translatable(String string, Object... objects)
  {
    return new TranslatableText(string, objects);
  }

  @Override
  public Text translatableWithStyle(Formatting style, String string, Object... objects)
  {
    return new TranslatableText(string, objects).setFormatting(style);
  }
}
