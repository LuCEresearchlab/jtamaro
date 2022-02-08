package jtamaro.internal.representation;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Path2D;
import java.awt.font.GlyphVector;

import jtamaro.internal.gui.RenderOptions;


public final class TextImpl extends GraphicImpl {

  private final String text;
  private final String fontName;
  private final double fontSize;
  private final ColorImpl color;


  public TextImpl(String content, String fontName, double size, ColorImpl color) {
    this.text = content;
    this.fontName = fontName;
    this.fontSize = size;
    this.color = color;
    final Font font = new Font(fontName, Font.PLAIN, (int)size);
    final FontRenderContext frc = new FontRenderContext(null, true, true);
    //final LineMetrics lineMetrics = font.getLineMetrics(content, frc);
    final GlyphVector glyphVector = font.createGlyphVector(frc, content);
    setPath(new Path2D.Double(glyphVector.getOutline()));
    setBaseY(0);
    addBoundingBoxPoints();
    addPoint(Place.BAL, 0, 0);
    addPoint(Place.BAM, getBBox().getWidth() / 2, 0);
    addPoint(Place.BAR, getBBox().getWidth(), 0);
  }

  public ColorImpl getColor() {
    return color;
  }
  
  @Override
  public void render(Graphics2D g2, RenderOptions o) {
    g2.setPaint(color.toAWT());
    g2.fill(getPath());
    drawDebugInfo(g2, o);
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
