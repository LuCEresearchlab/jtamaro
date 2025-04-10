package jtamaro.graphic;

import java.util.Objects;
import jtamaro.data.Sequence;
import jtamaro.data.Sequences;

/**
 * A CartesianWorld can be used to place Graphics at specific positions in a Cartesian coordinate
 * system. After placing zero or more Graphics, we can ask a CartesianWorld to represent itself as a
 * (composite) Graphic.
 *
 * <p>The origin of the CartesianWorld, the position (0, 0),
 * is always included in the resulting graphic. We can configure the CartesianWorld render the
 * background in a given color, and to include axes, rendered as a horizontal and vertical line
 * intersecting at the origin, in a given color.
 */
public final class CartesianWorld {

  /**
   * @implNote It is ok to use Sequence here because it is useful have fast insertion, and we never
   * need to perform random accesses.
   */
  private final Sequence<CompositionElement> compositionElements;

  private final Color axisColor;

  private final Color backgroundColor;

  /**
   * Default constructor.
   *
   * <p>Create an empty CartesianWorld.
   */
  public CartesianWorld() {
    this(Sequences.empty(), Colors.TRANSPARENT, Colors.TRANSPARENT);
  }

  private CartesianWorld(
      Sequence<CompositionElement> compositionElements,
      Color axisColor,
      Color backgroundColor
  ) {
    this.compositionElements = compositionElements;
    this.axisColor = axisColor;
    this.backgroundColor = backgroundColor;
  }

  /**
   * Configure the color of the axes for a CartesianWorld.
   *
   * @return A new CartesianWorld with the given axes color.
   */
  public CartesianWorld withAxes(Color axisColor) {
    return new CartesianWorld(
        compositionElements,
        axisColor,
        backgroundColor
    );
  }

  /**
   * Configure the background color for a CartesianWorld.
   *
   * @return A new CartesianWorld with the given background color.
   */
  public CartesianWorld withBackground(Color backgroundColor) {
    return new CartesianWorld(
        compositionElements,
        axisColor,
        backgroundColor
    );
  }

  /**
   * Place the given graphic at the given position in this CartesianWorld.
   *
   * @param x       x-position, with positive x going to the right
   * @param y       y-position, with positive y going upwards
   * @param graphic the graphic to place (the graphic will be placed such that its pinning position
   *                will coincide with the given x and y)
   * @return a new CartesianWorld that also includes the given graphic
   */
  public CartesianWorld place(double x, double y, Graphic graphic) {
    return new CartesianWorld(
        Sequences.cons(new CompositionElement(graphic, x, y), compositionElements),
        axisColor,
        backgroundColor
    );
  }


