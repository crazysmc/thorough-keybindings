package io.github.crazysmc.thrkbs.chatcomponents;

import io.github.crazysmc.thrkbs.core.api.ChatComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ChatComponentsImpl implements ChatComponents
{
  @Override
  public Component literal(String string)
  {
    return new TextComponent(string);
  }

  @Override
  public Component translatable(String string, Object... objects)
  {
    return new TranslatableComponent(string, objects);
  }

  @Override
  public Component translatableWithStyle(ChatFormatting style, String string, Object... objects)
  {
    return new TranslatableComponent(string, objects).withStyle(style);
  }
}
