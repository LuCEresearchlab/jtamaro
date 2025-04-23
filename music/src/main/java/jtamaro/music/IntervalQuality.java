package jtamaro.music;

import java.util.Map;

/**
 * Interval quality.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Interval_(music)#Quality">Interval Quality
 * (Wikipedia)</a>
 * @see <a href="https://www.musictheory.net/lessons/31">Music Theory lesson 31</a>
 */
public enum IntervalQuality {

  // Firsts, Seconds, Thirds, Fourths, Fifths, Sixths, Sevenths, Eights
  DIMINISHED("d", "Diminished"),
  // Seconds, Thirds, Sixths, Sevenths
  MINOR("m", "Minor"),
  // Firsts, Fourths, Fifths, Eights
  PERFECT("P", "Perfect"),
  // Seconds, Thirds, Sixths, Sevenths
  MAJOR("M", "Major"),
  // Firsts, Seconds, Thirds, Fourths, Fifths, Sixths, Sevenths, Eights
  AUGMENTED("A", "Augmented"),
  ;

  // https://en.wikipedia.org/wiki/Inversion_(music)#Intervals
  private static final Map<IntervalQuality, IntervalQuality> INVERSES = Map.of(
      PERFECT, PERFECT,
      MAJOR, MINOR,
      MINOR, MAJOR,
      AUGMENTED, DIMINISHED,
      DIMINISHED, AUGMENTED
  );

  private final String symbol;

  private final String name;

  IntervalQuality(String symbol, String name) {
    this.symbol = symbol;
    this.name = name;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getName() {
    return name;
  }

  public IntervalQuality invert() {
    return INVERSES.get(this);
  }

  public static void demo() {
    for (IntervalQuality q : values()) {
      System.out.println(q + " Inverse: " + q.invert());
    }
  }
}
