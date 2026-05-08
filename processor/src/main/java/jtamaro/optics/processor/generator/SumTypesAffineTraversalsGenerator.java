package jtamaro.optics.processor.generator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SequencedMap;
import java.util.SequencedSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

public final class SumTypesAffineTraversalsGenerator extends OpticsGenerator {

  private static final String AFFINE_TRAVERSAL_COMPONENT = """
        public static final AffineTraversal<%1$s, %1$s, %2$s, %2$s> %3$s%4$s = new AffineTraversal<>() {
          @Override
          public Either<%1$s, %2$s> getOrModify(%1$s source) {
            return source.%3$s() instanceof %2$s target
                ? Eithers.right(target)
                : Eithers.left(source);
          }
          @Override
          public %1$s set(%2$s target, %1$s source) {
            return %5$s;
          }
          @Override
          public Option<%2$s> preview(%1$s source) {
            return source.%3$s() instanceof %2$s target
                ? Options.some(target)
                : Options.none();
          }
        };
      """; // Lower indentation on purpose!

  private static final String MATCH_COMPONENT = """
        public static <X> X match%1$s(
            %2$s value,
      %3$s
        ) {
          return switch (value.%4$s()) {
      %5$s    };
        }
      """; // Weird indentations on purpose!

  private final Name targetRecordPackage;
  private final Map<TypeElement, List<TypeElement>> componentSubTypes;

  public SumTypesAffineTraversalsGenerator(
      Types types,
      TypeMirror targetRecordType,
      SequencedMap<String, TypeMirror> components,
      SequencedSet<String> allComponentNames
  ) {
    super(types, targetRecordType, components, allComponentNames);
    this.targetRecordPackage = Objects.requireNonNull(
        getEnclosingPackageName(types.asElement(targetRecordType))
    );
    this.componentSubTypes = components.sequencedValues()
        .stream()
        .map(types::asElement)
        .filter(TypeElement.class::isInstance)
        .map(TypeElement.class::cast)
        .collect(Collectors.groupingBy(
            Function.identity(),
            Collectors.flatMapping(
                this::getAccessibleSubTypes,
                Collectors.toUnmodifiableList()
            )
        ));
  }

  @Override
  public List<String> generate() {
    return components.sequencedEntrySet()
        .stream()
        .flatMap(e -> {
          final Element elem = types.asElement(e.getValue());
          if (!(elem instanceof TypeElement sourceTypeElem)) {
            // Should never happen
            return Stream.empty();
          }

          final String name = e.getKey();
          final List<TypeElement> subTypes = componentSubTypes.get(
              sourceTypeElem
          );

          final Stream<String> subTypesTraversals = subTypes.stream()
              .map(t -> traversalForSubType(name, t));

          // Match method generated iff all subtypes are accessible
          final int numCases = countConcreteSubTypes(
              sourceTypeElem
          );
          final Stream<String> matchComponent =
              subTypes.size() == numCases
                  ? Stream.of(matchForComponent(name, subTypes))
                  : Stream.empty();

          return Stream.concat(
              subTypesTraversals,
              matchComponent
          );
        })
        .toList();
  }

  private String matchForComponent(
      String targetName,
      List<TypeElement> targetTypes
  ) {
    final String params = targetTypes.stream()
        .map(t -> String.format(
            "      Function1<AffineTraversal<%1$s, %1$s, %2$s, %2$s>, X> case%3$s",
            Utils.formatType(types, targetRecordType),
            Utils.formatType(types, t),
            Utils.firstCharUppercase(t.getSimpleName())
        ))
        .collect(Collectors.joining(",\n"));
    final String cases = targetTypes.stream()
        .map(t -> String.format(
            "      case %1$s it -> case%2$s.apply(%3$s%4$s);\n",
            Utils.formatType(types, t),
            Utils.firstCharUppercase(t.getSimpleName()),
            targetName,
            t.getSimpleName()
        ))
        .collect(Collectors.joining());

    return String.format(
        MATCH_COMPONENT,
        Utils.firstCharUppercase(targetName),
        Utils.formatType(types, targetRecordType),
        params,
        targetName,
        cases
    );
  }

