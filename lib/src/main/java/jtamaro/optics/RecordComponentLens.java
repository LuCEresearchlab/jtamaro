package jtamaro.optics;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import jtamaro.data.Function1;

/**
 * {@link Lens} for record components.
 *
 * @implNote This class uses reflection to automatically generate a lens for a record component. It
 * is not properly type-checked at compile time, as unchecked casts are used.
 * @hidden Usage in public projects is not recommended. Please use the
 * {@link Glasses} annotation on record classes and the annotation processor instead.
 */
@SuppressWarnings("unchecked")
public final class RecordComponentLens<R extends Record, C> implements Lens<R, R, C, C> {

  private final RecordComponent[] components;

  private final RecordComponent target;

  private final Constructor<R> constructor;

  public RecordComponentLens(
      Class<R> recordClass,
      String targetComponentName
  ) {
    // Guaranteed to work because R extends Record
    components = recordClass.getRecordComponents();
    target = findComponent(components, targetComponentName);
    constructor = getDefaultConstructor(recordClass, components);
    constructor.setAccessible(true);
  }

  @Override
  public C view(R source) {
    return (C) getComponentValue(target, source);
  }

  @Override
  public R over(Function1<C, C> lift, R source) {
    final int n = components.length;
    final Object[] values = new Object[n];

    for (int i = 0; i < n; i++) {
      final RecordComponent component = components[i];
      final Object value = getComponentValue(component, source);
      values[i] = component == target
          ? lift.apply((C) value)
          : value;
    }

    try {
      return constructor.newInstance(values);
    } catch (IllegalAccessException
             | InstantiationException
             | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private static RecordComponent findComponent(
      RecordComponent[] components,
      String name
  ) {
    for (RecordComponent component : components) {
      if (name.equals(component.getName())) {
        return component;
      }
    }

    throw new IllegalArgumentException("Couldn't find component " + name);
  }

  private static <R extends Record> Constructor<R> getDefaultConstructor(
      Class<R> recordClass,
      RecordComponent[] components
  ) {
    final Class<?>[] componentTypes = new Class<?>[components.length];
    for (int i = 0; i < components.length; i++) {
      componentTypes[i] = components[i].getType();
    }
    try {
      return recordClass.getDeclaredConstructor(componentTypes);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  private static Object getComponentValue(
      RecordComponent component,
      Object recordInstance
  ) {
    try {
      final Method accessor = component.getAccessor();
      accessor.setAccessible(true);
      return accessor.invoke(recordInstance);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
}
