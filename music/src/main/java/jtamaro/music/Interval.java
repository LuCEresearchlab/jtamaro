package jtamaro.music;

import java.util.ArrayList;
import jtamaro.data.Sequences;

import static jtamaro.music.GenericInterval.EIGHTH;
import static jtamaro.music.GenericInterval.FIFTH;
import static jtamaro.music.GenericInterval.FIRST;
import static jtamaro.music.GenericInterval.FOURTH;
import static jtamaro.music.GenericInterval.SECOND;
import static jtamaro.music.GenericInterval.SEVENTH;
import static jtamaro.music.GenericInterval.SIXTH;
import static jtamaro.music.GenericInterval.THIRD;
import static jtamaro.music.IntervalQuality.AUGMENTED;
import static jtamaro.music.IntervalQuality.DIMINISHED;
import static jtamaro.music.IntervalQuality.MAJOR;
import static jtamaro.music.IntervalQuality.MINOR;
import static jtamaro.music.IntervalQuality.PERFECT;
import static jtamaro.music.Notes.C4;

/**
 * An unordered pitch interval.
 *
 * https://en.wikipedia.org/wiki/Pitch_interval#Unordered_Pitch_Interval
 * https://en.wikipedia.org/wiki/Interval_(music) https://www.musictheory.net/lessons/31
 * https://viva.pressbooks.pub/openmusictheory/chapter/intervals/
 * https://www.omnicalculator.com/other/music-interval
 * https://www.earmaster.com/music-theory-online/ch04/chapter-4-1.html
 * https://muted.io/circle-of-fifths/
 *
 * Comments: An octave, like C-C, includes 13 semitones (the distance is 12 semitones). An octave,
 * like C-C, includes 8 staff positions (the distance is 7 staff positions) When we talk about
 * semitones, we start at 0 (we exclude the start pitch). When we talk about staff positions, we
 * start at 1 (we include the start pitch). So, an octave, a PERFECT_EIGHT, spans 8 staff positions
 * (inclusive) and is 12 semitones wide (exclusive).
 */
public record Interval(int semitones, IntervalQuality quality, GenericInterval genericInterval) {

  public static final Interval PERFECT_UNISON = new Interval(0, PERFECT, FIRST);

  public static final Interval DIMINISHED_SECOND = new Interval(0, DIMINISHED, SECOND);

  public static final Interval MINOR_SECOND = new Interval(1, MINOR, SECOND);

  public static final Interval AUGMENTED_UNISON = new Interval(1, AUGMENTED, FIRST);

  public static final Interval MAJOR_SECOND = new Interval(2, MAJOR, SECOND);

  public static final Interval DIMINISHED_THIRD = new Interval(2, DIMINISHED, THIRD);

  public static final Interval MINOR_THIRD = new Interval(3, MINOR, THIRD);

  public static final Interval AUGMENTED_SECOND = new Interval(3, AUGMENTED, SECOND);

  public static final Interval MAJOR_THIRD = new Interval(4, MAJOR, THIRD);

  public static final Interval DIMINISHED_FOURTH = new Interval(4, DIMINISHED, FOURTH);

  public static final Interval PERFECT_FOURTH = new Interval(5, PERFECT, FOURTH);

  public static final Interval AUGMENTED_THIRD = new Interval(5, AUGMENTED, THIRD);

  public static final Interval AUGMENTED_FOURTH = new Interval(6, AUGMENTED, FOURTH);

  public static final Interval DIMINISHED_FIFTH = new Interval(6, DIMINISHED, FIFTH);

  public static final Interval PERFECT_FIFTH = new Interval(7, PERFECT, FIFTH);

  public static final Interval DIMINISHED_SIXTH = new Interval(7, DIMINISHED, SIXTH);

  public static final Interval MINOR_SIXTH = new Interval(8, MINOR, SIXTH);

  public static final Interval AUGMENTED_FIFTH = new Interval(8, AUGMENTED, FIFTH);

  public static final Interval MAJOR_SIXTH = new Interval(9, MAJOR, SIXTH);

  public static final Interval DIMINISHED_SEVENTH = new Interval(9, DIMINISHED, SEVENTH);

  public static final Interval MINOR_SEVENTH = new Interval(10, MINOR, SEVENTH);

  public static final Interval AUGMENTED_SIXTH = new Interval(10, AUGMENTED, SIXTH);

  public static final Interval MAJOR_SEVENTH = new Interval(11, MAJOR, SEVENTH);

  public static final Interval DIMINISHED_OCTAVE = new Interval(11, DIMINISHED, EIGHTH);

  public static final Interval PERFECT_OCTAVE = new Interval(12, PERFECT, EIGHTH);

  public static final Interval AUGMENTED_SEVENTH = new Interval(12, AUGMENTED, SEVENTH);

  public static final Interval AUGMENTED_OCTAVE = new Interval(13, AUGMENTED, EIGHTH);


  public final static ArrayList<Interval> INTERVALS = new ArrayList<>();

