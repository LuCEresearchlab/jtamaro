package jtamaro.optics;

import jtamaro.data.Eithers;
import jtamaro.data.Options;
import org.junit.Assert;
import org.junit.Test;

public final class PrismTest {

  @Test
  public void maybeJustReviewJust() {
    final String s = "Duck";

    Assert.assertEquals(
        Options.some(s),
        Prisms.some().review(s)
    );
  }

  @Test
  public void maybeJustPreviewJust() {
    final String s = "Poster";

    Assert.assertEquals(
        Options.some(s),
        Prisms.some().preview(Options.some(s))
    );
  }

  @Test
  public void maybeJustPreviewNothing() {
    Assert.assertEquals(
        Options.none(),
        Prisms.some().preview(Options.none())
    );
  }

  @Test
  public void maybeJustGetOrModifyJust() {
    final String s = "Box";

    Assert.assertEquals(
        Eithers.right(s),
        Prisms.some().getOrModify(Options.some(s))
    );
  }

  @Test
  public void maybeJustGetOrModifyNothing() {
    Assert.assertEquals(
        Eithers.left(Options.none()),
        Prisms.some().getOrModify(Options.none())
    );
  }

  @Test
  public void eitherLeftPreviewLeft() {
    final String s = "Glass";

    Assert.assertEquals(
        Options.some(s),
        Prisms.left().preview(Eithers.left(s))
    );
  }

  @Test
  public void eitherRightPreviewRight() {
    final String s = "Pencil";

    Assert.assertEquals(
        Options.none(),
        Prisms.right().preview(Eithers.left(s))
    );
  }

  @Test
  public void eitherLeftPreviewRight() {
    final String s = "Eraser";

    Assert.assertEquals(
        Options.none(),
        Prisms.left().preview(Eithers.right(s))
    );
  }

  @Test
  public void eitherLeftReviewLeft() {
    final String s = "Book";

    Assert.assertEquals(
        Eithers.left(s),
        Prisms.left().review(s)
    );
  }

  @Test
  public void eitherRightReviewRight() {
    final String s = "Tape";

    Assert.assertEquals(
        Eithers.right(s),
        Prisms.right().review(s)
    );
  }

  @Test
  public void eitherLeftPrismLeftGetOrModify() {
    final String s = "Marker";

    Assert.assertEquals(
        Eithers.right(s),
        Prisms.left().getOrModify(Eithers.left(s))
    );
  }

  @Test
  public void eitherRightPrismLeftGetOrModify() {
    final String s = "Chair";

    Assert.assertEquals(
        Eithers.left(Eithers.right(s)),
        Prisms.left().getOrModify(Eithers.right(s))
    );
  }

  @Test
  public void eitherLeftPrismRightGetOrModify() {
    final String s = "Ball";

    Assert.assertEquals(
        Eithers.left(Eithers.left(s)),
        Prisms.right().getOrModify(Eithers.left(s))
    );
  }

  @Test
  public void eitherRightPrismRightGetOrModify() {
    final String s = "Pencil";

    Assert.assertEquals(
        Eithers.right(s),
        Prisms.right().getOrModify(Eithers.right(s))
    );
  }
}
