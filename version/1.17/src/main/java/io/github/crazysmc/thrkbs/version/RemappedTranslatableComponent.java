package io.github.crazysmc.thrkbs.version;

import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Optional;

import static io.github.crazysmc.thrkbs.DynamicTextReplacer.debugHelpMessage;
import static io.github.crazysmc.thrkbs.HardcodedMapping.DEBUG_INFO;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

public class RemappedTranslatableComponent extends TranslatableComponent
{
  private final KeyRemapping remapping;

  public RemappedTranslatableComponent(KeyRemapping remapping, String key)
  {
    super(key);
    this.remapping = remapping;
  }

  @Override
  public <T> Optional<T> visitSelf(StyledContentConsumer<T> consumer, Style style)
  {
    return super.visitSelf((s, string) -> consumer.accept(s, translate(string)), style);
  }

  @Override
  public <T> Optional<T> visitSelf(ContentConsumer<T> consumer)
  {
    return super.visitSelf(string -> consumer.accept(translate(string)));
  }

  private String translate(String text)
  {
    String f3 = REGISTRY.get(DEBUG_INFO).getTranslatedKeyText();
    String key = remapping.getTranslatedKeyText();
    return debugHelpMessage(text, f3, key);
  }
}
