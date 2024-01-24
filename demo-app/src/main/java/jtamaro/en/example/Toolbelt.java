package jtamaro.en.example;

import static jtamaro.en.Pairs.*;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.Points.CENTER_LEFT;

import jtamaro.en.Sequence;
import jtamaro.en.Color;
import jtamaro.en.Function1;
import jtamaro.en.Graphic;
import jtamaro.en.Pair;


/**
 * These are commonly used methods that we do not provide in the JTamaro library,
 * but which we expect students to develop themselves throughout a course.
 * We place them here so examples can use them.
 */
public final class Toolbelt {
  
  // prevent instantiation
  private Toolbelt() {
  }

  // JTamaro Colors
  public static final Color JT_RED = rgb(210, 7, 27);
  public static final Color JT_ORANGE = rgb(255, 128, 33);
  public static final Color JT_GREEN = rgb(7, 138, 53);
  public static final Color JT_BROWN = rgb(147, 96, 77);
  public static final Color JT_YELLOW = rgb(242, 174, 0);
  public static final Color JT_BLUE = rgb(1, 138, 204);


  public static <T> int length(Sequence<T> s) {
    return isEmpty(s) ? 0 : 1 + length(rest(s));
  }


  public static <T> Pair<Sequence<T>,Sequence<T>> splitAt(int count, Sequence<T> sequence) {
    //TODO: check with Haskell: https://hackage.haskell.org/package/base-4.19.0.0/docs/Prelude.html#v:splitAt
    return pair(take(count, sequence), drop(count, sequence));
  }


