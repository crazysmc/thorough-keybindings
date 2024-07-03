package io.github.crazysmc.thrkbs.core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MixinPluginTest
{
  private static Field mappingRegistry;
  private static MappingRegistry originalMappingRegistry;

  private final MixinPlugin mixinPlugin = new MixinPlugin();
  private final int keyCode = new Random().nextInt();

  @BeforeAll
  static void beforeAll() throws NoSuchFieldException, IllegalAccessException
  {
    mappingRegistry = ThoroughKeybindings.class.getDeclaredField("MAPPING_REGISTRY");
    Field modifiers = Field.class.getDeclaredField("modifiers");
    modifiers.setAccessible(true);
    modifiers.setInt(mappingRegistry, mappingRegistry.getModifiers() & ~Modifier.FINAL);
    originalMappingRegistry = (MappingRegistry) mappingRegistry.get(null);
  }

  @AfterAll
  static void afterAll() throws IllegalAccessException
  {
    mappingRegistry.set(null, originalMappingRegistry);
  }

  @Test
  void postApplyNopInsn() throws IllegalAccessException
  {
    ClassNode classNode = new ClassNode();
    MethodNode methodNode = new MethodNode();
    classNode.methods.add(methodNode);
    methodNode.instructions.add(new InsnNode(Opcodes.NOP));

    MappingRegistry mock = mock(MappingRegistry.class);
    mappingRegistry.set(null, mock);
    mixinPlugin.postApply("test-target", classNode, null, null);
    verify(mock, never()).registerKeyCode(anyInt());
  }

  @Test
  void postApplyMethodInsn() throws IllegalAccessException
  {
    ClassNode classNode = new ClassNode();
    MethodNode methodNode = new MethodNode();
    classNode.methods.add(methodNode);
    methodNode.instructions.add(new IntInsnNode(Opcodes.BIPUSH, keyCode));
    methodNode.instructions.add(new MethodInsnNode(0, null, "intIfEqual$test-method", "(I)I"));

    MappingRegistry mock = mock(MappingRegistry.class);
    mappingRegistry.set(null, mock);
    mixinPlugin.postApply("test-target", classNode, null, null);
    verify(mock).registerKeyCode(keyCode);
  }

  @Test
  void postApplyTableSwitchInsn() throws IllegalAccessException
  {
    ClassNode classNode = new ClassNode();
    MethodNode methodNode = new MethodNode();
    classNode.methods.add(methodNode);
    LabelNode defaultLabel = new LabelNode();
    LabelNode otherLabel = new LabelNode();
    methodNode.instructions.add(new TableSwitchInsnNode('A', 'C', defaultLabel, otherLabel, defaultLabel, otherLabel));

    MappingRegistry mock = mock(MappingRegistry.class);
    mappingRegistry.set(null, mock);
    mixinPlugin.postApply("test-target", classNode, null, null);
    verify(mock).registerKeyCode('A');
    verify(mock).registerKeyCode('C');
  }

  @Test
  void postApplyLookupSwitchInsn() throws IllegalAccessException
  {
    ClassNode classNode = new ClassNode();
    MethodNode methodNode = new MethodNode();
    classNode.methods.add(methodNode);
    methodNode.instructions.add(new LookupSwitchInsnNode(null, new int[] { 'B', 'A' }, null));

    MappingRegistry mock = mock(MappingRegistry.class);
    mappingRegistry.set(null, mock);
    mixinPlugin.postApply("test-target", classNode, null, null);
    verify(mock).registerKeyCode('B');
    verify(mock).registerKeyCode('A');
  }
}
