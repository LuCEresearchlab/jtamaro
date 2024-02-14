package jtamaro.en;

/**
 * Function object that takes one argument.
 *
 * @param <A> Type of the argument of the function.
 * @param <R> Type of the value returned by the function.
 */
public interface Function1<A, R> {

  R apply(A a);
}
