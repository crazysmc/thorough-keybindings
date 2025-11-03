package io.github.crazysmc.thrkbs.version;

import net.minecraft.network.chat.FormattedText.ContentConsumer;
import net.minecraft.network.chat.FormattedText.StyledContentConsumer;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;

import java.util.Optional;

import static io.github.crazysmc.thrkbs.DynamicTextReplacer.debugHelpMessage;
import static io.github.crazysmc.thrkbs.HardcodedMapping.DEBUG_INFO;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

public class RemappedTranslatableContents extends TranslatableContents
{
  private final KeyRemapping remapping;

  public RemappedTranslatableContents(KeyRemapping remapping, String key)
  {
    super(key, null, NO_ARGS);
    this.remapping = remapping;
  }

  @Override
  public <T> Optional<T> visit(StyledContentConsumer<T> consumer, Style style)
  {
    return super.visit((s, string) -> consumer.accept(s, translate(string)), style);
  }

  @Override
  public <T> Optional<T> visit(ContentConsumer<T> consumer)
  {
    return super.visit(string -> consumer.accept(translate(string)));
  }

  private String translate(String text)
  {
    String f3 = REGISTRY.get(DEBUG_INFO).getTranslatedKeyText();
    String key = remapping.getTranslatedKeyText();
    return debugHelpMessage(text, f3, key);
  }
}
