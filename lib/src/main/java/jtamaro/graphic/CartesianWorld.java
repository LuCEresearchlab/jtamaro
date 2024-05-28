package jtamaro.graphic;

import java.util.Objects;

/**
 * A CartesianWorld can be used to place Graphics at specific positions in a Cartesian coordinate
 * system. After placing zero or more Graphics, we can ask a CartesianWorld to represent itself as a
 * (composite) Graphic.
 *
 * <p>The origin of the CartesianWorld, the position (0, 0),
 * is always included in the resulting graphic. We can configure the CartesianWorld to include
 * padding around the composition, to render the background (including the padding) in a given
 * color, and to include axes, rendered as a horizontal and vertical line intersecting at the
 * origin, in a given color.
 */
public final class CartesianWorld {

  private final Graphic composition;

  private final Color backgroundColor;

  private final Color axisColor;

  private final double padding;

  /**
   * Default constructor.
   *
   * <p>Create an empty CartesianWorld.
   */
  public CartesianWorld() {
    this(new EmptyGraphic(), Colors.TRANSPARENT, Colors.TRANSPARENT, 0.0);
  }

  /**
   * Create a CartesianWorld.
   *
   * @param composition     Graphic of the cartesian world. Drawn on top of a background.
   * @param backgroundColor Color of the background.
   * @param axisColor       Color of the axes, drawn on top of the composition.
   * @param padding         Padding added to the background with respect to the composition.
   */
  public CartesianWorld(Graphic composition, Color backgroundColor, Color axisColor, double padding) {
    this.composition = composition;
    this.backgroundColor = backgroundColor;
    this.axisColor = axisColor;
    this.padding = padding;
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
    final Graphic offset = new Rectangle(Math.abs(x), Math.abs(y), Colors.TRANSPARENT);
    final Point offsetOriginPoint;
    final Point offsetPoint;
    if (x < 0) {
      if (y < 0) {
        offsetOriginPoint = Points.TOP_RIGHT;
        offsetPoint = Points.BOTTOM_LEFT;
      } else {
        offsetOriginPoint = Points.BOTTOM_RIGHT;
        offsetPoint = Points.TOP_LEFT;
      }
    } else {
      if (y < 0) {
        offsetOriginPoint = Points.TOP_LEFT;
        offsetPoint = Points.BOTTOM_RIGHT;
      } else {
        offsetOriginPoint = Points.BOTTOM_LEFT;
        offsetPoint = Points.TOP_RIGHT;
      }
    }
    final Graphic pinnedOffset = new Pin(offsetPoint, offset);
    final Graphic placedGraphicImpl = new Compose(graphic, pinnedOffset);
    final Graphic result = new Compose(new Pin(offsetOriginPoint, placedGraphicImpl),
        composition);
    return new CartesianWorld(result, backgroundColor, axisColor, padding);
  }

  /**
   * Configure the background color for a CartesianWorld.
   *
   * @return A new CartesianWorld with the given background color.
   */
  public CartesianWorld withBackground(Color backgroundColor) {
    return new CartesianWorld(composition, backgroundColor, axisColor, padding);
  }

  /**
   * Configure the color of the axes for a CartesianWorld.
   *
   * @return A new CartesianWorld with the given axes color.
   */
  public CartesianWorld withAxes(Color axisColor) {
    return new CartesianWorld(composition, backgroundColor, axisColor, padding);
  }

  /**
   * Configure the color of the background of a CartesianWorld.
   *
   * @return A new CartesianWorld with the given background color.
   */
  public CartesianWorld withPadding(double padding) {
    return new CartesianWorld(composition, backgroundColor, axisColor, padding);
  }

  /**
   * Produce a graphic from the cartesian world.
   */
  public Graphic asGraphic() {
    Graphic result = composition;

    // Add background
    if (backgroundColor.alpha() > 0 || (Double.isFinite(padding) && padding != 0.0)) {
      final double width = composition.getWidth() + padding * 2.0;
      final double height = composition.getHeight() + padding * 2.0;

      // Overlay the composition on top of the background while retaining the original pin
      result = new Pin(
          result.getPin(),
          new Overlay(
              result,
              new Rectangle(width, height, backgroundColor)
          ));
    }

    // Add axes
    if (axisColor.alpha() > 0) {
      final Graphic verticalAxis = new Rectangle(1.0, result.getHeight(), axisColor);
      final Graphic horizontalAxis = new Rectangle(result.getWidth(), 1.0, axisColor);

      // Overlay the axes on top of the composition while retaining the original pin
      result = new Pin(
          result.getPin(),
          new Compose(
              new Pin(Points.TOP_CENTER, verticalAxis),
              new Pin(
                  Points.TOP_CENTER,
                  new Compose(
                      new Pin(Points.CENTER_LEFT, horizontalAxis),
                      new Pin(Points.CENTER_LEFT, result)
                  )
              )
          )
      );
    }

    return result;
  }

  public Color getBackgroundColor() {
    return backgroundColor;
  }

  public Color getAxisColor() {
    return axisColor;
  }

  public double getPadding() {
    return padding;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (other instanceof CartesianWorld that) {
      return Double.compare(that.padding, padding) == 0
          && Objects.equals(that.composition, composition)
          && Objects.equals(that.backgroundColor, backgroundColor)
          && Objects.equals(that.axisColor, axisColor);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(CartesianWorld.class, composition, backgroundColor, axisColor, padding);
  }
}
