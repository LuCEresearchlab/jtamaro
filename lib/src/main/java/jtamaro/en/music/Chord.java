package jtamaro.en.music;

import static jtamaro.en.Sequences.*;
import static jtamaro.en.IO.*;
import static jtamaro.en.Music.chord;
import static jtamaro.en.music.Interval.*;
import static jtamaro.en.music.Notes.*;

import java.util.ArrayList;

import jtamaro.en.Sequence;


// https://en.wikipedia.org/wiki/Chord_(music)#Notation_in_popular_music
public record Chord(Sequence<Interval> intervals, String name, String symbol) {

  public AbsoluteChord on(Note root) {
    return new AbsoluteChord(map(i->i.transpose(root), intervals));
  }

  // https://en.wikipedia.org/wiki/Chord_(music)#Notation_in_popular_music
  // https://en.wikipedia.org/wiki/Interval_(music)#Deducing_component_intervals_from_chord_names_and_symbols
  // https://en.wikipedia.org/wiki/Chord_notation#Rules_to_decode_chord_names_and_symbols
  // https://en.wikipedia.org/wiki/Chord_notation#Common_types_of_chords

  // Triads
  // https://en.wikipedia.org/wiki/Major_chord
  public static Chord MAJOR_TRIAD = new Chord(of(PERFECT_UNISON, MAJOR_THIRD, PERFECT_FIFTH), "Major triad", "maj");
  // https://en.wikipedia.org/wiki/Minor_chord
  public static Chord MINOR_TRIAD = new Chord(of(PERFECT_UNISON, MINOR_THIRD, PERFECT_FIFTH), "Minor triad", "min");
  // https://en.wikipedia.org/wiki/Augmented_triad
  public static Chord AUGMENTED_TRIAD = new Chord(of(PERFECT_UNISON, MAJOR_THIRD, AUGMENTED_FIFTH), "Augmented triad", "aug");
  // https://en.wikipedia.org/wiki/Diminished_triad
  public static Chord DIMINISHED_TRIAD = new Chord(of(PERFECT_UNISON, MINOR_THIRD, DIMINISHED_FIFTH), "Diminished triad", "dim");

  // Seventh Chords
  // https://en.wikipedia.org/wiki/Dominant_seventh_chord
  public static Chord DOMINANT_SEVENTH_CHORD = new Chord(of(PERFECT_UNISON, MAJOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH), "Dominant seventh chord", "^7");
  // https://en.wikipedia.org/wiki/Major_seventh_chord
  public static Chord MAJOR_SEVENTH_CHORD = new Chord(of(PERFECT_UNISON, MAJOR_THIRD, PERFECT_FIFTH, MAJOR_SEVENTH), "Major seventh chord", "maj^7");
  // https://en.wikipedia.org/wiki/Minor_major_seventh_chord
  public static Chord MINOR_MAJOR_SEVENTH_CHORD = new Chord(of(PERFECT_UNISON, MINOR_THIRD, PERFECT_FIFTH, MAJOR_SEVENTH), "Minor major seventh chord", "min^maj7");
  // https://en.wikipedia.org/wiki/Minor_seventh_chord
  public static Chord MINOR_SEVENTH_CHORD = new Chord(of(PERFECT_UNISON, MINOR_THIRD, PERFECT_FIFTH, MINOR_SEVENTH), "Minor seventh chord", "min^7");
  // https://en.wikipedia.org/wiki/Augmented_major_seventh_chord
  public static Chord AUGMENTED_MAJOR_SEVENTH_CHORD = new Chord(of(PERFECT_UNISON, MAJOR_THIRD, AUGMENTED_FIFTH, MAJOR_SEVENTH), "Augmented major seventh chord", "aug^maj7");
  // https://en.wikipedia.org/wiki/Augmented_seventh_chord
  public static Chord AUGMENTED_SEVENTH_CHORD = new Chord(of(PERFECT_UNISON, MAJOR_THIRD, AUGMENTED_FIFTH, MINOR_SEVENTH), "Augmented seventh chord", "aug^7");
  // https://en.wikipedia.org/wiki/Half-diminished_seventh_chord
  public static Chord HALF_DIMINISHED_SEVENTH_CHORD = new Chord(of(PERFECT_UNISON, MINOR_THIRD, DIMINISHED_FIFTH, MINOR_SEVENTH), "Half-diminished seventh chord", "min^7dim5");
  // https://en.wikipedia.org/wiki/Diminished_seventh_chord
  public static Chord DIMINISHED_SEVENTH_CHORD = new Chord(of(PERFECT_UNISON, MINOR_THIRD, DIMINISHED_FIFTH, DIMINISHED_SEVENTH), "Diminished seventh chord", "dim^7");
  // https://en.wikipedia.org/wiki/Dominant_seventh_flat_five_chord
  public static Chord DOMINANT_SEVENTH_FLAT_FIVE_CHORD = new Chord(of(PERFECT_UNISON, MAJOR_THIRD, DIMINISHED_FIFTH, MINOR_SEVENTH), "Dominant seventh flat five chord", "^7dim5");

  // Sixth Chords
  // https://en.wikipedia.org/wiki/Sixth_chord
  public static Chord MAJOR_SIXTH_CHORD = new Chord(of(PERFECT_UNISON, MAJOR_THIRD, PERFECT_FIFTH, MAJOR_SIXTH), "Major sixth chord", "^6");
  public static Chord MINOR_SIXTH_CHORD = new Chord(of(PERFECT_UNISON, MINOR_THIRD, PERFECT_FIFTH, MAJOR_SIXTH), "Minor sixth chord", "min^maj6");


  public final static ArrayList<Chord> CHORDS = new ArrayList<>();

  static {
    CHORDS.add(MAJOR_TRIAD);
    CHORDS.add(MINOR_TRIAD);
    CHORDS.add(AUGMENTED_TRIAD);
    CHORDS.add(DIMINISHED_TRIAD);
    CHORDS.add(DOMINANT_SEVENTH_CHORD);
    CHORDS.add(MAJOR_SEVENTH_CHORD);
    CHORDS.add(MINOR_MAJOR_SEVENTH_CHORD);
    CHORDS.add(MINOR_SEVENTH_CHORD);
    CHORDS.add(AUGMENTED_MAJOR_SEVENTH_CHORD);
    CHORDS.add(AUGMENTED_SEVENTH_CHORD);
    CHORDS.add(HALF_DIMINISHED_SEVENTH_CHORD);
    CHORDS.add(DIMINISHED_SEVENTH_CHORD);
    CHORDS.add(DOMINANT_SEVENTH_FLAT_FIVE_CHORD);
    CHORDS.add(MAJOR_SIXTH_CHORD);
    CHORDS.add(MINOR_SIXTH_CHORD);
  }


  public static void main(String[] args) {
    Note root = C4;
    for (Chord ch : CHORDS) {
      System.out.println(ch);
      AbsoluteChord absoluteChord = ch.on(root);
      Sequence<AbsoluteChord> seq = map(n -> chord(of(n)), absoluteChord.notes());
      playChords(cons(absoluteChord, seq), 80);
    }
  }

}
