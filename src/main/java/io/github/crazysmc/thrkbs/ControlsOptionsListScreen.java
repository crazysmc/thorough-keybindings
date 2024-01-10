package io.github.crazysmc.thrkbs;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ListWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.locale.LanguageManager;

import java.util.List;

public class ControlsOptionsListScreen extends Screen
{
  private static final String[] KEYS = new String[] {
      "controls.default.title",
      "key.attack|key.use",
      "key.forward|key.left",
      "key.back|key.right",
      "key.jump|key.sneak",
      "key.drop|key.inventory",
      "key.chat|key.playerlist",
      "key.pickItem|key.command",
      "controls.extra.title",
      "key.gameMenu|key.hideGui",
      "key.screenshot|key.debug",
      "key.togglePerspective|key.smoothCamera",
      "key.fullscreen",
      "controls.debug.title",
      "key.debug.reloadTextures|key.debug.reloadChunks",
      "key.debug.reloadResources|key.debug.renderDistance",
      "controls.hotbar.title",
      "key.hotbar.1|key.hotbar.2",
      "key.hotbar.3|key.hotbar.4",
      "key.hotbar.5|key.hotbar.6",
      "key.hotbar.7|key.hotbar.8",
      "key.hotbar.9",
      "controls.profilerChart.title",
      "key.profilerChart.1|key.profilerChart.2",
      "key.profilerChart.3|key.profilerChart.4",
      "key.profilerChart.5|key.profilerChart.6",
      "key.profilerChart.7|key.profilerChart.8",
      "key.profilerChart.9|key.profilerChart.back",
  };

  private final GameOptions gameOptions;
  protected Screen parent;
  private ControlsOptionsListWidget listWidget;

  public ControlsOptionsListScreen(Screen parent, GameOptions gameOptions)
  {
    this.parent = parent;
    this.gameOptions = gameOptions;
  }

  @Override
  public void init()
  {
    LanguageManager languageManager = LanguageManager.getInstance();
    @SuppressWarnings("unchecked") List<ButtonWidget> buttonWidgets = buttons;
    buttonWidgets.add(
        new OptionButtonWidget(1, this.width / 2 - 75, this.height - 38, languageManager.translate("gui.done")));
    listWidget = new ControlsOptionsListScreen.ControlsOptionsListWidget();
    listWidget.setScrollButtonIds(this.buttons, 2, 3);
  }

  @Override
  protected void buttonClicked(ButtonWidget button)
  {
    if (!button.active)
      return;
    if (button.id == 1)
      minecraft.openScreen(parent);
    else
      listWidget.buttonClicked(button);
  }

  @Override
  public void render(int mouseX, int mouseY, float tickDelta)
  {
    listWidget.render(mouseX, mouseY, tickDelta);
    LanguageManager languageManager = LanguageManager.getInstance();
    drawCenteredString(textRenderer, languageManager.translate("controls.title"), this.width / 2, 16, 0xffffff);
//    drawCenteredString(textRenderer, "(test)", this.width / 2, this.height - 56, 0x808080);
    super.render(mouseX, mouseY, tickDelta);
  }

  class ControlsOptionsListWidget extends ListWidget
  {
    public ControlsOptionsListWidget()
    {
      super(ControlsOptionsListScreen.this.minecraft, ControlsOptionsListScreen.this.width,
            ControlsOptionsListScreen.this.height, 32, ControlsOptionsListScreen.this.height - 65 + 4, 18);
    }

    @Override
    protected int size()
    {
      return KEYS.length;
    }

    @Override
    protected void entryClicked(int i, boolean bl)
    {
    }

    @Override
    protected boolean isEntrySelected(int index)
    {
      return false;
    }

    @Override
    protected void renderBackground()
    {
      ControlsOptionsListScreen.this.renderBackground();
    }

    @Override
    protected void renderEntry(int i, int j, int k, int l, BufferBuilder tesselator)
    {
      LanguageManager languageManager = LanguageManager.getInstance();
      if (KEYS[i].startsWith("key."))
      {
        String[] split = KEYS[i].split("\\|");
        String text = String.format("[%s] [%s]", languageManager.translate(split[0]),
                                    split.length > 1 ? languageManager.translate(split[1]) : "--");
        ControlsOptionsListScreen.this.drawCenteredString(textRenderer, text, ControlsOptionsListScreen.this.width / 2,
                                                          k + 1, 0xffffff);
      }
      else
        ControlsOptionsListScreen.this.drawCenteredString(textRenderer, languageManager.translate(KEYS[i]),
                                                          ControlsOptionsListScreen.this.width / 2, k + 1, 0xffffff);
    }
  }
}
