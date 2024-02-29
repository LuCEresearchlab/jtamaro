package jtamaro.en;

/**
 * Function object that takes four arguments.
 *
 * @param <A> Type of the first argument of the function.
 * @param <B> Type of the second argument of the function.
 * @param <C> Type of the third argument of the function.
 * @param <D> Type of the fourth argument of the function.
 * @param <R> Type of the value returned by the function.
 */
public interface Function4<A, B, C, D, R> {

  R apply(A a, B b, C c, D d);

}
