package jtamaro.music;

/**
 * Octave.
 *
 * <p>Octaves middle C: SPN: C4, Helmholz: c' A440: SPN: A4, Helmholz: a' C-1 = C,,, = Double
 * Contra C0 = C,, = Sub Contra C1 = C, = Contra C2 = C, = Great C3 = c, = Small C4 = c' = 1 Line C5
 * = c'' = 2 Line C6 = c''' = 3 Line C7 = c'''' = 4 Line C8 = c''''' = 5 Line C9 = c'''''' = 6 Line
 *
 * @see <a href="https://en.wikipedia.org/wiki/Scientific_pitch_notation">Scientific Pitch Notation
 * C0, F♯4, D♭6</a>
 * @see <a href="https://en.wikipedia.org/wiki/Helmholtz_pitch_notation">Helmholtz pitch notation
 * f♯'</a>
 */
public record Octave(int number, boolean helmholzSmall, String helmholzSuffix, String name) {

  public static final Interval INTERVAL = Interval.PERFECT_OCTAVE;

  public static final Octave OCTAVE_M1 = new Octave(-1, false, ",,,", "Double Contra");

  public static final Octave OCTAVE_0 = new Octave(0, false, ",,", "Sub Contra");

  public static final Octave OCTAVE_1 = new Octave(1, false, ",", "Contra");

  public static final Octave OCTAVE_2 = new Octave(2, false, "", "Great");

  public static final Octave OCTAVE_3 = new Octave(3, true, "", "Small");

  public static final Octave OCTAVE_4 = new Octave(4, true, "'", "1 Line");

  public static final Octave OCTAVE_5 = new Octave(4, true, "'", "2 Line");

  public static final Octave OCTAVE_6 = new Octave(4, true, "'", "3 Line");

  public static final Octave OCTAVE_7 = new Octave(4, true, "'", "4 Line");

  public static final Octave OCTAVE_8 = new Octave(4, true, "'", "5 Line");

  public static final Octave OCTAVE_9 = new Octave(4, true, "'", "6 Line");

  private static final Octave[] OCTAVES = new Octave[11];

  public static Octave get(int number) {
    return OCTAVES[number + 1];
  }

  public Octave {
    OCTAVES[number + 1] = this;
  }
}
