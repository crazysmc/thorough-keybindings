package io.github.crazysmc.thrkbs;

public enum MappingCategory
{
  MISC("misc"),
  MODIFIER("modifier"),
  PROFILER("profiler"),
  ;

  private final String type;

  MappingCategory(String type)
  {
    this.type = type;
  }

  public String getType()
  {
    return type;
  }
}
