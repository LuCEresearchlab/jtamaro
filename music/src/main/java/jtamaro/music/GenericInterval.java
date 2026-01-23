package jtamaro.music;

/**
 * A generic interval specifies the distance between two notes as the number of staff positions
 * involved (including the staff positions the two notes occupy). A generic interval does not
 * consider accidentals (#, b, ...).
 *
 * <p>They really are numbers, from "firsts" (top and bottom note on same staff position) to
 * "eights" (top and bottom node straddling 8 staff positions, inclusive).
 *
 * <p>For example, C-C, C-C#, Db-D, or even Ab-A# all are FIRSTs, C-D, C#-D, and even F#-Gb all are
 * SECONDs, C-E, D-F, and F#-Ab all are THIRDs.
 *
 * @see <a href="https://www.musictheory.net/lessons/30">Music Theory</a>
 */
public enum GenericInterval {

  FIRST(1, "First"),     // a.k.a. Unison, Prime
  SECOND(2, "Second"),
  THIRD(3, "Third"),
  FOURTH(4, "Fourth"),
  FIFTH(5, "Fifth"),
  SIXTH(6, "Sixth"),
  SEVENTH(7, "Seventh"),
  EIGHTH(8, "Eight"),    // a.k.a. Octave
  ;

  private static final GenericInterval[] INTERVAL_BY_STAFF_POSITIONS = {
      null,
      FIRST,
      SECOND,
      THIRD,
      FOURTH,
      FIFTH,
      SIXTH,
      SEVENTH,
      EIGHTH
  };

  private final int staffPositions;

  private final String name;

  GenericInterval(int staffPositions, String name) {
    this.staffPositions = staffPositions;
    this.name = name;
  }

  public int getStaffPositions() {
    return staffPositions;
  }

  public String getName() {
    return name;
  }

  public GenericInterval invert() {
    return byStaffPositions(9 - staffPositions);
  }

  public boolean isSimple() {
    return staffPositions <= 8;
  }

  public boolean isCompound() {
    return staffPositions > 8;
  }

  public GenericInterval getSimple() {
    return byStaffPositions((staffPositions - 1) % 7 + 1);
  }

  public static GenericInterval byStaffPositions(int staffPositions) {
    assert staffPositions >= 1 && staffPositions <= 8 : "staffPositions must be 1...8";
    return INTERVAL_BY_STAFF_POSITIONS[staffPositions];
  }
}
