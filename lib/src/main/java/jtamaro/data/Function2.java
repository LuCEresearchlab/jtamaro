package jtamaro.data;

/**
 * Function object that takes two arguments.
 *
 * @param <A> Type of the first argument of the function.
 * @param <B> Type of the second argument of the function.
 * @param <R> Type of the value returned by the function.
 */
public interface Function2<A, B, R> {

  R apply(A a, B b);

}