  // https://hackage.haskell.org/package/split-0.2.4/docs/Data-List-Split.html#v:chunksOf
  public static <T> Sequence<Sequence<T>> chunksOf(int count, Sequence<T> sequence) {
    Pair<Sequence<T>,Sequence<T>> p = splitAt(count, sequence);
    return isEmpty(sequence) ? empty() : cons(
      firstElement(p),
      chunksOf(count, secondElement(p))
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
    return reduce(
      empty(),
      (e, a) -> concat(e, a),
      nestedSequence
    );
  }

  // concatMap == flatMap == bind
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

  // and
  public static Boolean and(Sequence<Boolean> sequence) {
    return reduce(true, (e, a) -> e && a, sequence);
  }

  // or
  public static Boolean or(Sequence<Boolean> sequence) {
    return reduce(false, (e, a) -> e || a, sequence);
  }
  
  // all
  public static <T> Boolean all(Function1<T,Boolean> predicate, Sequence<T> sequence) {
    return reduce(true, (e, a) -> predicate.apply(e) && a, sequence);
  }

  // any
  public static <T> Boolean any(Function1<T,Boolean> predicate, Sequence<T> sequence) {
    return reduce(false, (e, a) -> predicate.apply(e) || a, sequence);
  }

  // sum
  public static int sumInt(Sequence<Integer> sequence) {
    return reduce(0, (e, a) -> e + a, sequence);
  }

  public static double sumDouble(Sequence<Double> sequence) {
    return reduce(0.0, (e, a) -> e + a, sequence);
  }

  // product
  public static int productInt(Sequence<Integer> sequence) {
    return reduce(1, (e, a) -> e * a, sequence);
  }

  public static double productDouble(Sequence<Double> sequence) {
    return reduce(0.0, (e, a) -> e * a, sequence);
  }

  // maximum
  public static int maxInt(Sequence<Integer> sequence) {
    return reduce(Integer.MIN_VALUE, (e, a) -> Integer.max(e, a), sequence);
  }

  public static int maxInt1(Sequence<Integer> sequence) {
    assert !isEmpty(sequence);
    return reduce(first(sequence), (e, a) -> Integer.max(e, a), rest(sequence));
  }

  public static double maxDouble(Sequence<Double> sequence) {
    return reduce(Double.NEGATIVE_INFINITY, (e, a) -> Double.max(e, a), sequence);
  }

  public static double maxDouble1(Sequence<Double> sequence) {
    assert !isEmpty(sequence);
    return reduce(first(sequence), (e, a) -> Double.max(e, a), rest(sequence));
  }

  // minimum
  public static int minInt(Sequence<Integer> sequence) {
    return reduce(Integer.MAX_VALUE, (e, a) -> Integer.min(e, a), sequence);
  }

  public static int minInt1(Sequence<Integer> sequence) {
    assert !isEmpty(sequence);
    return reduce(first(sequence), (e, a) -> Integer.min(e, a), rest(sequence));
  }

  public static double minDouble(Sequence<Double> sequence) {
    return reduce(Double.POSITIVE_INFINITY, (e, a) -> Double.min(e, a), sequence);
  }

  public static double minDouble1(Sequence<Double> sequence) {
    assert !isEmpty(sequence);
    return reduce(first(sequence), (e, a) -> Double.min(e, a), rest(sequence));
  }


  //--- graphic reductions
  public static Graphic composes(Sequence<Graphic> graphics) {
    return reduce(
      emptyGraphic(),
      (e, a) -> compose(e, a),
      graphics
    );
  }

  public static Graphic besides(Sequence<Graphic> graphics) {
    return reduce(
      emptyGraphic(),
      (e, a) -> beside(e, a),
      graphics
    );
  }

  public static Graphic aboves(Sequence<Graphic> graphics) {
    return reduce(
      emptyGraphic(),
      (e, a) -> above(e, a),
      graphics
    );
  }

  public static Graphic overlays(Sequence<Graphic> graphics) {
    return reduce(
      emptyGraphic(),
      (e, a) -> overlay(e, a),
      graphics
    );
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
   * Construct a convex and non-convex kite, given:
   * the length of the symmetry diagonal
   * the length of a side
   * the angle between the symmetry diagonal and the side
   */
  public static Graphic kite(double symmetryDiagonal, double side, double angle, Color color) {
    assert symmetryDiagonal >= 0;
    assert side >= 0;
    assert angle >= 0;
    assert angle <= 180;
    Graphic tri1 = rotate(-angle / 2, triangle(side, symmetryDiagonal, angle, color));
    Graphic tri2 = triangle(symmetryDiagonal, side, angle, color);
    return pin(CENTER_LEFT, above(tri2, tri1));
  }

  /**
   * Construct a convex kite, given:
   * the lengths of the two parts of the symmetry diagonal (height1 and height2)
   * the length of the cross diagonal
   */
  public static Graphic kiteByHeights(double height1, double height2, double crossDiagonal, Color color) {
    assert height1 >= 0;
    assert height2 >= 0;
    assert crossDiagonal >= 0;
    Graphic tl = rotate(90, triangle(crossDiagonal / 2, height1, 90, color));
    Graphic tr = triangle(height2, crossDiagonal / 2, 90, color);
    Graphic bl = rotate(180, triangle(height1, crossDiagonal / 2, 90, color));
    Graphic br = rotate(270, triangle(crossDiagonal / 2, height2, 90, color));
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
    Graphic leftTriangle = rotate(90,
      triangle(height, (bottomWidth - topWidth) / 2, 90, color)
    );
    Graphic rightTriangle = triangle((bottomWidth - topWidth) / 2, height, 90, color);
    Graphic centralBody = rectangle(topWidth, height, color);
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
    Graphic tri = rotate(flip ? -90 : 0,
      triangle(flip ? height : wDiff, flip ? wDiff : height, 90, color));
    Graphic rec = rectangle(bottomWidth - wDiff, height, color);
    return beside(rec, tri);
  }


  //--- rings
  public static Graphic ringSectorList(double diameter, double startAngle, double endAngle, Sequence<Graphic> pieces) {
    Graphic result = emptyGraphic();
    final double angleDelta = (endAngle - startAngle) / length(pieces);
    double angle = startAngle;
    for (Graphic piece : pieces) {
        Graphic spacer = rectangle(diameter / 2, 0, BLACK);
        Graphic offsetPiece = pin(CENTER_LEFT, beside(spacer, piece));
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
    Graphic tri1 = rotate(-360 / 2 / points,
      triangle(innerRadius, outerRadius, 360 / 2 / points, color)
    );
    Graphic tri2 = triangle(outerRadius, innerRadius, 360 / 2 / points, color);
    Graphic kite = pin(CENTER_LEFT, above(tri2, tri1));
    return ring(0, kite, points);
  }


  //--- polygon using ring
  public static Graphic polygon(double diameter, int sideCount, Color color) {
    return ring(0, rotate(-360 / sideCount / 2, triangle(diameter, diameter, 360 / sideCount, color)), sideCount);
  }

}
