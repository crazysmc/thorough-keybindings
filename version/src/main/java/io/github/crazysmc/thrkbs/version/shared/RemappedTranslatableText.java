package io.github.crazysmc.thrkbs.version.shared;

import io.github.crazysmc.thrkbs.version.KeyRebinding;
import net.minecraft.text.*;

import java.lang.reflect.Field;
import java.util.List;

import static io.github.crazysmc.thrkbs.DynamicTextReplacer.debugHelpMessage;
import static io.github.crazysmc.thrkbs.HardcodedMapping.DEBUG_INFO;
import static io.github.crazysmc.thrkbs.version.KeyRebinding.REGISTRY;

public class RemappedTranslatableText extends TranslatableText
{
  private final KeyRebinding rebinding;

  public RemappedTranslatableText(KeyRebinding rebinding, String key)
  {
    super(key);
    this.rebinding = rebinding;
  }

  private List<Text> getTranslations()
  {
    try
    {
      for (Field field : TranslatableText.class.getDeclaredFields())
        if (List.class.isAssignableFrom(field.getType()))
        {
          field.setAccessible(true);
          @SuppressWarnings("unchecked")
          List<Text> translations = (List<Text>) field.get(this);
          return translations;
        }
    }
    catch (IllegalAccessException e)
    {
      throw new IllegalStateException("could not access translations field", e);
    }
    throw new IllegalStateException("could not find translations field");
  }

  @Override
  protected void setTranslation(String string)
  {
    super.setTranslation(string);
    List<Text> translations = getTranslations();
    if (translations.isEmpty())
      return;
    String f3 = REGISTRY.get(DEBUG_INFO).getTranslatedKeyText();
    String key = rebinding.getTranslatedKeyText();
    translations.set(0, new LiteralText(debugHelpMessage(translations.get(0).getContent(), f3, key)));
  }
}
