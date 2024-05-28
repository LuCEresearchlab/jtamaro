package jtamaro.io.graphic;

import jtamaro.data.Function2;

/**
 * Mouse coordinate.
 *
 * <p>Coordinates origin is at the top-left of the graphic.
 *
 * @see Interaction#withMouseMoveHandler(Function2)
 */
public record Coordinate(int x, int y) {

}
