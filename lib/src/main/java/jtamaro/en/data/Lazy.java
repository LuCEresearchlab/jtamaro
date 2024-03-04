package jtamaro.en.data;

import jtamaro.en.Function0;
import jtamaro.en.Sequence;


/**
 * A lazy cell that might be a cons or an empty
 * (or possibly even other kinds of Sequence implementations).
 * What exactly it is is initially unclear.
 * Only after the first call to <code>first</code>, <code>rest</code>, or <code>isEmpty</code>,
 * which triggers resolution, will it be known.
 * 
 * Note that resolution happens only once;
 * the result of resolution is memoized in the <code>resolvedCell</code> field.
 * 
 * Also note that <code>hasDefiniteSize</code> is NOT lazy;
 * this method needs to be eager to fulfill its purpose.
 * 
 * Finally, note that this class is not thread-safe;
 * it is mutable and there is no synchronization.
 */
public class Lazy<T> extends Sequence<T> {

  private final Function0<Sequence<T>> resolver;
  private Sequence<T> resolvedCell;
  private final boolean hasDefiniteSize;

  
  public Lazy(Function0<Sequence<T>> resolver, boolean hasDefiniteSize) {
    this.resolver = resolver;
    this.hasDefiniteSize = hasDefiniteSize;
  }

  private boolean isUnresolved() {
    return resolvedCell == null;
  }

  private void resolveCell() {
    if (isUnresolved()) {
      resolvedCell = resolver.apply();
    }
  }

  @Override
  public T first() {
    resolveCell();
    return resolvedCell.first();
  }

  @Override
  public Sequence<T> rest() {
    resolveCell();
    return resolvedCell.rest();
  }

  @Override
  public boolean isEmpty() {
    resolveCell();
    return resolvedCell.isEmpty();
  }

  @Override
  public boolean hasDefiniteSize() {
    return hasDefiniteSize;
  }

}
