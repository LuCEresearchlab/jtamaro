package jtamaro.optics.processor;

import jtamaro.data.Options;
import jtamaro.optics.Glasses;
import org.junit.Assert;
import org.junit.Test;

public final class SumTypeTest {

  @Test
  public void justTest() {
    final Container c = new Container(new SomeString("1"));

    Assert.assertEquals(
        Options.some("1"),
        SumTypeTest$ContainerOptics.sthSomeString
            .then(SumTypeTest$SomeStringOptics.str)
            .preview(c)
    );
  }

  @Test
  public void nothingTest() {
    final Container c = new Container(new SomeInteger(0));

    Assert.assertEquals(
        Options.none(),
        SumTypeTest$ContainerOptics.sthSomeString
            .then(SumTypeTest$SomeStringOptics.str)
            .preview(c)
    );
  }

  @Test
  public void matchTest() {
    final Container c = new Container(new SomeInteger(5));

    Assert.assertEquals(
        new Container(new SomeInteger(10)),
        SumTypeTest$ContainerOptics.matchSth(
            c,
            someInteger -> someInteger
                .then(SumTypeTest$SomeIntegerOptics.i)
                .over(x -> x * 2, c),
            someString -> someString
                .then(SumTypeTest$SomeStringOptics.str)
                .over(x -> x + x, c)
        )
    );
  }

  sealed interface ISomething {

  }

  sealed interface ISomePrimitive extends ISomething {

  }

  @Glasses
  record SomeInteger(int i) implements ISomePrimitive {

  }

  @Glasses
  record SomeString(String str) implements ISomething {

  }

  @Glasses
  record Container(ISomething sth) {

  }
}
