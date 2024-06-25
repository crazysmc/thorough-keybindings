package io.github.crazysmc.thrkbs.core.api;

import net.minecraft.text.Formatting;
import net.minecraft.text.Text;

public interface ChatComponents
{
  Text literal(String string);

  Text translatable(String string, Object... objects);

  Text translatableWithStyle(Formatting style, String string, Object... objects);
}
