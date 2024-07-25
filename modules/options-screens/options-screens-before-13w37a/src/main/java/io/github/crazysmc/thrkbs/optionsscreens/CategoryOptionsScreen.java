package io.github.crazysmc.thrkbs.optionsscreens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.resource.language.I18n;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryOptionsScreen extends Screen
{
  private final Screen parent;
  private final GameOptions options;
  private final CategoryRegistry categoryRegistry;
  private final List<String> categories;
  protected String title = "Controls";

  public CategoryOptionsScreen(Screen parent, GameOptions options)
  {
    this.parent = parent;
    this.options = options;
    categoryRegistry = new CategoryRegistry();
    categories = categoryRegistry.getCategories();
  }

  @Override
  public void render(int mouseX, int mouseY, float tickDelta)
  {
    renderBackground();
    drawCenteredString(textRenderer, title, width / 2, 20, 0xffffff);
    super.render(mouseX, mouseY, tickDelta);
  }

  @Override
  protected void buttonClicked(ButtonWidget button)
  {
    if (!button.active)
      return;
    if (button.id == 200)
    {
      minecraft.openScreen(parent);
      return;
    }
    String category = categories.get(button.id);
    List<KeyBinding> list = Arrays.stream(options.keyBindings)
        .filter(keyMapping -> category.equals(categoryRegistry.getCategory(keyMapping.name)))
        .collect(Collectors.toList());
    minecraft.openScreen(new ControlsOptionsSubScreen(this, options, category, list));
  }

  @Override
  public void init()
  {
    this.title = I18n.translate("controls.title");
    @SuppressWarnings("unchecked") List<ButtonWidget> buttons = this.buttons;
    int left = width / 2 - 152;
    int top = height / 6;
    for (int i = 0; i < categories.size(); i++)
      buttons.add(
          new ButtonWidget(i, left + (i & 1) * 154, top + (i >> 1) * 24, 150, 20, I18n.translate(categories.get(i))));
    buttons.add(new ButtonWidget(200, width / 2 - 100, top + 168, I18n.translate("gui.done")));
  }
}
