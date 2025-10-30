package io.github.crazysmc.thrkbs.version;

import net.minecraft.network.chat.TranslatableComponent;

public class RemappedTranslatableComponent extends TranslatableComponent
{
  private final KeyRemapping remapping;

  public RemappedTranslatableComponent(KeyRemapping remapping, String key, Object... args)
  {
    super(key, args);
    this.remapping = remapping;
  }

  //FIXME
//  @Override
//  protected void decomposeTemplate(String string)
//  {
//    super.decomposeTemplate(string);
//    if (decomposedParts.isEmpty())
//      return;
//    String f3 = KeyRemapping.get(DEBUG_INFO).getTranslatedKeyMessage();
//    String key = remapping.getTranslatedKeyMessage();
//    decomposedParts.set(0, new TextComponent(debugHelpMessage(decomposedParts.get(0).getContents(), f3, key)));
//  }
}
