package io.github.crazysmc.thrkbs.chatcomponents;

import io.github.crazysmc.thrkbs.core.api.ChatComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class ChatComponentsImpl implements ChatComponents
{
  @Override
  public Component literal(String string)
  {
    return new TextComponent(string);
  }
}
