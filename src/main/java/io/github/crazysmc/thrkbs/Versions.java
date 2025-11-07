package io.github.crazysmc.thrkbs;

import net.fabricmc.loader.api.*;

public class Versions
{
  public static final Version V20W20A = semver("1.16-alpha.20.20.a");
  public static final Version V116PRE1 = semver("1.16-rc.1");
  public static final Version V21W11A = semver("1.17-alpha.21.11.a");
  public static final Version V22W12A = semver("1.19-alpha.22.12.a");
  public static final Version V11904PRE3 = semver("1.19.4-pre.3");
  public static final Version V23W33A = semver("1.20.2-alpha.23.33.a");
  public static final Version V25W15A = semver("1.21.6-alpha.25.15.a");
  public static final Version V25W31A = semver("1.21.9-alpha.25.31.a");
  public static final Version V12109PRE4 = semver("1.21.9-beta.4");
  public static final Version V25W41A = semver("1.21.11-alpha.25.41.a");

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
