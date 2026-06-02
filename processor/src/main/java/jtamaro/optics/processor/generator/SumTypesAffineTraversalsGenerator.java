package jtamaro.optics.processor.generator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.SequencedMap;
import java.util.SequencedSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public final class SumTypesAffineTraversalsGenerator extends OpticsGenerator {

  private static final String COMPONENT_AFFINE_TRAVERSAL = """
      new AffineTraversal<>() {
        @Override
        public Either<%1$s, %2$s> getOrModify(%1$s source) {
          return source.%3$s() instanceof %2$s $%3$s
              ? Eithers.right($%3$s)
              : Eithers.left(source);
        }
        @Override
        public %1$s set(%2$s %3$s, %1$s source) {
          return %5$s;
        }
        @Override
        public Option<%2$s> preview(%1$s source) {
          return source.%3$s() instanceof %2$s $%3$s
              ? Options.some($%3$s)
              : Options.none();
        }
      };
      """.indent(2).substring(2);

  private static final String COMPONENT_METHOD_TRAVERSAL = (
      "public static final%6$s AffineTraversal<%1$s, %1$s, %2$s, %2$s> %3$s%4$s() {\n  return "
          + COMPONENT_AFFINE_TRAVERSAL
          + "}"
  ).indent(2);


  private static final String MATCH_COMPONENT = """
        public static <%1$s> X match%2$s(
            %3$s value,
      %4$s
        ) {
          return switch (value.%5$s()) {
      %6$s    };
        }
      """; // Weird indentations on purpose!

  private final Name targetRecordPackage;

  private final Map<String, List<SubTypeWithArgs>> subTypesWithArgs;

  public SumTypesAffineTraversalsGenerator(
      Elements elements,
      Types types,
      TypeMirror targetRecordType,
      SequencedMap<String, TypeMirror> components,
      SequencedSet<String> allComponentNames
  ) {
    super(types, targetRecordType, components, allComponentNames);
    targetRecordPackage = elements.getPackageOf(
        types.asElement(targetRecordType)
    ).getQualifiedName();
    subTypesWithArgs = getComponentSubTypesWithArgs();
  }

  @Override
  public List<String> generate() {
    final String recordTypeParams
        = Utils.formatTypeParameters(types, targetRecordType);
    return components.sequencedKeySet()
        .stream()
        .filter(subTypesWithArgs::containsKey)
        .map(name -> Map.entry(name, subTypesWithArgs.get(name)))
        .flatMap(e -> {
              final Stream<String> traversals = e.getValue()
                  .stream()
                  .map(subTypeWithArgs -> methodForSubType(
                      e.getKey(),
                      subTypeWithArgs.impl,
                      subTypeWithArgs.args,
                      recordTypeParams
                  ));

              final TypeElement componentTypeElem
                  = (TypeElement) types.asElement(components.get(e.getKey()));
              if (allSubTypesReachable(componentTypeElem)) {
                return Stream.concat(
                    traversals,
                    Stream.of(matchForComponent(
                        e.getKey(),
                        recordTypeParams,
                        e.getValue()
                    ))
                );
              } else {
                return traversals;
              }
            }
        )
        .toList();
  }

  private String methodForSubType(
      String targetName,
      DeclaredType targetType,
      List<? extends TypeMirror> args,
      String recordParams
  ) {
    final String sourceTypeStr = Utils.formatType(types, targetRecordType);
    final String componentTypeStr = Utils.formatDeclaredType(
        types,
        targetType,
        args
    );
    final String overImpl = Utils.newRecordInstanceExpr(
        sourceTypeStr,
        targetName,
        allComponentNames,
        _ -> targetName
    );
    final String methodTypeParams = typeParamsForOpticMethod(
        recordParams,
        args
    );
    return String.format(
        COMPONENT_METHOD_TRAVERSAL,
        sourceTypeStr,
        componentTypeStr,
        targetName,
        targetType.asElement().getSimpleName(),
        overImpl,
        methodTypeParams
    );
  }

  private String matchForComponent(
      String targetName,
      String recordTypeParams,
      List<SubTypeWithArgs> subTypeWithArgs
  ) {
    final String matchTypeParams = recordTypeParams.isEmpty()
        ? "X"
        : "X, " + typeParamsForOpticMethod(
            recordTypeParams,
            subTypeWithArgs.stream()
                .flatMap(st -> st.args().stream())
                .filter(t -> t instanceof TypeVariable)
                .toList()
        );
    final String params = subTypeWithArgs.stream()
        .map(t -> String.format(
            "      Function1<AffineTraversal<%1$s, %1$s, %2$s, %2$s>, X> case%3$s",
            Utils.formatType(types, targetRecordType),
            Utils.formatDeclaredType(types, t.impl, t.args),
            Utils.firstCharUppercase(types.asElement(t.impl).getSimpleName())
        ))
        .collect(Collectors.joining(",\n"));
    final String cases = subTypeWithArgs.stream()
        .map(t -> String.format(
            "      case %1$s it -> case%2$s.apply(%3$s%4$s());\n",
            Utils.formatDeclaredType(types, t.impl, t.args),
            Utils.firstCharUppercase(types.asElement(t.impl).getSimpleName()),
            targetName,
            types.asElement(t.impl).getSimpleName()
        ))
        .collect(Collectors.joining());

    return String.format(
        MATCH_COMPONENT,
        matchTypeParams,
        Utils.firstCharUppercase(targetName),
        Utils.formatType(types, targetRecordType),
        params,
        targetName,
        cases
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
    }
    for (final String name : subTypesWithArgs.keySet()) {
      for (final SubTypeWithArgs st : subTypesWithArgs.get(name)) {
        addTypesToSet(st.impl, typesSet);
        for (final TypeMirror arg : st.args) {
          addTypesToSet(arg, typesSet);
        }
      }
    }
    return Collections.unmodifiableSet(typesSet);
  }

  private String typeParamsForOpticMethod(
      String recordParams,
      List<? extends TypeMirror> args
  ) {
    final StringBuilder result = new StringBuilder();
    result.append(
        args.stream()
            .filter(arg -> arg instanceof TypeVariable)
            .map(Object::toString)
            .collect(Collectors.joining(", "))
    );
    if (!result.isEmpty()) {
      result.append(", ");
    }
    result.append(recordParams);
    if (!result.isEmpty()) {
      result.insert(0, " <").append(">");
    }
    return result.toString();
  }

  private Map<String, List<SubTypeWithArgs>> getComponentSubTypesWithArgs() {
    return components.sequencedEntrySet()
        .stream()
        .filter(e -> e.getValue() instanceof DeclaredType)
        .collect(Collectors.toUnmodifiableMap(
            Map.Entry::getKey,
            e -> subTypesOfComponentType((DeclaredType) e.getValue()),
            (a, b) -> Stream.concat(
                a.stream(),
                b.stream()
            ).toList()
        ));
  }

  private List<SubTypeWithArgs> subTypesOfComponentType(
      DeclaredType componentType
  ) {
    final Element elem = types.asElement(componentType);
    if (!(elem instanceof TypeElement te)) {
      return List.of();
    }

    return te.getPermittedSubclasses()
        .stream()
        .filter(DeclaredType.class::isInstance)
        .map(DeclaredType.class::cast)
        .filter(dt -> {
          // Must be a record or class that is...
          final TypeElement subTypeElem = (TypeElement) types.asElement(dt);
          final Set<Modifier> modifiers = subTypeElem.getModifiers();
          // ...not private, ...
          if (modifiers.contains(Modifier.PRIVATE)) {
            return false;
          }
          // ...or public...
          if (modifiers.contains(Modifier.PUBLIC)) {
            return true;
          }
          // ...or package private in the same package as the target record
          return targetRecordPackage.equals(
              Utils.getEnclosingPackageName(subTypeElem)
          );
        })
        .flatMap(subType -> allConcreteSubTypesOf(componentType, subType))
        .toList();
  }

  /**
   * Recursively find all concrete subtypes of {@code subType}, a known subtype of
   * {@code superType}.
   *
   * <p>By <i>concrete</i>, here we mean non-abstract (thus non-sealed) types with their type
   * parameters substituted with appropriate type arguments according to the type arguments of the
   * super type.
   */
  private Stream<SubTypeWithArgs> allConcreteSubTypesOf(
      DeclaredType superType,
      DeclaredType subType
  ) {
    final Element subElem = types.asElement(subType);
    if (subElem.getModifiers().contains(Modifier.SEALED)) {
      // Recurse on sealed types.
      // Can safely cast because subtype is always a DeclaredType rather
      // than the more generic TypeMirror + we know it has the sealed modifer
      // which can only be applied to classes and interfaces.
      return ((TypeElement) subElem).getPermittedSubclasses()
          .stream()
          .filter(DeclaredType.class::isInstance)
          .map(DeclaredType.class::cast)
          .flatMap(sc -> allConcreteSubTypesOf(subType, sc));
    }

    // interface In<T>
    // record On<A> : In<Integer>
    // superType = In<Integer>
    // subType   = On<A>
    // superImpl = In<T>
    // We want to substitute T with Integer, but keep A as TypeVariable
    final List<? extends TypeMirror> superArgs = superType.getTypeArguments();
    final List<? extends TypeMirror> subParams = subType.getTypeArguments();
    return types.directSupertypes(subType)
        .stream()
        .filter(st -> types.isAssignable(superType, types.erasure(st)))
        .filter(DeclaredType.class::isInstance)
        .map(DeclaredType.class::cast)
        .map(superImpl -> new SubTypeWithArgs(
            subType,
            superImpl.getTypeArguments()
                .stream()
                .map(superParam -> {
                  final int indexInSuperTypeArgs =
                      superParam instanceof TypeVariable tv
                          // It seems we can use equality (and thus indexOf)
                          // to figure out whether the type variables match
                          ? subParams.indexOf(tv)
                          : -1;
                  return indexInSuperTypeArgs < 0
                      ? superParam
                      : superArgs.get(indexInSuperTypeArgs);
                })
                .toList()
        ));
  }

  /**
   * Find all classes that implement the given sealed interface to form the sum type.
   *
   * <p>Classes must be either {@code public} or package-private in the same
   * package as the
   */
  private boolean allSubTypesReachable(TypeElement type) {
    return type.getPermittedSubclasses()
        .stream()
        .map(types::asElement)
        // Should always succeed
        .filter(TypeElement.class::isInstance)
        .map(TypeElement.class::cast)
        .allMatch(te -> {
          final Set<Modifier> modifiers = te.getModifiers();
          if (modifiers.contains(Modifier.SEALED)) {
            return allSubTypesReachable(te);
          }
          if (modifiers.contains(Modifier.PRIVATE)) {
            return false;
          }
          if (modifiers.contains(Modifier.PUBLIC)) {
            return true;
          }
          return targetRecordPackage.equals(Utils.getEnclosingPackageName(te));
        });
  }

  record SubTypeWithArgs(
      DeclaredType impl,
      List<? extends TypeMirror> args
  ) {

  }
}
