package jtamaro.music;

import jtamaro.data.Sequence;
import jtamaro.data.Sequences;

import static jtamaro.music.Chord.AUGMENTED_TRIAD;
import static jtamaro.music.Chord.DIMINISHED_TRIAD;
import static jtamaro.music.Chord.MAJOR_TRIAD;
import static jtamaro.music.Chord.MINOR_TRIAD;
import static jtamaro.music.Scales.C_MAJOR;

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

  public static void demo() {
    // https://en.wikipedia.org/wiki/Roman_numeral_analysis#Major_scale
    //play(C_MAJOR, of(I_CHORD, ii_CHORD, iii_CHORD, IV_CHORD, V_CHORD, vi_CHORD, vii_DIM_CHORD));

    // https://en.wikipedia.org/wiki/Roman_numeral_analysis#Minor_scale
    // This progression on the minor scale is strange:
    //play(C_MINOR, of(i_CHORD, ii_DIM_CHORD, III_AUG_CHORD, iv_CHORD, V_CHORD, VI_CHORD, vii_DIM_CHORD));

    // https://blog.landr.com/common-chord-progressions/
    // 1
    //play(C_MAJOR, of(I_CHORD, V_CHORD, vi_CHORD, IV_CHORD));
    // 2
    //play(C_MAJOR, of(I_CHORD, IV_CHORD, V_CHORD, IV_CHORD));
    // 3
    // play(C_MAJOR, of(
    //   new RelativeChord(2, MINOR_SEVENTH_CHORD, "ii-7"),
    //   new RelativeChord(5, DOMINANT_SEVENTH_CHORD, "V7"),
    //   new RelativeChord(1, MAJOR_SEVENTH_CHORD, "IΔ7")
    // ));
    // 4 - 12 Bar Blues
    // play(C_MAJOR, of(
    //   I_CHORD, I_CHORD, I_CHORD, I_CHORD,
    //   IV_CHORD, IV_CHORD, I_CHORD, I_CHORD,
    //   V_CHORD, IV_CHORD, I_CHORD, I_CHORD
    // ));
    // 5
    //play(C_MAJOR, of(I_CHORD, vi_CHORD, IV_CHORD, V_CHORD));
    // 6
    play(C_MAJOR, Sequences.of(
        I_CHORD, V_CHORD, vi_CHORD, iii_CHORD,
        IV_CHORD, I_CHORD, IV_CHORD, V_CHORD
    ));
    // 7
    //play(C_MAJOR, of(I_CHORD, new RelativeChord(0, ???, "bVII"), I_CHORD));
  }

  private static void play(Scale scale, Sequence<RelativeChord> cs) {
    Sequence<AbsoluteChord> acs = cs.map(scale::in);
    MusicIO.playChords(acs, 60);
  }
}
