package jtamaro.optics.processor.generator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.SequencedMap;
import java.util.Set;
import java.util.function.Function;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Types;

final class ComponentLensesGenerator extends OpticsGenerator {

  private static final String COMPONENT_LENS = """
      new Lens<>() {
        @Override
        public %1$s over(Function1<%2$s, %2$s> lift, %1$s source) {
          return %4$s;
        }
        @Override
        public %2$s view(%1$s source) {
          return source.%3$s();
        }
      };
      """.indent(2).substring(2);

  private static final String COMPONENT_FIELD_LENS = (
      "public static final Lens<%1$s, %1$s, %2$s, %2$s> %3$s = "
          + COMPONENT_LENS
  ).indent(2);

  private static final String GENERIC_COMPONENT_METHOD_LENS = (
      "public static final <%5$s> Lens<%1$s, %1$s, %2$s, %2$s> %3$s() {\n  return "
          + COMPONENT_LENS
          + "}"
  ).indent(2);

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
    final String recordTypeParams
        = Utils.formatTypeParameters(types, targetRecordType);
    return components.sequencedEntrySet()
        .stream()
        .map(
            recordTypeParams.isEmpty()
                ? e -> fieldForComponent(e.getKey(), e.getValue())
                : e -> methodForComponent(
                    e.getKey(),
                    e.getValue(),
                    recordTypeParams
                )
        )
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
    // Type arguments
    for (TypeMirror typeArg : Utils.getTypeArguments(targetRecordType)) {
      if (typeArg instanceof TypeVariable tv) {
        addTypesToSet(tv.getLowerBound(), typesSet);
        addTypesToSet(tv.getUpperBound(), typesSet);
      }
    }
    return Collections.unmodifiableSet(typesSet);
  }

  /**
   * Generate a lens for a given record component.
   */
  private String fieldForComponent(String targetName, TypeMirror targetType) {
    final String sourceTypeStr = Utils.formatType(types, targetRecordType);
    final String componentTypeStr = Utils.formatType(types, targetType);
    final String overImpl = Utils.newRecordInstanceExpr(
        sourceTypeStr,
        targetName,
        allComponentNames,
        accessor -> "lift.apply(" + accessor + ")"
    );
    return String.format(
        COMPONENT_FIELD_LENS,
        sourceTypeStr,    // S, T
        componentTypeStr, // A, B
        targetName,       // 3: target component name
        overImpl          // 4: new instance in over
    );
  }

  /**
   * Generate a lens method for a given record component.
   */
  private String methodForComponent(
      String targetName,
      TypeMirror targetType,
      String typeParams
  ) {
    final String sourceTypeStr = Utils.formatType(types, targetRecordType);
    final String componentTypeStr = Utils.formatType(types, targetType);
    final String overImpl = Utils.newRecordInstanceExpr(
        sourceTypeStr,
        targetName,
        allComponentNames,
        accessor -> "lift.apply(" + accessor + ")"
    );
    return String.format(
        GENERIC_COMPONENT_METHOD_LENS,
        sourceTypeStr,    // S, T
        componentTypeStr, // A, B
        targetName,       // 3: target component name
        overImpl,         // 4: new instance in over
        typeParams        // 5: type parameters
    );
  }
}
