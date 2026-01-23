package jtamaro.music;

import java.util.List;
import jtamaro.data.Sequence;
import jtamaro.data.Sequences;

import static jtamaro.music.Interval.AUGMENTED_FIFTH;
import static jtamaro.music.Interval.DIMINISHED_FIFTH;
import static jtamaro.music.Interval.DIMINISHED_SEVENTH;
import static jtamaro.music.Interval.MAJOR_SEVENTH;
import static jtamaro.music.Interval.MAJOR_SIXTH;
import static jtamaro.music.Interval.MAJOR_THIRD;
import static jtamaro.music.Interval.MINOR_SEVENTH;
import static jtamaro.music.Interval.MINOR_THIRD;
import static jtamaro.music.Interval.PERFECT_FIFTH;
import static jtamaro.music.Interval.PERFECT_UNISON;

/**
 * Chord.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Chord_(music)#Notation_in_popular_music">Chord</a>
 * @see <a
 * href="https://en.wikipedia.org/wiki/Interval_(music)#Deducing_component_intervals_from_chord_names_and_symbols">
 * Deducing component intervals from chord names and symbols</a>
 * @see <a
 * href="https://en.wikipedia.org/wiki/Chord_notation#Rules_to_decode_chord_names_and_symbols">
 * Rules to decode chord names and symbols</a>
 * @see <a href="https://en.wikipedia.org/wiki/Chord_notation#Common_types_of_chords">
 * Common types of chords</a>
 */

public record Chord(Sequence<Interval> intervals, String name, String symbol) {

  // Triads

  /**
   * Major triad chord.
   *
   * <p><a href="https://en.wikipedia.org/wiki/Major_chord">Wikipedia</a>
   */
  public static final Chord MAJOR_TRIAD = new Chord(
      Sequences.of(PERFECT_UNISON, MAJOR_THIRD, PERFECT_FIFTH),
      "Major triad",
      "maj"
  );

  /**
   * Minor triad chord.
   *
   * <p><a href="https://en.wikipedia.org/wiki/Minor_chord">Wikipedia</a>
   */
  public static final Chord MINOR_TRIAD = new Chord(
      Sequences.of(PERFECT_UNISON, MINOR_THIRD, PERFECT_FIFTH),
      "Minor triad",
      "min"
  );

  /**
   * Augmented triad.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Augmented_triad">Wikipedia</a>
   */
  public static final Chord AUGMENTED_TRIAD = new Chord(
      Sequences.of(PERFECT_UNISON, MAJOR_THIRD, AUGMENTED_FIFTH),
      "Augmented triad",
      "aug"
  );

  /**
   * Diminished triad.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Diminished_triad">Wikipedia</a>
   */
  public static final Chord DIMINISHED_TRIAD = new Chord(
      Sequences.of(PERFECT_UNISON, MINOR_THIRD, DIMINISHED_FIFTH),
      "Diminished triad",
      "dim"
  );

  // Seventh Chords

  /**
   * Dominant 7th chord.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Dominant_seventh_chord">Wikipedia</a>
   */
  public static final Chord DOMINANT_SEVENTH_CHORD = new Chord(
      Sequences.of(PERFECT_UNISON, MAJOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH),
      "Dominant seventh chord",
      "^7"
  );

  /**
   * Major 7th cord.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Major_seventh_chord">Wikipedia</a>
   */
  public static final Chord MAJOR_SEVENTH_CHORD = new Chord(
      Sequences.of(PERFECT_UNISON, MAJOR_THIRD, PERFECT_FIFTH, MAJOR_SEVENTH),
      "Major seventh chord",
      "maj^7"
  );

  /**
   * Minor major 7th chord.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Minor_major_seventh_chord">Wikipedia</a>
   */
  public static final Chord MINOR_MAJOR_SEVENTH_CHORD = new Chord(
      Sequences.of(PERFECT_UNISON, MINOR_THIRD, PERFECT_FIFTH, MAJOR_SEVENTH),
      "Minor major seventh chord",
      "min^maj7"
  );

