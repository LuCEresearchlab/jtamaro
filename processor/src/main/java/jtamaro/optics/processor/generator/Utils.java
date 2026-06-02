package jtamaro.optics.processor.generator;

import java.util.List;
import java.util.SequencedSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Types;

final class Utils {

  public static final String EITHER_CLASS_NAME = "jtamaro.data.Either";

  public static final String EITHERS_CLASS_NAME = "jtamaro.data.Eithers";

  public static final String FUNCTION_1_CLASS_NAME = "jtamaro.data.Function1";

  public static final String FUNCTION_2_CLASS_NAME = "jtamaro.data.Function2";

  public static final String SEQUENCE_CLASS_NAME = "jtamaro.data.Sequence";

  public static final String OPTION_CLASS_NAME = "jtamaro.data.Option";

  public static final String OPTIONS_CLASS_NAME = "jtamaro.data.Options";

  public static final String AFFINE_TRAVERSAL_CLASS_NAME
      = "jtamaro.optics.AffineTraversal";

  public static final String LENS_CLASS_NAME = "jtamaro.optics.Lens";

  public static final String TRAVERSAL_CLASS_NAME = "jtamaro.optics.Traversal";

  private Utils() {
  }

  /**
   * Return appropriate string representation of a given {@link TypeMirror}.
   */
  public static String formatType(Types types, TypeMirror type) {
    return switch (type) {
      // Wrap primitives for usage with generics
      case PrimitiveType primitiveType -> formatType(
          types,
          types.boxedClass(primitiveType).asType()
      );
      // Simplify declared types
      case DeclaredType declaredType -> {
        final List<? extends TypeMirror> args = declaredType.getTypeArguments();
        final String simpleTypeName = types.asElement(declaredType)
            .getSimpleName()
            .toString();
        if (args.isEmpty()) {
          yield simpleTypeName;
        } else {
          final String argsStr = args.stream()
              .map(arg -> formatType(types, arg))
              .collect(Collectors.joining(", "));
          yield simpleTypeName + "<" + argsStr + ">";
        }
      }
      default -> type.toString();
    };
  }

  /**
   * Return appropriate string representation of a given {@link DeclaredType}.
   */
  public static String formatDeclaredType(
      Types types,
      DeclaredType type,
      List<? extends TypeMirror> args
  ) {
    final String simpleTypeName = types.asElement(type)
        .getSimpleName()
        .toString();
    if (args.isEmpty()) {
      return simpleTypeName;
    } else {
      final String argsStr = args.stream()
          .map(arg -> formatType(types, arg))
          .collect(Collectors.joining(", "));
      return simpleTypeName + "<" + argsStr + ">";
    }
  }

  /**
   * Get a string that represents the comma-separated type parameters of a given type (and their
   * lower and upper bounds).
   *
   * @return Empty string if the given type has no type parameters, or a string containing
   * comma-separated type parameters of the given class
   */
  public static String formatTypeParameters(
      Types types,
      TypeMirror typeMirror
  ) {
    return getTypeArguments(typeMirror)
        .stream()
        .filter(TypeVariable.class::isInstance)
        .map(TypeVariable.class::cast)
        .map(tv -> {
          final String lowerBound = switch (tv.getLowerBound()) {
            // Ignore NullType as a lower bound (default)
            case NullType ignored -> "";
            case TypeMirror bound -> " super " + formatType(types, bound);
          };
          final String upperBound = switch (tv.getUpperBound()) {
            // Ignore "extends Object" upper bound (default)
            case TypeMirror bound when bound.getKind().equals(TypeKind.DECLARED)
                && bound.toString().equals("java.lang.Object") -> "";
            case TypeMirror bound -> " extends " + formatType(types, bound);
          };
          return tv.asElement().getSimpleName()
              + lowerBound
              + upperBound;
        })
        .collect(Collectors.joining(", "));
  }

  /**
   * Get the {@link TypeMirror} of the first type parameter of the given {@link TypeMirror}.
   */
  public static TypeMirror getFirstTypeArgument(TypeMirror typeMirror) {
    final List<? extends TypeMirror> args = getTypeArguments(typeMirror);
    if (args.isEmpty()) {
      throw new IllegalArgumentException(
          "Provided type " + typeMirror + " has no type parameter"
      );
    } else {
      return args.getFirst();
    }
  }

  /**
   * Get the {@link TypeMirror} of the all type parameters of the given {@link TypeMirror} (if
   * any).
   */
  public static List<? extends TypeMirror> getTypeArguments(
      TypeMirror typeMirror
  ) {
    if (typeMirror instanceof DeclaredType declaredType) {
      return declaredType.getTypeArguments()
          .stream()
          .map(TypeMirror.class::cast)
          .toList();
    } else {
      return List.of();
    }
  }

  /**
   * Find the {@link Name} of the package that contains the given element, if any.
   *
   * @return The fully qualified name of the package that contains the given element, or
   * {@code null} if the given element is in no package.
   */
  public static Name getEnclosingPackageName(Element element) {
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

  /**
   * Create a new record instance expr that copies the values of all components except for one (the
   * {@code targetComponent}). That value is transformed using the {@code targetComponentExpr}
   * function that is invoked with the string of the expression for getting the original value of
   * the component.
   */
  public static String newRecordInstanceExpr(
      String recordTypeStr,
      String targetComponentName,
      SequencedSet<String> allComponentNames,
      Function<String, String> targetComponentExpr
  ) {
    final String args = allComponentNames.stream()
        .map(componentName -> {
          final String accessor = "source"
              + "."
              + componentName
              + "()";
          return componentName.equals(targetComponentName)
              ? targetComponentExpr.apply(accessor)
              : accessor;
        })
        .collect(Collectors.joining(", "));
    return "new " + recordTypeStr + "(" + args + ")";
  }

  public static String firstCharUppercase(CharSequence cs) {
    return String.valueOf(Character.toUpperCase(cs.charAt(0)))
        + cs.subSequence(1, cs.length());
  }
}
