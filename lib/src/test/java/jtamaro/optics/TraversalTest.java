package jtamaro.optics;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import jtamaro.data.Function1;
import jtamaro.data.Function2;
import org.junit.Assert;
import org.junit.Test;

public final class TraversalTest {

  private static final Traversal<Set<Integer>, Set<Integer>, Integer, Integer>
      TRAVERSAL_LIST = new Traversal<>() {
    @Override
    public <R> R foldMap(
        R neutralElement,
        Function2<R, R, R> reducer,
        Function1<Integer, R> map,
        Set<Integer> source
    ) {
      R result = neutralElement;
      for (final Integer it : source) {
        result = reducer.apply(result, map.apply(it));
      }
      return result;
    }

    @Override
    public Set<Integer> over(
        Function1<Integer, Integer> lift,
        Set<Integer> source
    ) {
      return source.stream()
          .map(lift::apply)
          .collect(Collectors.toSet());
    }
  };

  @Test
  public void fold() {
    final int n = 24;

    Assert.assertEquals(
        Integer.valueOf(n * (n + 1) / 2),
        TRAVERSAL_LIST.fold(
            0,
            Integer::sum,
            IntStream.range(0, n + 1).boxed().collect(Collectors.toSet())
        )
    );
  }

  @Test
  public void over() {
    final Set<Integer> s = Set.of(1, 2, 3, 4, 5);

    Assert.assertEquals(
        s.stream().map(x -> x * 2).collect(Collectors.toSet()),
        TRAVERSAL_LIST.over(x -> x * 2, s)
    );
  }
}
