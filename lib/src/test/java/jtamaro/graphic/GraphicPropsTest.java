package jtamaro.graphic;

import java.util.SequencedMap;
import org.junit.Assert;
import org.junit.Test;

public final class GraphicPropsTest {

  private static final Graphic CHILD_A = new Rectangle(200, 80, Colors.RED);

  private static final Graphic CHILD_B = new Rectangle(80, 200, Colors.BLUE);

  @Test
  public void testAboveContent() {
    final SequencedMap<String, String> props = new Above(CHILD_A, CHILD_B)
        .getProps(Graphic.PropStyle.PLAIN);
    Assert.assertEquals(2, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
  }

  @Test
  public void testBesideContent() {
    final SequencedMap<String, String> props = new Beside(CHILD_A, CHILD_B)
        .getProps(Graphic.PropStyle.PLAIN);
    Assert.assertEquals(2, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
  }

  @Test
  public void testCircularSectorContent() {
    final SequencedMap<String, String> props = new CircularSector(100, 90, Colors.YELLOW)
        .getProps();
    Assert.assertEquals(5, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
    Assert.assertTrue(props.containsKey("radius"));
    Assert.assertTrue(props.containsKey("angle"));
    Assert.assertTrue(props.containsKey("color"));
  }

  @Test
  public void testComposeContent() {
    final SequencedMap<String, String> props = new Compose(CHILD_A, CHILD_B).getProps();
    Assert.assertEquals(2, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
  }

  @Test
  public void testEllipseContent() {
    final SequencedMap<String, String> props = new Ellipse(100, 90, Colors.GREEN).getProps();
    Assert.assertEquals(3, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
    Assert.assertTrue(props.containsKey("color"));
  }

  @Test
  public void testEmptyContent() {
    final SequencedMap<String, String> props = new EmptyGraphic().getProps();
    Assert.assertEquals(2, props.size());
    Assert.assertEquals(0, Double.parseDouble(props.get("width")), 0);
    Assert.assertEquals(0, Double.parseDouble(props.get("height")), 0);
  }

  @Test
  public void testOverlayContent() {
    final SequencedMap<String, String> props = new Overlay(CHILD_A, CHILD_B).getProps();
    Assert.assertEquals(2, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
  }

  @Test
  public void testPinContent() {
    final SequencedMap<String, String> props = new Pin(Points.TOP_LEFT, CHILD_A).getProps();
    Assert.assertEquals(3, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
    Assert.assertTrue(props.containsKey("pinPoint"));
  }

  @Test
  public void testRectangleContent() {
    final SequencedMap<String, String> props = new Rectangle(70, 120, Colors.MAGENTA).getProps();
    Assert.assertEquals(3, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
    Assert.assertTrue(props.containsKey("color"));
  }

  @Test
  public void testRotateContent() {
    final SequencedMap<String, String> props = new Rotate(30, CHILD_A).getProps();
    Assert.assertEquals(3, props.size());
    Assert.assertTrue(props.containsKey("width"));
    Assert.assertTrue(props.containsKey("height"));
    Assert.assertTrue(props.containsKey("angle"));
  }

  @Test
  public void testTextContent() {
    final SequencedMap<String, String> props = new Text("Hi", Fonts.SANS_SERIF, 10, Colors.RED)
        .getProps();
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
    final SequencedMap<String, String> props = new Triangle(50, 40, 60, Colors.BLACK).getProps();
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
    final String dump = new Above(CHILD_A, CHILD_B).dump();
    Assert.assertTrue(dump.indexOf("top") < dump.indexOf("bottom"));
  }

  @Test
  public void testBesideDumpOrder() {
    final String dump = new Beside(CHILD_A, CHILD_B).dump();
    Assert.assertTrue(dump.indexOf("left") < dump.indexOf("right"));
  }

  @Test
  public void testComposeDumpOrder() {
    final String dump = new Compose(CHILD_A, CHILD_B).dump();
    Assert.assertTrue(dump.indexOf("foreground") < dump.indexOf("background"));
  }

  @Test
  public void testOverlayDumpOrder() {
    final String dump = new Overlay(CHILD_A, CHILD_B).dump();
    Assert.assertTrue(dump.indexOf("foreground") < dump.indexOf("background"));
  }
}
