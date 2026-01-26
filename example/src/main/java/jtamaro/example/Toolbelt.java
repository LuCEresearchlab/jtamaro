package jtamaro.example;

import jtamaro.data.Function1;
import jtamaro.data.Pair;
import jtamaro.data.Sequence;
import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.Graphics;

import static jtamaro.data.Sequences.cons;
import static jtamaro.data.Sequences.empty;
import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.replicate;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.rgb;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Graphics.triangle;
import static jtamaro.graphic.Points.CENTER_LEFT;

/**
 * These are commonly used methods that we do not provide in the JTamaro library, but which we
 * expect students to develop themselves throughout a course. We place them here so examples can use
 * them.
 */
@SuppressWarnings("unused")
public final class Toolbelt {

  // JTamaro Colors
  public static final Color JT_RED = rgb(210, 7, 27);

  public static final Color JT_ORANGE = rgb(255, 128, 33);

  public static final Color JT_GREEN = rgb(7, 138, 53);

  public static final Color JT_BROWN = rgb(147, 96, 77);

  public static final Color JT_YELLOW = rgb(242, 174, 0);

  public static final Color JT_BLUE = rgb(1, 138, 204);

  // prevent instantiation
  private Toolbelt() {
  }

  public static <T> int length(Sequence<T> s) {
    return s.isEmpty() ? 0 : 1 + length(s.rest());
  }

  public static <T> T get(Sequence<T> s, int i) {
    return i == 0
        ? s.first()
        : get(s.rest(), i - 1);
  }

  public static <T> Pair<Sequence<T>, Sequence<T>> splitAt(int count, Sequence<T> sequence) {
    //TODO: check with Haskell: https://hackage.haskell.org/package/base-4.19.0.0/docs/Prelude.html#v:splitAt
    return new Pair<>(sequence.take(count), sequence.drop(count));
  }


  // https://hackage.haskell.org/package/split-0.2.4/docs/Data-List-Split.html#v:chunksOf
  public static <T> Sequence<Sequence<T>> chunksOf(int count, Sequence<T> sequence) {
    Pair<Sequence<T>, Sequence<T>> p = splitAt(count, sequence);
    return sequence.isEmpty() ? empty() : cons(
        p.first(),
        chunksOf(count, p.second())
    );
  }

  // Python's [a,b,c] * count
  // TODO: Is there a Haskell library function for this?
  //       Like a finite `cycle`?
  //       Doesn't seem like it.
  //       Would use `concatMap (replicate count) subsequence`
  public static <T> Sequence<T> times(int count, Sequence<T> subsequence) {
    return concats(replicate(subsequence, count));
  }

  //--- special folds
  // https://hackage.haskell.org/package/base-4.14.1.0/docs/Data-List.html#g:4

  // concat (flatten) == join
  public static <T> Sequence<T> concats(Sequence<Sequence<T>> nestedSequence) {
    return nestedSequence.reduce(empty(), Sequence::concat);
  }

  // concatMap == flatMap == bind
  /*
  public static <T,U> Sequence<U> concatMap(Function1<T,Sequence<U>> mapper, Sequence<T> nestedSequence) {
    return flatMap(mapper, nestedSequence);
    // return reduce(
    //   empty(),
    //   (T e, Sequence<U> a) -> concat(mapper.apply(e), a),
    //   nestedSequence
    // );
    //return concats(map(mapper, nestedSequence));
    // (more generally, in Haskell) return join(fmap(mapper, nestedSequence));
  }
   */

  // and
  public static Boolean and(Sequence<Boolean> sequence) {
    return sequence.reduce(true, (e, a) -> e && a);
  }

  // or
  public static Boolean or(Sequence<Boolean> sequence) {
    return sequence.reduce(false, (e, a) -> e || a);
  }

  // all
  public static <T> Boolean all(Function1<T, Boolean> predicate, Sequence<T> sequence) {
    return sequence.reduce(true, (e, a) -> predicate.apply(e) && a);
  }

