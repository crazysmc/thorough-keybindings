package io.github.crazysmc.thrkbs;

import net.minecraft.client.options.KeyBinding;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import net.ornithemc.osl.keybinds.api.KeyBindingEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class ThoroughKeybindings implements ClientModInitializer
{
  public static final String MOD_ID = "thorough-keybindings";
  public static final Logger LOGGER = LogManager.getLogger("ThoroughKeybindings");
  /*
key_key.attack:key.mouse.left
key_key.use:key.mouse.right
key_key.forward:key.keyboard.w
key_key.left:key.keyboard.a
key_key.back:key.keyboard.s
key_key.right:key.keyboard.d
key_key.jump:key.keyboard.space
key_key.sneak:key.keyboard.left.alt
key_key.sprint:key.keyboard.left.shift
key_key.drop:key.keyboard.x
key_key.inventory:key.keyboard.e
key_key.chat:key.keyboard.tab
key_key.playerlist:key.keyboard.k
key_key.pickItem:key.mouse.middle
key_key.command:key.keyboard.grave.accent
key_key.socialInteractions:key.keyboard.p
key_key.screenshot:key.keyboard.f2
key_key.togglePerspective:key.keyboard.q
key_key.smoothCamera:key.keyboard.f4
key_key.fullscreen:key.keyboard.f11
key_key.spectatorOutlines:key.keyboard.unknown
key_key.swapOffhand:key.keyboard.f
key_key.saveToolbarActivator:key.keyboard.minus
key_key.loadToolbarActivator:key.keyboard.o
key_key.advancements:key.keyboard.l
key_key.hotbar.1:key.keyboard.1
key_key.hotbar.2:key.keyboard.2
key_key.hotbar.3:key.keyboard.3
key_key.hotbar.4:key.keyboard.4
key_key.hotbar.5:key.keyboard.5
key_key.hotbar.6:key.keyboard.r
key_key.hotbar.7:key.keyboard.t
key_key.hotbar.8:key.keyboard.g
key_key.hotbar.9:key.keyboard.v
   */
  public static KeyBinding hideGuiKey = new KeyBinding("key.hideGui", Keyboard.KEY_F1);
  public static KeyBinding screenshotKey = new KeyBinding("key.screenshot", Keyboard.KEY_F2);
  public static KeyBinding perspectiveKey = new KeyBinding("key.togglePerspective", Keyboard.KEY_F5);

  @Override
  public void initClient()
  {
    KeyBindingEvents.REGISTER_KEYBINDS.register(registry ->
    {
      registry.register(hideGuiKey);
      registry.register(screenshotKey);
      registry.register(perspectiveKey);
    });
  }
}