  /**
   * Minor 7th chord.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Minor_seventh_chord">Wikipedia</a>
   */
  public static final Chord MINOR_SEVENTH_CHORD = new Chord(
      Sequences.of(PERFECT_UNISON, MINOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH),
      "Minor seventh chord",
      "min^7"
  );

  /**
   * Augmented major 7th chord.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Augmented_major_seventh_chord">Wikipedia</a>
   */
  public static final Chord AUGMENTED_MAJOR_SEVENTH_CHORD = new Chord(
      Sequences.of(PERFECT_UNISON, MAJOR_THIRD, AUGMENTED_FIFTH, MAJOR_SEVENTH),
      "Augmented major seventh chord",
      "aug^maj7"
  );

  /**
   * Augmented 7th chord.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Augmented_seventh_chord">Wikipedia</a>
   */
  public static final Chord AUGMENTED_SEVENTH_CHORD = new Chord(
      Sequences.of(PERFECT_UNISON, MAJOR_THIRD, AUGMENTED_FIFTH, MINOR_SEVENTH),
      "Augmented seventh chord",
      "aug^7"
  );

  /**
   * Half diminished 7th chord.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Half-diminished_seventh_chord">Wikipedia</a>
   */
  public static final Chord HALF_DIMINISHED_SEVENTH_CHORD = new Chord(
      Sequences.of(PERFECT_UNISON, MINOR_THIRD, DIMINISHED_FIFTH, MINOR_SEVENTH),
      "Half-diminished seventh chord",
      "min^7dim5"
  );

  /**
   * Diminished 7th chord.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Diminished_seventh_chord">Wikipedia</a>
   */
  public static final Chord DIMINISHED_SEVENTH_CHORD = new Chord(
      Sequences.of(PERFECT_UNISON, MINOR_THIRD, DIMINISHED_FIFTH, DIMINISHED_SEVENTH),
      "Diminished seventh chord",
      "dim^7"
  );

  /**
   * Dominant 7th flat 5 chord.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Dominant_seventh_flat_five_chord">Wikipedia</a>
   */
  public static final Chord DOMINANT_SEVENTH_FLAT_FIVE_CHORD = new Chord(
      Sequences.of(PERFECT_UNISON, MAJOR_THIRD, DIMINISHED_FIFTH, MINOR_SEVENTH),
      "Dominant seventh flat five chord",
      "^7dim5"
  );

  // Sixth Chords

  /**
   * Major 6th chord.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Sixth_chord">Wikipedia</a>
   */
  public static final Chord MAJOR_SIXTH_CHORD = new Chord(
      Sequences.of(PERFECT_UNISON, MAJOR_THIRD, PERFECT_FIFTH, MAJOR_SIXTH),
      "Major sixth chord",
      "^6"
  );

  /**
   * Minor 6th chord.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Sixth_chord">Wikipedia</a>
   */
  public static final Chord MINOR_SIXTH_CHORD = new Chord(
      Sequences.of(PERFECT_UNISON, MINOR_THIRD, PERFECT_FIFTH, MAJOR_SIXTH),
      "Minor sixth chord",
      "min^maj6"
  );

  public static final List<Chord> CHORDS = List.of(
      MAJOR_TRIAD,
      MINOR_TRIAD,
      AUGMENTED_TRIAD,
      DIMINISHED_TRIAD,
      DOMINANT_SEVENTH_CHORD,
      MAJOR_SEVENTH_CHORD,
      MINOR_MAJOR_SEVENTH_CHORD,
      MINOR_SEVENTH_CHORD,
      AUGMENTED_MAJOR_SEVENTH_CHORD,
      AUGMENTED_SEVENTH_CHORD,
      HALF_DIMINISHED_SEVENTH_CHORD,
      DIMINISHED_SEVENTH_CHORD,
      DOMINANT_SEVENTH_FLAT_FIVE_CHORD,
      MAJOR_SIXTH_CHORD,
      MINOR_SIXTH_CHORD
  );

  public AbsoluteChord on(Note root) {
    return new AbsoluteChord(intervals.map(i -> i.transpose(root)));
  }
}
