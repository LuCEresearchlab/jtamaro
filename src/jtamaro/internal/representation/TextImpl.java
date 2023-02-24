package jtamaro.internal.representation;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Path2D;
import java.awt.geom.AffineTransform;
import java.awt.font.GlyphVector;

import jtamaro.internal.gui.RenderOptions;


public final class TextImpl extends GraphicImpl {

  private final String text;
  private final String fontName;
  private final double fontSize;
  private final ColorImpl color;
  private final Location baselineStart;
  private final Location baselineLeft;
  private final Location baselineMiddle;
  private final Location baselineRight;


  public TextImpl(String content, String fontName, double size, ColorImpl color) {
    this.text = content;
    this.fontName = fontName;
    this.fontSize = size;
    this.color = color;
    final Font font = new Font(fontName, Font.PLAIN, (int)size);
    final FontRenderContext frc = new FontRenderContext(null, true, true);
    //final LineMetrics lineMetrics = font.getLineMetrics(content, frc);
    final GlyphVector glyphVector = font.createGlyphVector(frc, content);
    final Path2D.Double path = new Path2D.Double(glyphVector.getOutline());
    // The left edge of the bbox of the path is usually not 0, but at a negative x value.
    // But we want our TextImpl's path to start at x=0, so we translate it.
    final TightBoundingBox tmpBbox = new TightBoundingBox(path);
    path.transform(AffineTransform.getTranslateInstance(-tmpBbox.getMinX(), 0));
    setPath(path);
    //setBaseY(0);
    //addBoundingBoxLocations();
    // Those are bbox bounds on the baseline.
    // We could also add places for text-metric-based bounds (left, right).
    //addLocation(Place.BAL, getBBox().getMinX(), 0);
    //addLocation(Place.BAM, (getBBox().getMinX() + getBBox().getMaxX()) / 2, 0);
    //addLocation(Place.BAR, getBBox().getMaxX(), 0);
    baselineStart = new Location(this, -tmpBbox.getMinX(), 0);
    baselineLeft = new Location(this, getBBox().getMinX(), 0);
    baselineMiddle = new Location(this, (getBBox().getMinX() + getBBox().getMaxX()) / 2, 0);
    baselineRight = new Location(this, getBBox().getMaxX(), 0);
  }

  // new Point API
  /**
   * The origin of the outline path of the rendered text.
   * Usually this is a point on the baseline to the left of the bounding box.
   * The point at which to draw a text, 
   * if one wants the left edge of the bounding box of the rendered text to be at x=0.
   */
  public Location getBaselineStart() {
    return baselineStart;
  }

  public Location getBaselineLeft() {
    return baselineLeft;
  }

  public Location getBaselineMiddle() {
    return baselineMiddle;
  }

  public Location getBaselineRight() {
    return baselineRight;
  }


  public ColorImpl getColor() {
    return color;
  }

  @Override
  public void render(Graphics2D g2, RenderOptions o) {
    g2.setPaint(color.toAWT());
    g2.fill(getPath());
  }

  @Override
  public void dump(StringBuilder sb, String indent) {
    super.dump(sb, indent);
    appendField(sb, indent, "text", text);
    appendField(sb, indent, "fontName", fontName);
    appendField(sb, indent, "fontSize", ""+fontSize);
    appendField(sb, indent, "color", ""+color);
  }

}
