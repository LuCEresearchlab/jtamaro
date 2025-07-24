package jtamaro.optics.processor.generator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.SequencedMap;
import java.util.Set;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

final class ComponentLensesGenerator extends OpticsGenerator {

  public ComponentLensesGenerator(
      Types types,
      TypeMirror targetRecordType,
      SequencedMap<String, TypeMirror> allComponents
  ) {
    super(types, targetRecordType, allComponents, allComponents.sequencedKeySet());
  }

  /**
   * Generate lenses for each record component.
   */
  @Override
  public List<String> generate() {
    return components.sequencedEntrySet()
        .stream()
        .map(e -> lensForComponent(e.getKey(), e.getValue()))
        .toList();
  }

  @Override
  public Set<String> usedTypes() {
    final Set<String> typesSet = new HashSet<>();
    // Library types
    typesSet.add(Utils.FUNCTION_1_CLASS_NAME);
    typesSet.add(Utils.LENS_CLASS_NAME);
    // Record types
    typesSet.add(types.erasure(targetRecordType).toString());
    for (TypeMirror componentType : components.values()) {
      addTypesToSet(componentType, typesSet);
    }
    return Collections.unmodifiableSet(typesSet);
  }

  /**
   * Generate a lens for a given record component.
   */
  private String lensForComponent(String targetName, TypeMirror targetType) {
    final String sourceTypeStr = Utils.formatType(types, targetRecordType);
    final String componentTypeStr = Utils.formatType(types, targetType);
    final String overImpl = Utils.newRecordInstanceExpr(
        sourceTypeStr,
        targetName,
        allComponentNames,
        accessor -> "lift.apply(" + accessor + ")"
    );
    return String.format("""
              public static final Lens<%1$s, %1$s, %2$s, %2$s> %3$s = new Lens<>() {
                @Override
                public %1$s over(Function1<%2$s, %2$s> lift, %1$s source) {
                  return %4$s;
                }

                @Override
                public %2$s view(%1$s source) {
                  return source.%3$s();
                }
              };
            """, // Lower indentation on purpose!
        sourceTypeStr,    // S, T
        componentTypeStr, // A, B
        targetName,       // 3: target component name
        overImpl          // 4: new instance in over
    );
  }
}
