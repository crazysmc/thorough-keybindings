package io.github.crazysmc.thrkbs;

import net.ornithemc.osl.config.api.ConfigScope;
import net.ornithemc.osl.config.api.LoadingPhase;
import net.ornithemc.osl.config.api.config.BaseConfig;
import net.ornithemc.osl.config.api.config.option.BooleanOption;
import net.ornithemc.osl.config.api.serdes.FileSerializerType;
import net.ornithemc.osl.config.api.serdes.SerializerTypes;

public class ModConfig extends BaseConfig
{
  public final BooleanOption testOption = new BooleanOption("Test", "Description", false);

  @Override
  public String getNamespace()
  {
    return ThoroughKeybindings.MOD_ID;
  }

  @Override
  public String getName()
  {
    return "Thorough Keybindings Config";
  }

  @Override
  public String getSaveName()
  {
    return ThoroughKeybindings.MOD_ID;
  }

  @Override
  public ConfigScope getScope()
  {
    return ConfigScope.GLOBAL;
  }

  @Override
  public LoadingPhase getLoadingPhase()
  {
    return LoadingPhase.START;
  }

  @Override
  public FileSerializerType<?> getType()
  {
    return SerializerTypes.JSON;
  }

  @Override
  public int getVersion()
  {
    return 0;
  }

  @Override
  public void init()
  {
    registerOptions("Keybindings", testOption);
  }
}
