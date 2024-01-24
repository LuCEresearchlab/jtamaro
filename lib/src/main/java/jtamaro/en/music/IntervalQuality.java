package jtamaro.en.music;

import java.util.HashMap;


/**
 * https://en.wikipedia.org/wiki/Interval_(music)#Quality
 */
public enum IntervalQuality {

  PERFECT("P", "Perfect"),
  MAJOR("M", "Major"),
  MINOR("m", "Minor"),
  AUGMENTED("A", "Augmented"),
  DIMINISHED("d", "Diminished");

  private final String symbol;
  private final String name;

  private IntervalQuality(String symbol, String name) {
    this.symbol = symbol;
    this.name = name;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getName() {
    return name;
  }

  // https://en.wikipedia.org/wiki/Inversion_(music)#Intervals
  private static final HashMap<IntervalQuality,IntervalQuality> INVERSES;

  static {
    INVERSES = new HashMap<>();
    INVERSES.put(PERFECT, PERFECT);
    INVERSES.put(MAJOR, MINOR);
    INVERSES.put(MINOR, MAJOR);
    INVERSES.put(AUGMENTED, DIMINISHED);
    INVERSES.put(DIMINISHED, AUGMENTED);
  }

  public IntervalQuality invert() {
    return INVERSES.get(this);
  }

  public static void main(String[] args) {
    for (IntervalQuality q : values()) {
      System.out.println(q + " Inverse: " + q.invert());
    }
  }

}
