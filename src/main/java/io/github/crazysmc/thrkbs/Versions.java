package io.github.crazysmc.thrkbs;

import net.fabricmc.loader.api.*;

public abstract class Versions
{
  public static final String S263S4 = "26.3-alpha.4";
  public static final Version V263S4 = semver(S263S4);
  public static final String S263S6 = "26.3-alpha.6";
  public static final Version V263S6 = semver(S263S6);

  private static Version semver(String version)
  {
    try
    {
      return SemanticVersion.parse(version);
    }
    catch (VersionParsingException e)
    {
      throw new IllegalStateException(e);
    }
  }
}
