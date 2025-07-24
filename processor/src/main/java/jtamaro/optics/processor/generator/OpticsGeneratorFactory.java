package jtamaro.optics.processor.generator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SequencedMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

public final class OpticsGeneratorFactory {

  private OpticsGeneratorFactory() {
  }

  public static Set<OpticsGenerator> produce(
      Types types,
      TypeElement targetRecordElement
  ) {
    final TypeMirror recordTypeMirror = targetRecordElement.asType();
    final SequencedMap<String, TypeMirror> allComponents
        = getRecordComponents(targetRecordElement);

    return Stream.of(
            componentLenses(types, recordTypeMirror, allComponents),
            listTraversals(types, recordTypeMirror, allComponents)
        )
        .filter(OpticsGenerator::isProducingSomething)
        .collect(Collectors.toUnmodifiableSet());

  }

  private static OpticsGenerator componentLenses(
      Types types,
      TypeMirror recordTypeMirror,
      SequencedMap<String, TypeMirror> allComponents
  ) {
    return new ComponentLensesGenerator(
        types,
        recordTypeMirror,
        allComponents
    );
  }

  private static OpticsGenerator listTraversals(
      Types types,
      TypeMirror recordTypeMirror,
      SequencedMap<String, TypeMirror> allComponents
  ) {
    // Filter components to only those that are of type List
    final SequencedMap<String, TypeMirror> listComponents = allComponents
        .sequencedEntrySet()
        .stream()
        .filter(e -> Utils.SEQUENCE_CLASS_NAME.equals(types.erasure(e.getValue()).toString()))
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            OpticsGeneratorFactory::noDuplicateKeys,
            LinkedHashMap::new
        ));
    return new SequenceComponentTraversalsGenerator(
        types,
        recordTypeMirror,
        listComponents,
        allComponents.sequencedKeySet()
    );
  }


  /**
   * Get a mapping from name to {@link TypeMirror} of all components of a given record.
   */
  private static SequencedMap<String, TypeMirror> getRecordComponents(
      TypeElement recordElement
  ) {
    return recordElement.getRecordComponents()
        .stream()
        .collect(Collectors.toMap(
            rce -> rce.getSimpleName().toString(),
            // Note 1: Need the boxed version of the type for type parameters!
            // Note 2: we use fully qualified types because at this point we
            //         cannot reliably determine whether a "segment" of the
            //         fully qualified name corresponds to a package or an
            //         outer class.
            rce -> rce.getAccessor().getReturnType(),
            OpticsGeneratorFactory::noDuplicateKeys,
            // Order matters because TypeElement#getRecordComponents provides
            // record components in the order used by the default record
            // constructor
            LinkedHashMap::new
        ));
  }

  /**
   * Throw an {@link IllegalStateException} when handling two entries of a map with the same key.
   */
  private static <T> T noDuplicateKeys(T ignoredA, T ignoredB) {
    throw new IllegalStateException("Duplicate elements are not allowed");
  }
}
