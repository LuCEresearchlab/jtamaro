package jtamaro.internal.representation;

/**
 * A place within a shape, such as a corner of the bounding box.
 */
public class Place {

  private final String name;
  private final String description;


  private Place(final String name, final String description) {
    this.name = name;
    this.description = description;
  }

  public int hashCode() {
    return name.hashCode();
  }

  public boolean equals(final Object other) {
    return other instanceof Place && ((Place)other).name.equals(name);
  }

  public String toString() {
    return name;
  }

  public static final Place TL = new Place("TL", "The top-left corner of a graphic's bounding box.");
  public static final Place TM = new Place("TM", "The top-middle corner of a graphic's bounding box.");
  public static final Place TR = new Place("TR", "The top-right corner of a graphic's bounding box.");
  public static final Place ML = new Place("ML", "The middle-left corner of a graphic's bounding box.");
  public static final Place MM = new Place("MM", "The middle-middle corner of a graphic's bounding box.");
  public static final Place MR = new Place("MR", "The middle-right corner of a graphic's bounding box.");
  public static final Place BL = new Place("BL", "The bottom-left corner of a graphic's bounding box.");
  public static final Place BM = new Place("BM", "The bottom-middle corner of a graphic's bounding box.");
  public static final Place BR = new Place("BR", "The bottom-right corner of a graphic's bounding box.");
  public static final Place PIN = new Place("PIN", "The location of a graphic's pinhole.");
  public static final Place BAL = new Place("BAL", "The left end of the baseline of a text's bounding box.");
  public static final Place BAM = new Place("BAM", "The middle of the baseline of a text's bounding box.");
  public static final Place BAR = new Place("BAR", "The right end of the baseline of a text's bounding box.");
  public static final Place CENTER = new Place("CENTER", "The center of a triangle or wedge.");
  public static final Place TOP = new Place("TOP", "The top corner of a triangle.");
  public static final Place LEFT = new Place("LEFT", "The left corner of a triangle.");
  public static final Place RIGHT = new Place("RIGHT", "The right corner of a triangle.");
  public static final Place BEGIN = new Place("BEGIN", "The begin of the circular periphery of a wedge.");
  public static final Place END = new Place("END", "The end of the circular periphery of a wedge.");
  
}
