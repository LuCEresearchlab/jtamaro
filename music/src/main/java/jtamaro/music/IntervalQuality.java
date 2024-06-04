package jtamaro.music;

import java.util.HashMap;

/**
 * https://en.wikipedia.org/wiki/Interval_(music)#Quality https://www.musictheory.net/lessons/31
 */
public enum IntervalQuality {

  DIMINISHED("d",
      "Diminished"), // Firsts, Seconds, Thirds, Fourths, Fifths, Sixths, Sevenths, Eights
  MINOR("m", "Minor"),           // Seconds, Thirds, Sixths, Sevenths
  PERFECT("P", "Perfect"),       // Firsts, Fourths, Fifths, Eights
  MAJOR("M", "Major"),           // Seconds, Thirds, Sixths, Sevenths
  AUGMENTED("A",
      "Augmented");   // Firsts, Seconds, Thirds, Fourths, Fifths, Sixths, Sevenths, Eights

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
  private static final HashMap<IntervalQuality, IntervalQuality> INVERSES;

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
