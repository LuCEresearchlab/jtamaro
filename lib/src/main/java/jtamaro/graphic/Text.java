package jtamaro.graphic;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;
import java.util.SequencedMap;

/**
 * A graphic with the text rendered using the specified font, size and color.
 *
 * <p>>When the indicated True-Type Font is not found in the system, a very basilar font that is
 * always available is used instead. The resulting graphic has the minimal size that still fits the
 * whole text.
 *
 * <p>The pinning position is horizontally aligned on the left and vertically on the baseline of
 * the text.
 */
final class Text extends Graphic {

  private final String content;

  private final String font;

  private final double size;

  private final Color color;

  /**
   * Default constructor.
   *
   * @param content text to render
   * @param font    the font to be used to render the text
   * @param size    size in typographic points (e.g., 16)
   * @param color   color to be used to fill the text
   */
  public Text(String content, String font, double size, Color color) {
    super(buildPath(content, font, size));
    this.content = content;
    this.font = font;
    this.size = size;
    this.color = color;
  }

  public String getContent() {
    return content;
  }

  public String getFont() {
    return font;
  }

  public double getSize() {
    return size;
  }

  public Color getColor() {
    return color;
  }

  @Override
  protected void render(Graphics2D g2d, RenderOptions options) {
    g2d.setPaint(Colors.toAwtColor(color));
    g2d.fill(getPath());
  }

  @Override
  protected String getInspectLabel() {
    return "text";
  }

  @Override
  protected SequencedMap<String, String> getProps(PropStyle propStyle) {
    final SequencedMap<String, String> props = super.getProps(propStyle);
    props.put("content", content);
    props.put("font", font);
    props.put("size", String.format("%.2f", size));
    props.put("color", Colors.format(propStyle, color));
    return props;
  }

  @Override
  public boolean structurallyEqualTo(Graphic other) {
    return other instanceof Text that
        && Double.compare(that.size, size) == 0
        && Objects.equals(that.content, content)
        && Objects.equals(that.font, font)
        && Objects.equals(that.color, color);
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof Text that && structurallyEqualTo(that));
  }

  @Override
  public int hashCode() {
    return Objects.hash(Text.class, content, font, size, color);
  }

  private static Path2D.Double buildPath(String content, String fontName, double size) {
    final Font font = new Font(fontName, Font.PLAIN, (int) size);
    final FontRenderContext frc = new FontRenderContext(null, true, true);
    final GlyphVector glyphVector = font.createGlyphVector(frc, content);
    final Path2D.Double path = new Path2D.Double(glyphVector.getOutline());

    final Rectangle2D tmpBbox = path.getBounds2D();
    path.transform(AffineTransform.getTranslateInstance(-tmpBbox.getMinX(), 0.0));
    return path;
  }
}
