package jtamaro.optics.processor.generator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.SequencedMap;
import java.util.SequencedSet;
import java.util.Set;
import java.util.stream.Stream;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

final class SequenceComponentTraversalsGenerator extends OpticsGenerator {

  private static final String METHOD_LENS_AT_INDEX = """
        private static <T> Lens<Sequence<T>, Sequence<T>, T, T> lensAtIndex(int idx) {
          return new Lens<>() {
            @Override
            public Sequence<T> over(Function1<T, T> lift, Sequence<T> source) {
              return source.zipWithIndex()
                .map(p -> p.second() == idx
                    ? lift.apply(p.first())
                    : p.first());
            }

            @Override
            public T view(Sequence<T> source) {
              return source.drop(idx).first();
            }
          };
        }
      """; // Lower indentation on purpose!

  public SequenceComponentTraversalsGenerator(
      Types types,
      TypeMirror targetRecordType,
      SequencedMap<String, TypeMirror> allComponents,
      SequencedSet<String> allComponentsNames
  ) {
    super(types, targetRecordType, allComponents, allComponentsNames);
  }

  @Override
  public List<String> generate() {
    final Stream<String> traversals = components.sequencedEntrySet()
        .stream()
        .flatMap(e -> {
          final String targetName = e.getKey();
          final TypeMirror targetElementType = getFirstTypeArgument(e.getValue());
          return Stream.of(
              lensTraversalForComponent(targetName, targetElementType),
              lensAtIndexForComponent(targetName, targetElementType)
          );
        });
    return Stream.concat(
        traversals,
        Stream.of(METHOD_LENS_AT_INDEX)
    ).toList();
  }

  @Override
  public Set<String> usedTypes() {
    final Set<String> typesSet = new HashSet<>();
    // JTamaro types
    typesSet.add(Utils.FUNCTION_1_CLASS_NAME);
    typesSet.add(Utils.FUNCTION_2_CLASS_NAME);
    typesSet.add(Utils.LENS_CLASS_NAME);
    typesSet.add(Utils.TRAVERSAL_CLASS_NAME);
    typesSet.add(Utils.SEQUENCE_CLASS_NAME);
    // Record types
    typesSet.add(types.erasure(targetRecordType).toString());
    for (final TypeMirror componentType : components.values()) {
      addTypesToSet(componentType, typesSet);
    }
    return Collections.unmodifiableSet(typesSet);
  }

  /**
   * Generate a traversal that allows to focus on a lens for a single element of a record component
   * of type Sequence.
   */
  private String lensTraversalForComponent(
      String targetName,
      TypeMirror targetElementType
  ) {
    final String sourceTypeStr = Utils.formatType(types, targetRecordType);
    final String componentElementTypeStr = Utils.formatType(types, targetElementType);
    final String overImpl = Utils.newRecordInstanceExpr(
        sourceTypeStr,
        targetName,
        allComponentNames,
        _ -> "newValue"
    );
    return String.format("""
              public static final Traversal<%1$s, %1$s, Lens<%1$s, %1$s, %2$s, %2$s>, %2$s> %3$sElements = new Traversal<>() {
                @Override
                public %1$s over(Function1<Lens<%1$s, %1$s, %2$s, %2$s>, %2$s> lift,
                                 %1$s source) {
                  final Sequence<%2$s> newValue = source.%3$s().zipWithIndex()
                      .map(p -> lift.apply(%3$s.then(lensAtIndex(p.second()))));
                  return %4$s;
                }

                @Override
                public <R> R foldMap(R neutralElement,
                                     Function2<R, R, R> reducer,
                                     Function1<Lens<%1$s, %1$s, %2$s, %2$s>, R> map,
                                     %1$s source) {
                  return source.%3$s().zipWithIndex().foldLeft(
                      neutralElement,
                      (acc, p) -> reducer.apply(
                          acc,
                          map.apply(%3$s.then(lensAtIndex(p.second())))
                      )
                  );
                }
              };
            """, // Lower indentation on purpose!
        sourceTypeStr,    // S, T
        componentElementTypeStr, // A, B
        targetName,       // 3: target component name
        overImpl          // 4: new instance in over
    );
  }

  private String lensAtIndexForComponent(
      String targetName,
      TypeMirror targetElementType
  ) {
    final String sourceTypeStr = Utils.formatType(types, targetRecordType);
    final String componentElementTypeStr = Utils.formatType(types, targetElementType);
    return String.format("""
              public static final Lens<%1$s, %1$s, %2$s, %2$s> %3$sLensAt(int idx) {
                return %3$s.then(lensAtIndex(idx));
              }
            """, // Lower indentation on purpose!
        sourceTypeStr, // S, T
        componentElementTypeStr, // A, B
        targetName // 3: target component name
    );
  }

  /**
   * Get the {@link TypeMirror} of the first type parameter of the given {@link TypeMirror}.
   */
  private static TypeMirror getFirstTypeArgument(TypeMirror typeMirror) {
    if (typeMirror instanceof DeclaredType declaredType) {
      final List<? extends TypeMirror> typeArgs = declaredType.getTypeArguments();
      if (!typeArgs.isEmpty()) {
        return typeArgs.getFirst();
      }
    }
    throw new IllegalArgumentException("Provided type " + typeMirror + " has no type parameter");
  }
}