  // any
  public static <T> Boolean any(Function1<T, Boolean> predicate, Sequence<T> sequence) {
    return sequence.reduce(false, (e, a) -> predicate.apply(e) || a);
  }

  // sum
  public static int sumInt(Sequence<Integer> sequence) {
    return sequence.reduce(0, Integer::sum);
  }

  public static double sumDouble(Sequence<Double> sequence) {
    return sequence.reduce(0.0, Double::sum);
  }

  // product
  public static int productInt(Sequence<Integer> sequence) {
    return sequence.reduce(1, (e, a) -> e * a);
  }

  public static double productDouble(Sequence<Double> sequence) {
    return sequence.reduce(0.0, (e, a) -> e * a);
  }

  // maximum
  public static int maxInt(Sequence<Integer> sequence) {
    return sequence.reduce(Integer.MIN_VALUE, Integer::max);
  }

  public static int maxInt1(Sequence<Integer> sequence) {
    assert !sequence.isEmpty();
    return sequence.rest().reduce(sequence.first(), Integer::max);
  }

  public static double maxDouble(Sequence<Double> sequence) {
    return sequence.reduce(Double.NEGATIVE_INFINITY, Double::max);
  }

  public static double maxDouble1(Sequence<Double> sequence) {
    assert !sequence.isEmpty();
    return sequence.rest().reduce(sequence.first(), Double::max);
  }

  // minimum
  public static int minInt(Sequence<Integer> sequence) {
    return sequence.reduce(Integer.MAX_VALUE, Integer::min);
  }

  public static int minInt1(Sequence<Integer> sequence) {
    assert !sequence.isEmpty();
    return sequence.rest().reduce(sequence.first(), Integer::min);
  }

  public static double minDouble(Sequence<Double> sequence) {
    return sequence.reduce(Double.POSITIVE_INFINITY, Double::min);
  }

  public static double minDouble1(Sequence<Double> sequence) {
    assert !sequence.isEmpty();
    return sequence.rest().reduce(sequence.first(), Double::min);
  }


  //--- graphic reductions
  public static Graphic composes(Sequence<Graphic> graphics) {
    return graphics.reduce(emptyGraphic(), Graphics::compose);
  }

  public static Graphic besides(Sequence<Graphic> graphics) {
    return graphics.reduce(emptyGraphic(), Graphics::beside);
  }

  public static Graphic aboves(Sequence<Graphic> graphics) {
    return graphics.reduce(emptyGraphic(), Graphics::above);
  }

  public static Graphic overlays(Sequence<Graphic> graphics) {
    return graphics.reduce(emptyGraphic(), Graphics::overlay);
  }


  //--- gaps
  public static Graphic hgap(double width) {
    return rectangle(width, 0, TRANSPARENT);
  }

  public static Graphic vgap(double height) {
    return rectangle(0, height, TRANSPARENT);
  }


  //--- specialized rectangle
  public static Graphic square(double side, Color color) {
    return rectangle(side, side, color);
  }


  //--- specialized ellipse
  public static Graphic circle(double diameter, Color color) {
    return ellipse(diameter, diameter, color);
  }


  //--- specialized triangles
  public static Graphic isoscelesTriangle(double side, double angle, Color color) {
    return triangle(side, side, angle, color);
  }

  public static Graphic equilateralTriangle(double side, Color color) {
    return triangle(side, side, 60, color);
  }

  public static Graphic rightTriangle(double side1, double side2, Color color) {
    return triangle(side1, side2, 90, color);
  }

  //--- kites

  /**
   * Construct a convex and non-convex kite, given: the length of the symmetry diagonal the length
   * of a side the angle between the symmetry diagonal and the side
   */
  public static Graphic kite(double symmetryDiagonal, double side, double angle, Color color) {
    assert symmetryDiagonal >= 0;
    assert side >= 0;
    assert angle >= 0;
    assert angle <= 180;
    final Graphic tri1 = rotate(-angle / 2, triangle(side, symmetryDiagonal, angle, color));
    final Graphic tri2 = triangle(symmetryDiagonal, side, angle, color);
    return pin(CENTER_LEFT, above(tri2, tri1));
  }

