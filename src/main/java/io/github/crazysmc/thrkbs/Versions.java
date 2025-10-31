package io.github.crazysmc.thrkbs;

import net.fabricmc.loader.api.*;

public class Versions
{
  public static final Version V20W06A = semver("1.16-alpha.20.06.a");
  public static final Version V20W20A = semver("1.16-alpha.20.20.a");

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
