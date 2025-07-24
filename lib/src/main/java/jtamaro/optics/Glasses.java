package jtamaro.optics;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used on a {@link Record} class <code>R</code> to generate a <code>RLenses</code> class
 * with a {@link Lens} for each of its component.
 *
 * <p><b>Example</b>: for the following record class <code>Demo</code>:
 * <pre>{@code
 * public record Demo(int c0, String c1) {}
 * }</pre>
 * it would generate the following class <code>DemoLenses</code>:
 * <pre>{@code
 * public final class DemoLenses {
 *   public static final Lens<Demo, Demo, Integer, Integer> c0 = new Lens<>() {
 *     public Integer view(Demo source) {
 *       return source.c0();
 *     }
 *     public Demo over(Function1<Integer, Integer> lift, Demo source) {
 *       return new Demo(lift.apply(source.c0()), source.c1());
 *     }
 *   };
 *   public static final Lens<Demo, Demo, String, String> c1 = new Lens<>() {
 *     public String view(Demo source) {
 *       return source.c1();
 *     }
 *     public Demo over(Function1<String, String> lift, Demo source) {
 *       return new Demo(source.c0(), lift.apply(source.c1()));
 *     }
 *   };
 *
 *   private DemoLenses() {}
 * }
 * }</pre>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Glasses {

}