  /**
   * Produce a graphic from the cartesian world.
   */
  public Graphic asGraphic() {
    /*
     * Note: this method is long and ugly for performance reasons.
     * The main use-case of cartesian-world is interactive programs and simulations,
     * so we want it to render as fast as possible, within the constraints of our
     * public APIs.
     */

    Graphic composition = new EmptyGraphic();
    double maxX = 0.0;
    double maxY = 0.0;
    double minX = 0.0;
    double minY = 0.0;
    for (final CompositionElement el : compositionElements) {
      // Compute position
      final double startX = el.x - el.offsetX;
      final double endX = el.x + el.offsetX;
      final double startY = el.y - el.offsetY;
      final double endY = el.y + el.offsetY;
      minX = Math.min(minX, startX);
      maxX = Math.max(maxX, endX);
      minY = Math.min(minY, startY);
      maxY = Math.max(maxY, endY);

      final double strutWidth = Math.abs(el.x) + el.offsetX;
      final double strutHeight = Math.abs(el.y) + el.offsetY;
      final boolean straddleX0 = Math.abs(el.x) < el.offsetX;
      final boolean straddleY0 = Math.abs(el.y) < el.offsetY;

      final Graphic area = new Rectangle(
          straddleX0 ? strutWidth * 2 : strutWidth,
          straddleY0 ? strutHeight * 2 : strutHeight,
          Colors.TRANSPARENT
      );

      // Determine pinning
      final Point originPin;
      final Point areaPin;
      if (el.x >= 0) {
        if (el.y >= 0) {
          originPin = straddleX0 && straddleY0 ? Points.CENTER
              : straddleX0 ? Points.BOTTOM_CENTER
                  : straddleY0 ? Points.CENTER_LEFT
                      : Points.BOTTOM_LEFT;
          areaPin = Points.TOP_RIGHT;
        } else {
          originPin = straddleX0 && straddleY0 ? Points.CENTER
              : straddleX0 ? Points.TOP_CENTER
                  : straddleY0 ? Points.CENTER_LEFT
                      : Points.TOP_LEFT;
          areaPin = Points.BOTTOM_RIGHT;
        }
      } else {
        if (el.y >= 0) {
          originPin = straddleX0 && straddleY0 ? Points.CENTER
              : straddleX0 ? Points.BOTTOM_CENTER
                  : straddleY0 ? Points.CENTER_RIGHT
                      : Points.BOTTOM_RIGHT;
          areaPin = Points.TOP_LEFT;
        } else {
          originPin = straddleX0 && straddleY0 ? Points.CENTER
              : straddleX0 ? Points.TOP_CENTER
                  : straddleY0 ? Points.CENTER_RIGHT
                      : Points.TOP_RIGHT;
          areaPin = Points.BOTTOM_LEFT;
        }
      }

      // Add graphic to composition
      composition = new Compose(
          new Pin(originPin, new Compose(
              new Pin(areaPin, area),
              new Pin(areaPin, el.graphic)
          )),
          composition
      );
    }

    // Add axes (if their color would be visible)
    if (axisColor.alpha() > 0.0) {
      composition = new Compose(
          composition,
          new Compose(
              // xAxisNeg
              new Pin(Points.CENTER_RIGHT, new Rectangle(-minX, 1, axisColor)),
              new Compose(
                  // xAxisPos
                  new Pin(Points.CENTER_LEFT, new Rectangle(maxX, 1, axisColor)),
                  new Compose(
                      // yAxisNeg
                      new Pin(Points.TOP_CENTER, new Rectangle(1, -minY, axisColor)),
                      // yAxisPos
                      new Pin(Points.BOTTOM_CENTER, new Rectangle(1, maxY, axisColor))
                  )
              )
          )
      );
    }

    // Add background (if the color would be visible)
    if (backgroundColor.alpha() > 0) {
      composition = new Compose(
          composition,
          new Compose(
              // q1
              new Pin(Points.BOTTOM_LEFT, new Rectangle(maxX, maxY, backgroundColor)),
              new Compose(
                  // q2,
                  new Pin(Points.BOTTOM_RIGHT, new Rectangle(-minX, maxY, backgroundColor)),
                  new Compose(
                      // q3
                      new Pin(Points.TOP_RIGHT, new Rectangle(-minX, -minY, backgroundColor)),
                      // q4
                      new Pin(Points.TOP_LEFT, new Rectangle(maxX, -minY, backgroundColor))
                  )
              )
          )
      );
    }

    return composition;
  }

  public Color getBackgroundColor() {
    return backgroundColor;
  }

  public Color getAxisColor() {
    return axisColor;
  }

  @Override
  public boolean equals(Object other) {
    return this == other || (other instanceof CartesianWorld that
        && Objects.equals(that.compositionElements, compositionElements)
        && Objects.equals(that.backgroundColor, backgroundColor)
        && Objects.equals(that.axisColor, axisColor));
  }

  @Override
  public int hashCode() {
    return Objects.hash(CartesianWorld.class, compositionElements, backgroundColor, axisColor);
  }

  private record CompositionElement(
      Graphic graphic,
      double x,
      double y,
      double offsetX,
      double offsetY
  ) {

    public CompositionElement(Graphic graphic, double x, double y) {
      this(graphic, x, y, graphic.getWidth() / 2.0, graphic.getHeight() / 2.0);
    }
  }
}