  private String traversalForSubType(
      String targetName,
      TypeElement targetType
  ) {
    final String sourceTypeStr = Utils.formatType(types, targetRecordType);
    final String componentTypeStr = Utils.formatType(types, targetType);
    final String overImpl = Utils.newRecordInstanceExpr(
        sourceTypeStr,
        targetName,
        allComponentNames,
        _ -> "target"
    );
    return String.format(
        AFFINE_TRAVERSAL_COMPONENT,
        sourceTypeStr,
        componentTypeStr,
        targetName,
        targetType.getSimpleName(),
        overImpl
    );
  }

  @Override
  public Set<String> usedTypes() {
    final Set<String> typesSet = new HashSet<>();
    // JTamaro types
    typesSet.add(Utils.EITHER_CLASS_NAME);
    typesSet.add(Utils.EITHERS_CLASS_NAME);
    typesSet.add(Utils.OPTION_CLASS_NAME);
    typesSet.add(Utils.OPTIONS_CLASS_NAME);
    typesSet.add(Utils.AFFINE_TRAVERSAL_CLASS_NAME);
    typesSet.add(Utils.FUNCTION_1_CLASS_NAME);
    // Record types
    typesSet.add(types.erasure(targetRecordType).toString());
    for (final TypeMirror componentType : components.values()) {
      addTypesToSet(componentType, typesSet);

      // Components of sum types
      if (types.asElement(componentType) instanceof TypeElement el) {
        for (final TypeElement stc : componentSubTypes.get(el)) {
          addTypesToSet(stc.asType(), typesSet);
        }
      }
    }
    return Collections.unmodifiableSet(typesSet);
  }

  /**
   * Find all classes that implement the given sealed interface to form the sum
   * type.
   *
   * <p>Classes must be either {@code public} or package-private in the same
   * package as the
   */
  private Stream<TypeElement> getAccessibleSubTypes(
      TypeElement interfaceElement
  ) {
    return interfaceElement.getPermittedSubclasses()
        .stream()
        .map(types::asElement)
        // Should always succeed
        .filter(TypeElement.class::isInstance)
        .map(TypeElement.class::cast)
        // If subtype is a sealed interface, we need to recurse
        .flatMap(te ->
            te.getKind() == ElementKind.INTERFACE
                && te.getModifiers().contains(Modifier.SEALED)
                ? getAccessibleSubTypes(te)
                : Stream.of(te))
        // Must be a record or class that is...
        .filter(te -> te.getKind() == ElementKind.RECORD
            || te.getKind() == ElementKind.CLASS)
        .filter(te -> {
          // ...non-abstract, ...
          if (te.getModifiers().contains(Modifier.ABSTRACT)) {
            return false;
          }
          // ...public...
          if (te.getModifiers().contains(Modifier.PUBLIC)) {
            return true;
          }
          // ...or package private in the same package as the target record
          final Name tePackage = getEnclosingPackageName(te);
          return !te.getModifiers().contains(Modifier.PRIVATE) &&
              targetRecordPackage.equals(tePackage);
        });
  }

  private int countConcreteSubTypes(TypeElement interfaceElement) {
    return interfaceElement.getPermittedSubclasses()
        .stream()
        .map(types::asElement)
        // Should always succeed
        .filter(TypeElement.class::isInstance)
        .map(TypeElement.class::cast)
        // Count recursively for sealed interface subtypes
        .mapToInt(te -> te.getKind() == ElementKind.INTERFACE
            && te.getModifiers().contains(Modifier.SEALED)
            ? countConcreteSubTypes(te)
            : 1)
        .sum();
  }

  /**
   * Find the {@link Name} of the package that contains the given element, if
   * any.
   *
   * @return The fully qualified name of the package that contains the given
   * element, or {@code null} if the given element is in no package.
   */
  private Name getEnclosingPackageName(Element element) {
    Element itr = element;
    // Navigate upwards until we find a package (or null - according to the
    // documentation of Element).
    while (itr != null && itr.getKind() != ElementKind.PACKAGE) {
      itr = itr.getEnclosingElement();
    }
    if (itr instanceof PackageElement pe) {
      return pe.getQualifiedName();
    } else {
      return null;
    }
  }
}
