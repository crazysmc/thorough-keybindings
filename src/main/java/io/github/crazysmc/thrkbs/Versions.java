package io.github.crazysmc.thrkbs;

import net.fabricmc.loader.api.*;

public class Versions
{
  public static final Version V113PRE6 = semver("1.13.0-pre.6");
  public static final Version V18W11A = semver("1.13.0-alpha.18.11.a");
  public static final Version V17W43A = semver("1.13.0-alpha.17.43.a");

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