  /**
   * Construct a convex kite, given: the lengths of the two parts of the symmetry diagonal (height1
   * and height2) the length of the cross diagonal
   */
  public static Graphic kiteByHeights(double height1, double height2, double crossDiagonal, Color color) {
    assert height1 >= 0;
    assert height2 >= 0;
    assert crossDiagonal >= 0;
    final Graphic tl = rotate(90, triangle(crossDiagonal / 2, height1, 90, color));
    final Graphic tr = triangle(height2, crossDiagonal / 2, 90, color);
    final Graphic bl = rotate(180, triangle(height1, crossDiagonal / 2, 90, color));
    final Graphic br = rotate(270, triangle(crossDiagonal / 2, height2, 90, color));
    return above(
        beside(tl, tr),
        beside(bl, br)
    );
  }


  //--- trapezoids
  public static Graphic isoscelesTrapezoid(double bottomWidth, double height, double topWidth, Color color) {
    assert topWidth <= bottomWidth;
    assert height > 0;
    assert bottomWidth > 0;
    final Graphic leftTriangle = rotate(90,
        triangle(height, (bottomWidth - topWidth) / 2, 90, color)
    );
    final Graphic rightTriangle = triangle((bottomWidth - topWidth) / 2, height, 90, color);
    final Graphic centralBody = rectangle(topWidth, height, color);
    return besides(of(leftTriangle, centralBody, rightTriangle));
  }

  /**
   * Construct a right trapezoid by placing a right triangle on the right side of a rectangle
   */
  public static Graphic rightTrapezoid(double bottomWidth, double height, double topWidth, Color color, boolean flip) {
    assert topWidth <= bottomWidth;
    assert height > 0;
    assert bottomWidth > 0;
    final double wDiff = bottomWidth - topWidth;
    final Graphic tri = rotate(flip ? -90 : 0,
        triangle(flip ? height : wDiff, flip ? wDiff : height, 90, color));
    final Graphic rec = rectangle(bottomWidth - wDiff, height, color);
    return beside(rec, tri);
  }


  //--- rings
  public static Graphic ringSectorList(double diameter, double startAngle, double endAngle, Sequence<Graphic> pieces) {
    Graphic result = emptyGraphic();
    final double angleDelta = (endAngle - startAngle) / length(pieces);
    double angle = startAngle;
    for (Graphic piece : pieces) {
      final Graphic spacer = rectangle(diameter / 2, 0, BLACK);
      final Graphic offsetPiece = pin(CENTER_LEFT, beside(spacer, piece));
      result = compose(result, rotate(angle, offsetPiece));
      angle += angleDelta;
    }
    return result;
  }

  public static Graphic ringList(double diameter, Sequence<Graphic> pieces) {
    return ringSectorList(diameter, 0, 360, pieces);
  }

  public static Graphic ringSector(double diameter, double startAngle, double endAngle, Graphic piece, int count) {
    return ringSectorList(diameter, startAngle, endAngle, replicate(piece, count));
  }

  public static Graphic ring(double diameter, Graphic piece, int count) {
    return ringList(diameter, replicate(piece, count));
  }


  //--- star using ring
  public static Graphic star(double innerRadius, double outerRadius, int points, Color color) {
    // TODO: reformulate using kite()
    final Graphic tri1 = rotate(-360.0 / 2.0 / points,
        triangle(innerRadius, outerRadius, 360.0 / 2.0 / points, color)
    );
    final Graphic tri2 = triangle(outerRadius, innerRadius, 360.0 / 2.0 / points, color);
    final Graphic kite = pin(CENTER_LEFT, above(tri2, tri1));
    return ring(0, kite, points);
  }


  //--- polygon using ring
  public static Graphic polygon(double diameter, int sideCount, Color color) {
    return ring(0,
        rotate(-360.0 / sideCount / 2.0, triangle(diameter, diameter, 360.0 / sideCount, color)),
        sideCount);
  }
}
