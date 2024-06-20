package io.github.crazysmc.thrkbs.core.api;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public interface ChatComponents
{
  Component literal(String string);

  Component translatable(String string, Object... objects);

  Component translatableWithStyle(ChatFormatting style, String string, Object... objects);
}
