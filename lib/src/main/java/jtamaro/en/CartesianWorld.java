package jtamaro.en;

import jtamaro.internal.representation.GraphicImpl;
import jtamaro.internal.representation.Location;
import jtamaro.internal.representation.ColorImpl;
import jtamaro.internal.representation.ComposeImpl;
import jtamaro.internal.representation.EmptyGraphicImpl;
import jtamaro.internal.representation.PinPointImpl;
import jtamaro.internal.representation.PointImpl;
import jtamaro.internal.representation.RectangleImpl;

import static jtamaro.en.Graphics.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Points.*;

import jtamaro.en.graphic.AbstractGraphic;


/**
 * A CartesianWorld can be used to place Graphics at specific positions
 * in a Cartesian coordinate system.
 * After placing zero or more Graphics,
 * we can ask a CartesianWorld to represent itself as a (composite) Graphic.
 *
 * <p>The origin of the CartesianWorld, the position (0, 0),
 * is always included in the resulting graphic.
 * We can configure the CartesianWorld to include padding around the composition,
 * to render the background (including the padding) in a given color,
 * and to include axes, rendered as a horizontal and vertical line intersecting
 * at the origin, in a given color.
 */
public class CartesianWorld {

  // Composite graphic, pinned at the origin of the coordinate system
  private final GraphicImpl compositionImpl;
  private final Color backgroundColor;
  private final Color axisColor;
  private final double padding;


  /**
   * Create an empty CartesianWorld.
   */
  public CartesianWorld() {
    compositionImpl = new EmptyGraphicImpl();
    backgroundColor = null;
    axisColor = null;
    padding = 0.0;
  }

  private CartesianWorld(GraphicImpl compositionImpl, Color backgroundColor, Color axisColor, double padding) {
    this.compositionImpl = compositionImpl;
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
    GraphicImpl graphicToPlaceImpl = ((AbstractGraphic) graphic).getImplementation();
    GraphicImpl offset = new RectangleImpl(Math.abs(x), Math.abs(y), new ColorImpl(0, 0, 0, 0));
    PointImpl offsetOriginPoint;
    PointImpl offsetPoint;
    if (x < 0) {
      if (y < 0) {
        offsetOriginPoint = PointImpl.TOP_RIGHT;
        offsetPoint = PointImpl.BOTTOM_LEFT;
      } else {
        offsetOriginPoint = PointImpl.BOTTOM_RIGHT;
        offsetPoint = PointImpl.TOP_LEFT;
      }
    } else {
      if (y < 0) {
        offsetOriginPoint = PointImpl.TOP_LEFT;
        offsetPoint = PointImpl.BOTTOM_RIGHT;
      } else {
        offsetOriginPoint = PointImpl.BOTTOM_LEFT;
        offsetPoint = PointImpl.TOP_RIGHT;
      }
    }
    Location offsetOriginLocation = offset.getLocation(offsetOriginPoint);
    GraphicImpl pinnedOffset = new PinPointImpl(offsetPoint, offset);
    GraphicImpl placedGraphicImpl = new ComposeImpl(graphicToPlaceImpl, pinnedOffset);
    GraphicImpl result = new ComposeImpl(new PinPointImpl(offsetOriginLocation, placedGraphicImpl), compositionImpl);
    return new CartesianWorld(result, backgroundColor, axisColor, padding);
  }

  public CartesianWorld withBackground(Color backgroundColor) {
    return new CartesianWorld(compositionImpl, backgroundColor, axisColor, padding);
  }

  public CartesianWorld withAxes(Color axisColor) {
    return new CartesianWorld(compositionImpl, backgroundColor, axisColor, padding);
  }

  public CartesianWorld withPadding(double padding) {
    return new CartesianWorld(compositionImpl, backgroundColor, axisColor, padding);
  }

  /**
   * We maintain the composition in its internal representation;
   * return it wrapped in a Graphic.
   *
   * @return a Graphic representing the graphical composition of this CartesianWorld
   */
  private Graphic wrap() {
    return new AbstractGraphic(compositionImpl) {
    };
  }

  public Graphic asGraphic() {
    //System.out.println("Size: " + compositionImpl.getWidth() + ", " + compositionImpl.getHeight());
    Location pin = compositionImpl.getPin();
    //System.out.println("Pin: " + pin.getX() + ", " + pin.getY());
    Location tl = compositionImpl.getTopLeft();
    //System.out.println("TL: " + tl.getX() + ", " + tl.getY());
    Location br = compositionImpl.getBottomRight();
    //System.out.println("BR: " + br.getX() + ", " + br.getY());
    double topY = tl.getY();
    double leftX = tl.getX();
    double bottomY = br.getY();
    double rightX = br.getX();

    double leftWidth = Math.abs(leftX) + padding;
    double rightWidth = Math.abs(rightX) + padding;
    double topHeight = Math.abs(topY) + padding;
    double bottomHeight = Math.abs(bottomY) + padding;

    CartesianWorld cs = new CartesianWorld();
    if (backgroundColor != null || padding != 0) {
      Color background = backgroundColor == null ? TRANSPARENT : backgroundColor;
      cs = cs
          .place(0, 0, pin(BOTTOM_RIGHT, rectangle(leftWidth, topHeight, background)))
          .place(0, 0, pin(BOTTOM_LEFT, rectangle(rightWidth, topHeight, background)))
          .place(0, 0, pin(TOP_LEFT, rectangle(rightWidth, bottomHeight, background)))
          .place(0, 0, pin(TOP_RIGHT, rectangle(leftWidth, bottomHeight, background)));
    }
    cs = cs.place(0, 0, wrap());
    if (axisColor != null) {
      final double lineThickness = 1;
      cs = cs
          .place(0, 0, pin(CENTER_RIGHT, rectangle(leftWidth, lineThickness, axisColor)))
          .place(0, 0, pin(CENTER_LEFT, rectangle(rightWidth, lineThickness, axisColor)))
          .place(0, 0, pin(BOTTOM_CENTER, rectangle(lineThickness, topHeight, axisColor)))
          .place(0, 0, pin(TOP_CENTER, rectangle(lineThickness, bottomHeight, axisColor)));
    }
    return cs.wrap();
  }

}
