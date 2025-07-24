package jtamaro.optics.processor.generator;

import java.util.List;
import java.util.SequencedSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

final class Utils {

  public static final String FUNCTION_1_CLASS_NAME = "jtamaro.data.Function1";

  public static final String FUNCTION_2_CLASS_NAME = "jtamaro.data.Function2";

  public static final String SEQUENCE_CLASS_NAME = "jtamaro.data.Sequence";

  public static final String LENS_CLASS_NAME = "jtamaro.optics.Lens";

  public static final String TRAVERSAL_CLASS_NAME = "jtamaro.optics.Traversal";

  private Utils() {
  }

  /**
   * Return appropriate string representation of a given {@link TypeMirror}.
   */
  public static String formatType(
      Types types,
      TypeMirror type
  ) {
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
   * Create a new record instance expr that copies the values of all components except for one (the
   * {@code targetComponent}), which is transformed using the {@code targetComponentExpr} function
   * that is invoked with the string of the expression for getting the original value of the
   * component.
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
}
