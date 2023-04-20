package jtamaro.internal.representation;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;


/**
 * Compute the tight bounding box of a given Shape.
 * Shape.getBounds() and Shape.getBounds2D() only computes a fast conservative approximation
 * of the bounding box.
 * Specifically, the bounding box of a rotated shape, which is represented as a Path2D,
 * will be computed by Path2D.getBounds2D(), and that approximation
 * simply looks at all the points and control points.
 * This causes, e.g., the bounding box of a rotated circle
 * to be considerably bigger than the tight bounding box.
 * For example, Path2D.getBounds2D() on a circle of radius 100 that was rotated by 45 degrees,
 * returns a bounding box with width and height of about 220).
 */
public final class TightBoundingBox {

  private double minX;
  private double minY;
  private double maxX;
  private double maxY;


  public TightBoundingBox(final Shape s) {
    minX = Double.POSITIVE_INFINITY;
    maxX = Double.NEGATIVE_INFINITY;
    minY = Double.POSITIVE_INFINITY;
    maxY = Double.NEGATIVE_INFINITY;
    PathIterator it = s.getPathIterator(new AffineTransform());
    growBoundingBoxFromPath(it);
  }

  public double getMinX() {
    return minX;
  }

  public double getMaxX() {
    return maxX;
  }

  public double getMinY() {
    return minY;
  }

  public double getMaxY() {
    return maxY;
  }

  public double getWidth() {
    return maxX - minX;
  }

  public double getHeight() {
    return maxY - minY;
  }

  private void includePoint(final double x, final double y) {
    if (Double.isFinite(x)) {
      minX = Math.min(minX, x);
      maxX = Math.max(maxX, x);
    }
    if (Double.isFinite(y)) {
      minY = Math.min(minY, y);
      maxY = Math.max(maxY, y);
    }
  }

  private double f(double t, double p0i, double p1i, double p2i, double p3i) {
    return
        Math.pow(1 - t, 3) * p0i +
            3 * Math.pow(1 - t, 2) * t * p1i +
            3 * (1 - t) * Math.pow(t, 2) * p2i +
            Math.pow(t, 3) * p3i;
  }

  /**
   * Gets the exact bounding box of the path by evaluating curve segments.
   * Slower to compute than the control box, but more accurate.
   */
  private void growBoundingBoxFromPath(final PathIterator it) {
    double cx = 0;
    double cy = 0;

    double[] coords = new double[6];
    double[] p0 = new double[2];
    double[] p1 = new double[2];
    double[] p2 = new double[2];
    double[] p3 = new double[2];
    while (!it.isDone()) {
      int type = it.currentSegment(coords);
      switch (type) {
        case PathIterator.SEG_MOVETO:
        case PathIterator.SEG_LINETO:
          double x = coords[0];
          double y = coords[1];
          includePoint(x, y);
          cx = x;
          cy = y;
          break;

        case PathIterator.SEG_QUADTO:
        case PathIterator.SEG_CUBICTO:
          final double cp1x, cp1y, cp2x, cp2y, p3x, p3y;
          if (type == PathIterator.SEG_QUADTO) {
            // convert QUADTO to CUBICTO
            // http://fontforge.org/bezier.html
            final double qp1x = coords[0];
            final double qp1y = coords[1];
            p3x = coords[2];
            p3y = coords[3];
            cp1x = cx + 2.0 / 3.0 * (qp1x - cx);    // CP1 = QP0 + 2/3 * (QP1-QP0)
            cp1y = cy + 2.0 / 3.0 * (qp1y - cy);
            cp2x = p3x + 2.0 / 3.0 * (qp1x - p3x);  // CP2 = QP2 + 2/3 * (QP1-QP2)
            cp2y = p3y + 2.0 / 3.0 * (qp1y - p3y);
          } else {
            cp1x = coords[0];
            cp1y = coords[1];
            cp2x = coords[2];
            cp2y = coords[3];
            p3x = coords[4];
            p3y = coords[5];
          }

          // http://blog.hackers-cafe.net/2009/06/how-to-calculate-bezier-curves-bounding.html
          includePoint(p3x, p3y);

          p0[0] = cx;
          p0[1] = cy;
          p1[0] = cp1x;
          p1[1] = cp1y;
          p2[0] = cp2x;
          p2[1] = cp2y;
          p3[0] = p3x;
          p3[1] = p3y;

          for (int i = 0; i <= 1; i++) {
            final double b = 6 * p0[i] - 12 * p1[i] + 6 * p2[i];
            final double a = -3 * p0[i] + 9 * p1[i] - 9 * p2[i] + 3 * p3[i];
            final double c = 3 * p1[i] - 3 * p0[i];

            if (a == 0) {
              if (b == 0) {
                continue;
              }

              final double t = -c / b;
              if (0 < t && t < 1) {
                if (i == 0) {
                  includePoint(f(t, p0[i], p1[i], p2[i], p3[i]), maxY);
                } else {
                  includePoint(maxX, f(t, p0[i], p1[i], p2[i], p3[i]));
                }
              }

              continue;
            }

            final double b2ac = Math.pow(b, 2) - 4 * c * a;
            if (b2ac < 0) {
              continue;
            }

            final double t1 = (-b + Math.sqrt(b2ac)) / (2 * a);
            if (0 < t1 && t1 < 1) {
              if (i == 0) {
                includePoint(f(t1, p0[i], p1[i], p2[i], p3[i]), maxY);
              } else {
                includePoint(maxX, f(t1, p0[i], p1[i], p2[i], p3[i]));
              }
            }

            final double t2 = (-b - Math.sqrt(b2ac)) / (2 * a);
            if (0 < t2 && t2 < 1) {
              if (i == 0) {
                includePoint(f(t2, p0[i], p1[i], p2[i], p3[i]), maxY);
              } else {
                includePoint(maxX, f(t2, p0[i], p1[i], p2[i], p3[i]));
              }
            }
          }

          cx = p3x;
          cy = p3y;
          break;
      }
      it.next();
    }
  }

}
