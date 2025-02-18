package jtamaro.graphic;

import java.util.SequencedMap;
import org.junit.Assert;
import org.junit.Test;

public final class GraphicPropsTest {

  private static final Graphic childA = new Rectangle(200, 80, Colors.RED);

  private static final Graphic childB = new Rectangle(80, 200, Colors.BLUE);

  @Test
  public void testAboveContent() {
    final SequencedMap<String, String> props = new Above(childA, childB)
        .getProps(true);
    Assert.assertEquals(2, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
  }

  @Test
  public void testBesideContent() {
    final SequencedMap<String, String> props = new Beside(childA, childB)
        .getProps(true);
    Assert.assertEquals(2, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
  }

  @Test
  public void testCircularSectorContent() {
    final SequencedMap<String, String> props = new CircularSector(100, 90, Colors.YELLOW)
        .getProps(true);
    Assert.assertEquals(5, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
    Assert.assertTrue(props.containsKey("radius"));
    Assert.assertTrue(props.containsKey("angle"));
    Assert.assertTrue(props.containsKey("color"));
  }

  @Test
  public void testComposeContent() {
    final SequencedMap<String, String> props = new Compose(childA, childB)
        .getProps(true);
    Assert.assertEquals(2, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
  }

  @Test
  public void testEllipseContent() {
    final SequencedMap<String, String> props = new Ellipse(100, 90, Colors.GREEN)
        .getProps(true);
    Assert.assertEquals(3, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
    Assert.assertTrue(props.containsKey("color"));
  }

  @Test
  public void testEmptyContent() {
    final SequencedMap<String, String> props = new EmptyGraphic()
        .getProps(true);
    Assert.assertEquals(2, props.size());
    Assert.assertEquals(0, Double.parseDouble(props.get("width")), 0);
    Assert.assertEquals(0, Double.parseDouble(props.get("height")), 0);
  }

  @Test
  public void testOverlayContent() {
    final SequencedMap<String, String> props = new Overlay(childA, childB)
        .getProps(true);
    Assert.assertEquals(2, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
  }

  @Test
  public void testPinContent() {
    final SequencedMap<String, String> props = new Pin(Points.TOP_LEFT, childA)
        .getProps(true);
    Assert.assertEquals(3, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
    Assert.assertTrue(props.containsKey("pinPoint"));
  }

  @Test
  public void testRectangleContent() {
    final SequencedMap<String, String> props = new Rectangle(70, 120, Colors.MAGENTA)
        .getProps(true);
    Assert.assertEquals(3, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
    Assert.assertTrue(props.containsKey("color"));
  }

  @Test
  public void testRotateContent() {
    final SequencedMap<String, String> props = new Rotate(30, childA)
        .getProps(true);
    Assert.assertEquals(3, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
    Assert.assertTrue(props.containsKey("angle"));
  }

  @Test
  public void testTextContent() {
    final SequencedMap<String, String> props = new Text("Hi", Fonts.SANS_SERIF, 10, Colors.RED)
        .getProps(true);
    Assert.assertEquals(6, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
    Assert.assertTrue(props.containsKey("content"));
    Assert.assertTrue(props.containsKey("font"));
    Assert.assertTrue(props.containsKey("size"));
    Assert.assertTrue(props.containsKey("color"));
  }

  @Test
  public void testTriangleContent() {
    final SequencedMap<String, String> props = new Triangle(50, 40, 60, Colors.BLACK)
        .getProps(true);
    Assert.assertEquals(6, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
    Assert.assertTrue(props.containsKey("side1"));
    Assert.assertTrue(props.containsKey("side2"));
    Assert.assertTrue(props.containsKey("angle"));
    Assert.assertTrue(props.containsKey("color"));
  }

  @Test
  public void testAboveDumpOrder() {
    final String dump = new Above(childA, childB).dump();
    Assert.assertTrue(dump.indexOf("top") < dump.indexOf("bottom"));
  }

  @Test
  public void testBesideDumpOrder() {
    final String dump = new Beside(childA, childB).dump();
    Assert.assertTrue(dump.indexOf("left") < dump.indexOf("right"));
  }

  @Test
  public void testComposeDumpOrder() {
    final String dump = new Compose(childA, childB).dump();
    Assert.assertTrue(dump.indexOf("foreground") < dump.indexOf("background"));
  }

  @Test
  public void testOverlayDumpOrder() {
    final String dump = new Overlay(childA, childB).dump();
    Assert.assertTrue(dump.indexOf("foreground") < dump.indexOf("background"));
  }
}
