package io.github.crazysmc.thrkbs.version;

import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

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

  @Override
  protected void setTranslation(String string)
  {
    super.setTranslation(string);
    if (translations.isEmpty())
      return;
    String f3 = REGISTRY.get(DEBUG_INFO).getTranslatedKeyText();
    String key = rebinding.getTranslatedKeyText();
    translations.set(0, new LiteralText(debugHelpMessage(translations.get(0).getContent(), f3, key)));
  }
}
