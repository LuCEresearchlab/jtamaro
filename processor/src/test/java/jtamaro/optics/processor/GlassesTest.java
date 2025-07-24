package jtamaro.optics.processor;

import java.lang.annotation.Annotation;
import java.util.Locale;
import jtamaro.data.Sequence;
import jtamaro.data.Sequences;
import jtamaro.optics.Lens;
import jtamaro.optics.Traversal;
import org.junit.Assert;
import org.junit.Test;

public final class GlassesTest {

  @Test
  public void composedLens() {
    final Lens<PkgRecord, PkgRecord, Integer, Integer> lens = PkgRecordLenses.pr
        .then(PubRecordLenses.a);
    final PkgRecord rec = new PkgRecord(new PubRecord(0,
        0.1,
        () -> Annotation.class)
    );

    Assert.assertEquals(
        Integer.valueOf(0),
        lens.view(rec)
    );
    Assert.assertEquals(
        new PkgRecord(new PubRecord(1, rec.pr().b(), rec.pr().an())),
        lens.set(1, rec)
    );
  }

  @Test
  public void parametricType() {
    final Lens<PubRecord.InnerRecord, PubRecord.InnerRecord, Sequence<String>, Sequence<String>>
        lens = PubRecord$InnerRecordLenses.words;
    final PubRecord.InnerRecord rec = new PubRecord.InnerRecord(Sequences.of("hi"));

    Assert.assertEquals(
        Sequences.of("hi"),
        lens.view(rec)
    );
    Assert.assertEquals(
        new PubRecord.InnerRecord(Sequences.of()),
        lens.set(Sequences.of(), rec)
    );
  }

  @Test
  public void sequenceType() {
    final Traversal<PubRecord.InnerRecord, PubRecord.InnerRecord, Lens<PubRecord.InnerRecord, PubRecord.InnerRecord, String, String>, String>
        traversal = PubRecord$InnerRecordLenses.wordsElements;

    final PubRecord.InnerRecord rec = new PubRecord.InnerRecord(
        Sequences.of("hello", "world"));

    Assert.assertEquals(
        new PubRecord.InnerRecord(rec.words()
            .map(it -> it.toUpperCase(Locale.ROOT))),
        traversal.over(
            l -> l.view(rec).toUpperCase(Locale.ROOT),
            rec
        )
    );
  }

  @Test
  public void sequenceAtIndexLens() {
    final Lens<PubRecord.InnerRecord, PubRecord.InnerRecord, String, String> lens
        = PubRecord$InnerRecordLenses.wordsLensAt(1);

    final PubRecord.InnerRecord rec = new PubRecord.InnerRecord(
        Sequences.of("Zero", "Two", "Two"));

    Assert.assertEquals(
        new PubRecord.InnerRecord(
            Sequences.of("Zero", "One", "Two")),
        lens.set("One", rec)
    );
  }
}
