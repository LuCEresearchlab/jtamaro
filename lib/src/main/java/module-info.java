/**
 * JTamaro is a Java educational library designed for teaching problem decomposition using
 * graphics.
 *
 * <p>JTamaro is a close cousin to
 * the <a href="https://github.com/LuceResearchLab/pytamaro">PyTamaro</a> compositional graphics
 * library for Python. Unlike PyTamaro, JTamaro also includes simple purely functional data
 * structures (with types like {@code Sequence}, {@code Option}, and {@code Pair}),
 * and functionality for developing purely-functional interactive applications (with a fluent API to
 * configure and run an Interaction). JTamaro provides a minimal API, much smaller than the Java API
 * surface covered in standard Java programming courses.
 *
 * <p>The library promotes purity, immutability, parametric polymorphism, subtyping polymorphism,
 * and higher-order functions. It does this by focusing on Java records, interfaces, and generics,
 * and by a design that encourages immutability and the composition of independent components.
 */
module jtamaro {
  requires transitive java.desktop;
  requires java.logging;

  exports jtamaro.data;
  exports jtamaro.graphic;
  exports jtamaro.io;
  exports jtamaro.interaction;
  exports jtamaro.optics;
}