  static {
    INTERVALS.add(PERFECT_UNISON);
    INTERVALS.add(DIMINISHED_SECOND);
    INTERVALS.add(MINOR_SECOND);
    INTERVALS.add(AUGMENTED_UNISON);
    INTERVALS.add(MAJOR_SECOND);
    INTERVALS.add(DIMINISHED_THIRD);
    INTERVALS.add(MINOR_THIRD);
    INTERVALS.add(AUGMENTED_SECOND);
    INTERVALS.add(MAJOR_THIRD);
    INTERVALS.add(DIMINISHED_FOURTH);
    INTERVALS.add(PERFECT_FOURTH);
    INTERVALS.add(AUGMENTED_THIRD);
    INTERVALS.add(AUGMENTED_FOURTH);
    INTERVALS.add(DIMINISHED_FIFTH);
    INTERVALS.add(PERFECT_FIFTH);
    INTERVALS.add(DIMINISHED_SIXTH);
    INTERVALS.add(MINOR_SIXTH);
    INTERVALS.add(AUGMENTED_FIFTH);
    INTERVALS.add(MAJOR_SIXTH);
    INTERVALS.add(DIMINISHED_SEVENTH);
    INTERVALS.add(MINOR_SEVENTH);
    INTERVALS.add(AUGMENTED_SIXTH);
    INTERVALS.add(MAJOR_SEVENTH);
    INTERVALS.add(DIMINISHED_OCTAVE);
    INTERVALS.add(PERFECT_OCTAVE);
    INTERVALS.add(AUGMENTED_SEVENTH);
    INTERVALS.add(AUGMENTED_OCTAVE);
  }

  public String toString() {
    return getShortName() + " (" + getLongName() + ")";
  }

  public String getShortName() {
    return quality.getSymbol() + genericInterval.getStaffPositions();
  }

  public String getLongName() {
    return quality.getName() + " " + genericInterval().getName();
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
   * Produce a Chord representing this Interval. The Chord's name and symbol correspond to this
   * Interval's long and short name.
   *
   * @return a Chord for this Interval
   */
  public Chord chord() {
    return new Chord(Sequences.of(PERFECT_UNISON, this), getLongName(), getShortName());
  }

  /**
   * Produce an AbsoluteChord for this interval, based on the given root Note.
   *
   * @param root the lower Note of the Chord
   */
  public AbsoluteChord dyad(Note root) {
    return new AbsoluteChord(Sequences.of(root, transpose(root)));
  }

  /**
   * Produce an Interval that is the inversion of this Interval.
   *
   * @return the inverted Interval
   * @see <a href="https://en.wikipedia.org/wiki/Inversion_(music)#Intervals">Wikipedia:
   * Inversion</a>
   */
  public Interval invert() {
    Interval simple = getSimple(); // Get simple interval from which this is compounded
    int s = 12 - simple.semitones;
    IntervalQuality q = simple.quality.invert();
    GenericInterval gi = simple.genericInterval.invert();
    return new Interval(s, q, gi);
  }

  public boolean isSimple() {
    return semitones <= PERFECT_OCTAVE.semitones;
  }

  public boolean isCompound() {
    return semitones > PERFECT_OCTAVE.semitones;
  }

  public Interval getSimple() {
    // TODO: Check that this is correct!
    return isSimple() ? this : new Interval(semitones % 12, quality, genericInterval.getSimple());
  }

  public static Interval get(GenericInterval genericInterval, IntervalQuality quality) {
    for (Interval i : INTERVALS) {
      if (i.quality() == quality && i.genericInterval() == genericInterval) {
        return i;
      }
    }
    return null;
  }

  // Table shown in the last step of this page:
  // https://www.musictheory.net/lessons/31
  private static void printIntervalTable() {
    System.out.print('\t');
    for (IntervalQuality q : IntervalQuality.values()) {
      System.out.print(q.getName() + '\t');
    }
    System.out.println();
    for (GenericInterval gi : GenericInterval.values()) {
      System.out.print(gi.getName() + '\t');
      for (IntervalQuality q : IntervalQuality.values()) {
        Interval i = get(gi, q);
        //String cell = i==null ? "" : i.getShortName();
        String cell = i == null ? "" : "" + i.semitones();
        System.out.print(cell + '\t');
      }
      System.out.println();
    }
  }

  private static void printInversionTable() {
    System.out.println("Interval\tInversion\t");
    for (Interval interval : INTERVALS) {
      System.out.println(interval + "\t" + interval.invert());
    }
  }

  private static void playIntervals() {
    final Note root = C4;
    for (Interval interval : INTERVALS) {
      MusicIO.playChords(Sequences.of(
          interval.dyad(root),
          interval.invert().dyad(root)
      ));
    }
  }

  public static void main(String[] args) {
    System.out.println("Number of semitones for the different specific intervals:");
    printIntervalTable();
    System.out.println("All intervals with their intervallic inversions:");
    printInversionTable();
  }

}
