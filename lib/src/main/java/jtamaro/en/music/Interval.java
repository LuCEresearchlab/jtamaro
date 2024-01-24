package jtamaro.en.music;

import static jtamaro.en.Sequences.of;
import static jtamaro.en.IO.*;
import static jtamaro.en.music.IntervalQuality.*;
import static jtamaro.en.music.Notes.C4;

import java.util.ArrayList;


/**
 * An unordered pitch interval.
 * 
 * https://en.wikipedia.org/wiki/Pitch_interval#Unordered_Pitch_Interval
 * https://en.wikipedia.org/wiki/Interval_(music)
 */
public record Interval(int semitones, IntervalQuality quality, int number) {
  
  private final static String[] NUMBER_NAMES = {
    null,
    "unison",
    "second",
    "third",
    "fourth",
    "fifth",
    "sixth",
    "seventh",
    "octave"
  };

  
  public static final Interval PERFECT_UNISON = new Interval(0, PERFECT, 1);
  public static final Interval DIMINISHED_SECOND = new Interval(0, DIMINISHED, 2);
  public static final Interval MINOR_SECOND = new Interval(1, MINOR, 2);
  public static final Interval AUGMENTED_UNISON = new Interval(1, AUGMENTED, 1);
  public static final Interval MAJOR_SECOND = new Interval(2, MAJOR, 2);
  public static final Interval DIMINISHED_THIRD = new Interval(2, DIMINISHED, 3);
  public static final Interval MINOR_THIRD = new Interval(3, MINOR, 3);
  public static final Interval AUGMENTED_SECOND = new Interval(3, AUGMENTED, 2);
  public static final Interval MAJOR_THIRD = new Interval(4, MAJOR, 3);
  public static final Interval DIMINISHED_FOURTH = new Interval(4, DIMINISHED, 4);
  public static final Interval PERFECT_FOURTH = new Interval(5, PERFECT, 4);
  public static final Interval AUGMENTED_THIRD = new Interval(5, AUGMENTED, 3);
  public static final Interval AUGMENTED_FOURTH = new Interval(6, AUGMENTED, 4);
  public static final Interval DIMINISHED_FIFTH = new Interval(6, DIMINISHED, 5);
  public static final Interval PERFECT_FIFTH = new Interval(7, PERFECT, 5);
  public static final Interval DIMINISHED_SIXTH = new Interval(7, DIMINISHED, 6);
  public static final Interval MINOR_SIXTH = new Interval(8, MINOR, 6);
  public static final Interval AUGMENTED_FIFTH = new Interval(8, AUGMENTED, 5);
  public static final Interval MAJOR_SIXTH = new Interval(9, MAJOR, 6);
  public static final Interval DIMINISHED_SEVENTH = new Interval(9, DIMINISHED, 7);
  public static final Interval MINOR_SEVENTH = new Interval(10, MINOR, 7);
  public static final Interval AUGMENTED_SIXTH = new Interval(10, AUGMENTED, 6);
  public static final Interval MAJOR_SEVENTH = new Interval(11, MAJOR, 7);
  public static final Interval DIMINISHED_OCTAVE = new Interval(11, DIMINISHED, 8);
  public static final Interval PERFECT_OCTAVE = new Interval(12, PERFECT, 8);
  public static final Interval AUGMENTED_SEVENTH = new Interval(12, AUGMENTED, 7);
  
  public static final Interval AUGMENTED_OCTAVE = new Interval(13, AUGMENTED, 8);
  
  public final static ArrayList<Interval> INTERVALS = new ArrayList<>();
  static {
    INTERVALS.add(PERFECT_UNISON);
    INTERVALS.add(DIMINISHED_SECOND);
    INTERVALS.add(MINOR_SECOND);
    //...
  }

  /**
   * Transpose the given Note by this Interval.
   * TODO: Should notes include sharp/flat quality that is affected by transpose?
   * 
   * @param note the Note to transpose
   * @return a Node that is transposed
   */
  public Note transpose(Note note) {
    return note.transpose(semitones);
  }

  /**
   * Produce a Chord representing this Interval.
   * The Chord's name and symbol correspond to this Interval's long and short name.
   * 
   * @return a Chord for this Interval
   */
  public Chord chord() {
    return new Chord(of(PERFECT_UNISON, this), getLongName(), getShortName());
  }

  /**
   * Produce an AbsoluteChord for this interval, based on the given root Note.
   * 
   * @param root the lower Note of the Chord
   * @return
   */
  public AbsoluteChord dyad(Note root) {
    return new AbsoluteChord(of(root, transpose(root)));
  }

  /**
   * Produce an Interval that is the inversion of this Interval.
   * 
   * @return the inverted Interval
   * 
   * @see <a href="https://en.wikipedia.org/wiki/Inversion_(music)#Intervals">Wikipedia: Inversion</a>
   */
  public Interval invert() {
    Interval simple = getSimple(); // Get simple interval from which this is compounded
    int s = 12 - simple.semitones;
    IntervalQuality q = simple.quality.invert();
    int n = 9 - simple.number;
    return new Interval(s, q, n);
  }

  public String toString() {
    return getShortName() + " (" + getLongName() + ")";
  }

  public String getShortName() {
    return quality.getSymbol() + number;
  }

  public String getLongName() {
    return quality.getName() + " " + NUMBER_NAMES[number];
  }

  public boolean isSimple() {
    return semitones <= PERFECT_OCTAVE.semitones;
  }

  public boolean isCompound() {
    return semitones > PERFECT_OCTAVE.semitones;
  }

  public Interval getSimple() {
    // TODO: Check that this is correct!
    return isSimple() ? this : new Interval(semitones % 12, quality, number);
  }

  public static void main(String[] args) {
    Note root = C4;
    for (Interval interval : INTERVALS) {
      System.out.println(interval + " Inverse: " + interval.invert());
      playChords(of(
        interval.dyad(root),
        interval.invert().dyad(root)
      ));
    }
  }

}
