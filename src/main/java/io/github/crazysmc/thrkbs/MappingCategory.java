package io.github.crazysmc.thrkbs;

public enum MappingCategory
{
  MISC("misc"),
  DEBUG("debug"),
  MODIFIER("modifier"),
  PROFILER("profiler"),
  ;

  private final String type;
  public Object object;

  MappingCategory(String type)
  {
    this.type = type;
  }

  public String getType()
  {
    return type;
  }

  public String getId()
  {
    return String.format("key.categories.%s", type);
  }
}
