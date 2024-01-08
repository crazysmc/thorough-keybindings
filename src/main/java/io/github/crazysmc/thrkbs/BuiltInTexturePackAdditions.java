package io.github.crazysmc.thrkbs;

import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.resource.pack.BuiltInTexturePack;
import net.ornithemc.osl.resource.loader.api.ModTexturePack;

// this class should not be necessary
public class BuiltInTexturePackAdditions extends BuiltInTexturePack implements ModTexturePack
{
  private final ModMetadata modMetadata;

  public BuiltInTexturePackAdditions(ModMetadata modMetadata)
  {
    this.modMetadata = modMetadata;
  }

  @Override
  public ModMetadata getModMetadata()
  {
    return modMetadata;
  }
}
