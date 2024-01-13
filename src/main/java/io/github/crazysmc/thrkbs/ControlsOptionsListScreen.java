package io.github.crazysmc.thrkbs;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.locale.LanguageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControlsOptionsListScreen extends Screen
{
  private final Screen parent;
  private final GameOptions options;
  private final List<KeyBinding> defaultKeys;
  private final List<KeyBinding> extraKeys = new ArrayList<>();
  private final List<KeyBinding> debugKeys = new ArrayList<>();
  private final List<KeyBinding> hotbarKeys = new ArrayList<>();
  private final List<KeyBinding> profilerKeys = new ArrayList<>();
  private final List<KeyBinding> modKeys = new ArrayList<>();
  protected String title = "Controls";

  public ControlsOptionsListScreen(Screen parent, GameOptions options)
  {
    this.parent = parent;
    this.options = options;
    defaultKeys = Arrays.asList(options.keyBindings).subList(0, 14);
    Arrays.stream(options.keyBindings).skip(14).forEach(keyBinding -> {
      if (keyBinding.name.startsWith("key.extra."))
        extraKeys.add(keyBinding);
      else if (keyBinding.name.startsWith("key.debug."))
        debugKeys.add(keyBinding);
      else if (keyBinding.name.startsWith("key.hotbar."))
        hotbarKeys.add(keyBinding);
      else if (keyBinding.name.startsWith("key.profilerChart."))
        profilerKeys.add(keyBinding);
      else if (!keyBinding.name.startsWith("key.hidden."))
        modKeys.add(keyBinding);
    });
  }

  @Override
  public void init()
  {
    LanguageManager lm = LanguageManager.getInstance();
    this.title = lm.translate("controls.title");
    @SuppressWarnings("unchecked") List<ButtonWidget> buttons = this.buttons;
    int left = width / 2 - 152;
    int right = width / 2 + 2;
    int top = height / 6;
    buttons.add(new ButtonWidget(100, left, top, 150, 20, lm.translate("controls.default.title")));
    buttons.add(new ButtonWidget(101, left, top + 24, 150, 20, lm.translate("controls.extra.title")));
    buttons.add(new ButtonWidget(102, left, top + 48, 150, 20, lm.translate("controls.debug.title")));
    buttons.add(new ButtonWidget(110, right, top, 150, 20, lm.translate("controls.hotbar.title")));
    buttons.add(new ButtonWidget(111, right, top + 24, 150, 20, lm.translate("controls.profilerChart.title")));
    if (!modKeys.isEmpty())
      buttons.add(new ButtonWidget(112, right, top + 48, 150, 20, lm.translate("controls.mod.title")));
    buttons.add(new ButtonWidget(200, width / 2 - 100, top + 168, lm.translate("gui.done")));
  }

  @Override
  protected void buttonClicked(ButtonWidget button)
  {
    if (!button.active)
      return;
    Screen screen;
    switch (button.id)
    {
      case 100:
        screen = new ControlsOptionsSubScreen(this, options, "controls.default.title", defaultKeys);
        break;
      case 101:
        screen = new ControlsOptionsSubScreen(this, options, "controls.extra.title", extraKeys);
        break;
      case 102:
        screen = new ControlsOptionsSubScreen(this, options, "controls.debug.title", debugKeys).setLongNames(true);
        break;
      case 110:
        screen = new ControlsOptionsSubScreen(this, options, "controls.hotbar.title", hotbarKeys);
        break;
      case 111:
        screen = new ControlsOptionsSubScreen(this, options, "controls.profilerChart.title", profilerKeys);
        break;
      case 112:
        screen = new ControlsOptionsSubScreen(this, options, "controls.mod.title", modKeys);
        break;
      case 200:
        screen = parent;
        break;
      default:
        return;
    }
    minecraft.openScreen(screen);
  }

  @Override
  public void render(int mouseX, int mouseY, float tickDelta)
  {
    renderBackground();
    drawCenteredString(textRenderer, title, width / 2, 20, 0xffffff);
    super.render(mouseX, mouseY, tickDelta);
  }
}
