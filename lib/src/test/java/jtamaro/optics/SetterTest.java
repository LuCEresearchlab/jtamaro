package jtamaro.optics;

import jtamaro.data.Function1;
import org.junit.Assert;
import org.junit.Test;

public final class SetterTest {

  private static final Setter<Rec, Rec, Character, Character> SETTER_REC_A =
      (lift, source) -> new Rec(lift.apply(source.a), source.b);

  private record Rec(Character a, Boolean b) {

  }

  @Test
  public void putPut() {
    final Rec s = new Rec('c', false);
    final char value = 'd';

    Assert.assertEquals(
        "Setting twice is the same as setting once",
        SETTER_REC_A.set(value, s),
        SETTER_REC_A.set(value, SETTER_REC_A.set(value, s))
    );
  }

  @Test
  public void functorialityIdentity() {
    final Rec s = new Rec('!', true);

    Assert.assertEquals(
        "Setters must preserve identities",
        s,
        SETTER_REC_A.over(Function1.identity(), s)
    );
  }

  @Test
  public void functorialityComposition() {
    final Rec s = new Rec('~', true);
    final Function1<Character, Character> f = c -> (char) (c + 1);
    final Function1<Character, Character> g = c -> (char) (c - 2);

    Assert.assertEquals(
        s,
        SETTER_REC_A.over(Function1.identity(), s)
    );
    Assert.assertEquals(
        "Setters must preserve composition",
        SETTER_REC_A.over(c -> g.apply(f.apply(c)), s),
        SETTER_REC_A.over(f, SETTER_REC_A.over(g, s))
    );
  }

  @Test
  public void compose() {
    final String s = "!!!!";
    final Setter<String, String, Integer, Integer> setter1 =
        (lift, source) -> source.substring(0, 1)
            .repeat(lift.apply(source.length()));
    final Setter<Integer, Integer, Double, Double> setter2 =
        (lift, source) -> lift.apply(source.doubleValue()).intValue();

    Assert.assertEquals(
        "!!",
        setter1.then(setter2).over(Math::sqrt, s)
    );
  }
}
