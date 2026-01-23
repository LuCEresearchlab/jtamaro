package jtamaro.music;

import static jtamaro.music.Chord.AUGMENTED_TRIAD;
import static jtamaro.music.Chord.DIMINISHED_TRIAD;
import static jtamaro.music.Chord.MAJOR_TRIAD;
import static jtamaro.music.Chord.MINOR_TRIAD;

/**
 * A chord like they are used in chord progressions, with a scale step (usually shown as a Roman
 * numeral) and an underlying chord (e.g., a Major triad).
 *
 * <p>TODO: Not at all sure this is right.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Chord_(music)#Roman_numerals">Wikipedia: Chord</a>
 * @see <a href="https://en.wikipedia.org/wiki/Roman_numeral_analysis">Wikipedia: Roman numeral
 * analysis</a>
 */
public record RelativeChord(int scaleStep, Chord chord, String symbol) {

  public static final RelativeChord I_CHORD = new RelativeChord(1, MAJOR_TRIAD, "I");

  public static final RelativeChord II_CHORD = new RelativeChord(2, MAJOR_TRIAD, "II");

  public static final RelativeChord III_CHORD = new RelativeChord(3, MAJOR_TRIAD, "III");

  public static final RelativeChord IV_CHORD = new RelativeChord(4, MAJOR_TRIAD, "IV");

  public static final RelativeChord V_CHORD = new RelativeChord(5, MAJOR_TRIAD, "V");

  public static final RelativeChord VI_CHORD = new RelativeChord(6, MAJOR_TRIAD, "VI");

  public static final RelativeChord VII_CHORD = new RelativeChord(7, MAJOR_TRIAD, "VII");

  public static final RelativeChord i_CHORD = new RelativeChord(1, MINOR_TRIAD, "i");

  public static final RelativeChord ii_CHORD = new RelativeChord(2, MINOR_TRIAD, "ii");

  public static final RelativeChord iii_CHORD = new RelativeChord(3, MINOR_TRIAD, "iii");

  public static final RelativeChord iv_CHORD = new RelativeChord(4, MINOR_TRIAD, "iv");

  public static final RelativeChord v_CHORD = new RelativeChord(5, MINOR_TRIAD, "v");

  public static final RelativeChord vi_CHORD = new RelativeChord(6, MINOR_TRIAD, "vi");

  public static final RelativeChord vii_CHORD = new RelativeChord(7, MINOR_TRIAD, "vii");

  public static final RelativeChord I_AUG_CHORD = new RelativeChord(1, AUGMENTED_TRIAD, "I^+");

  public static final RelativeChord II_AUG_CHORD = new RelativeChord(2, AUGMENTED_TRIAD, "II^+");

  public static final RelativeChord III_AUG_CHORD = new RelativeChord(3, AUGMENTED_TRIAD, "III^+");

  public static final RelativeChord IV_AUG_CHORD = new RelativeChord(4, AUGMENTED_TRIAD, "IV^+");

  public static final RelativeChord V_AUG_CHORD = new RelativeChord(5, AUGMENTED_TRIAD, "V^+");

  public static final RelativeChord VI_AUG_CHORD = new RelativeChord(6, AUGMENTED_TRIAD, "VI^+");

  public static final RelativeChord VII_AUG_CHORD = new RelativeChord(7, AUGMENTED_TRIAD, "VII^+");

  public static final RelativeChord i_DIM_CHORD = new RelativeChord(1, DIMINISHED_TRIAD, "i^o");

  public static final RelativeChord ii_DIM_CHORD = new RelativeChord(2, DIMINISHED_TRIAD, "ii^o");

  public static final RelativeChord iii_DIM_CHORD = new RelativeChord(3, DIMINISHED_TRIAD, "iii^o");

  public static final RelativeChord iv_DIM_CHORD = new RelativeChord(4, DIMINISHED_TRIAD, "iv^o");

  public static final RelativeChord v_DIM_CHORD = new RelativeChord(5, DIMINISHED_TRIAD, "v^o");

  public static final RelativeChord vi_DIM_CHORD = new RelativeChord(6, DIMINISHED_TRIAD, "vi^o");

  public static final RelativeChord vii_DIM_CHORD = new RelativeChord(7, DIMINISHED_TRIAD, "vii^o");
}
