package jtamaro.optics.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import jtamaro.optics.processor.generator.OpticsGenerator;
import jtamaro.optics.processor.generator.OpticsGeneratorFactory;

/**
 * Annotation processor that produces lenses for each component of an annotated record class.
 */
@SupportedAnnotationTypes("jtamaro.optics.Glasses")
@SupportedSourceVersion(SourceVersion.RELEASE_25)
public final class GlassesProcessor extends AbstractProcessor {

  /**
   * Default constructor.
   */
  public GlassesProcessor() {
    super();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    return annotations.stream()
        // Find all elements annotated with our annotation
        .flatMap(annotation -> roundEnv.getElementsAnnotatedWith(annotation).stream())
        // Filter for non-private records
        .filter(this::isNonPrivateRecord)
        // Safely cast to TypeElement (should always succeed at this point)
        .filter(TypeElement.class::isInstance)
        .map(TypeElement.class::cast)
        // Process each record class
        .allMatch(this::processRecord);
  }

  private boolean processRecord(TypeElement recordElement) {
    final Messager messager = processingEnv.getMessager();
    final Filer filer = processingEnv.getFiler();
    final Elements elements = processingEnv.getElementUtils();
    final Types types = processingEnv.getTypeUtils();

    final String packageName = elements.getPackageOf(recordElement).toString();
    final String simpleName = getLensesSimpleName(recordElement, packageName);

    final OpticsClassSpecs specs = generateOpticsSpecs(
        elements,
        types,
        recordElement,
        simpleName
    );

    try {
      final JavaFileObject genFile = filer.createSourceFile(
          packageName + "." + simpleName,
          recordElement
      );
      try (Writer writer = genFile.openWriter()) {
        writeFile(writer, specs);
      }
      return true;
    } catch (IOException e) {
      messager.printError(e.getMessage(), recordElement);
      return false;
    }
  }

  private OpticsClassSpecs generateOpticsSpecs(
      Elements elements,
      Types types,
      TypeElement targetRecordElement,
      String className
  ) {
    final Set<OpticsGenerator> generators = OpticsGeneratorFactory.produce(
        types,
        targetRecordElement
    );

    final List<String> fieldsAndMethods = generators.stream()
        .flatMap(generator -> generator.generate().stream())
        .toList();

    final List<String> typesToImport = generators.stream()
        .flatMap(generator -> generator.usedTypes().stream())
        .sorted()
        .toList();

    // "protected" is semantically equivalent to package-private (no visibility
    // modifier) for record classes because they cannot be extended: we only
    // care about whether the record is public or not and we want our *Lenses
    // class to match that
    final boolean isPublic = targetRecordElement.getModifiers()
        .contains(Modifier.PUBLIC);

    // Package of record
    final PackageElement recPkg = elements.getPackageOf(targetRecordElement);

    return new OpticsClassSpecs(
        recPkg,
        className,
        isPublic,
        typesToImport,
        fieldsAndMethods
    );
  }

  private void writeFile(
      Writer writer,
      OpticsClassSpecs specs
  ) throws IOException {
    // Package statement
    writer.append("package ")
        .append(specs.pkg.toString())
        .append(";\n\n");

    // Imports
    for (final String type : specs.typesToImport) {
      writer.append("import ")
          .append(type)
          .append(";\n");
    }
    writer.append('\n');

    // Class declaration statement
    if (specs.isPublic) {
      writer.append("public ");
    }
    writer.append("final class ")
        .append(specs.className)
        .append(" {\n\n");

    // Private default constructor
    writer.append("  private ")
        .append(specs.className)
        .append("() {\n  }\n");

    // Fields and methods
    for (final String it : specs.fieldsAndMethods) {
      writer.append('\n')
          .append(it);
    }

    writer.write("}\n");
  }

  private boolean isNonPrivateRecord(Element el) {
    if (el.getKind() != ElementKind.RECORD) {
      printSkipWarning("non-record class", el);
      return false;
    } else if (el.getModifiers().contains(Modifier.PRIVATE)) {
      printSkipWarning("private record", el);
      return false;
    } else {
      return true;
    }
  }

  private String getLensesSimpleName(TypeElement element, String packageName) {
    final String classNameWithEnclosing = element.getQualifiedName()
        .toString()
        // +1 for the "." after the package name
        .substring(packageName.length() + 1)
        // Replace the "." of inner classes with "$"
        .replace(".", "$");
    return classNameWithEnclosing + "Lenses";
  }

  private void printSkipWarning(String reason, Element el) {
    final Messager messager = processingEnv.getMessager();
    messager.printWarning("Skipping Lenses generation for "
        + reason
        + " "
        + el.getSimpleName());
  }

  private record OpticsClassSpecs(
      PackageElement pkg,
      String className,
      boolean isPublic,
      List<String> typesToImport,
      List<String> fieldsAndMethods
  ) {

  }
}
