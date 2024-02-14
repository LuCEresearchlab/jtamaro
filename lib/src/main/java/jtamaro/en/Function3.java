package jtamaro.en;

/**
 * Function object that takes three arguments.
 *
 * @param <A> Type of the first argument of the function.
 * @param <B> Type of the second argument of the function.
 * @param <C> Type of the third argument of the function.
 * @param <R> Type of the value returned by the function.
 */
public interface Function3<A, B, C, R> {

  R apply(A a, B b, C c);

}
