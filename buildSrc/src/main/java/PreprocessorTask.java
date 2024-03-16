import com.vdurmont.semver4j.Requirement;
import com.vdurmont.semver4j.Semver;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Sync;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PreprocessorTask extends Sync
{
  public static final Pattern PATTERN = Pattern.compile("^\\s*(//|#)\\$");

  public PreprocessorTask()
  {
    boolean[] inc = { false };
    eachFile(file -> {
      Semver mc = getMinecraftSemver.get();
      inc[0] = true;
      file.filter(line -> {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.find())
        {
          line = line.substring(matcher.end());
          if (line.startsWith("if"))
          {
            inc[0] = Requirement.buildNPM(line.substring(2)).isSatisfiedBy(mc);
            return null;
          }
        }
        return inc[0] ? line : null;
      });
      if (file.getSize() == 0)
        file.exclude();
    });
  }

  @Input
  public abstract Property<String> getMinecraft();

  private Supplier<Semver> getMinecraftSemver = () -> {
    Semver mc = new Semver(getMinecraft().get(), Semver.SemverType.LOOSE);
    getMinecraftSemver = () -> mc;
    return mc;
  };
}
