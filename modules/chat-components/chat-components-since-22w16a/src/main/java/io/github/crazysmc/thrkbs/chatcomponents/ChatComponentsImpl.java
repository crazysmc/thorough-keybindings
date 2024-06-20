package io.github.crazysmc.thrkbs.chatcomponents;

import io.github.crazysmc.thrkbs.core.api.ChatComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ChatComponentsImpl implements ChatComponents
{
  @Override
  public Component literal(String string)
  {
    return Component.literal(string);
  }

  @Override
  public Component translatable(String string, Object... objects)
  {
    return Component.translatable(string, objects);
  }

  @Override
  public Component translatableWithStyle(ChatFormatting style, String string, Object... objects)
  {
    return Component.translatable(string, objects).withStyle(style);
  }
}
