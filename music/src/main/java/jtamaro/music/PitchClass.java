package jtamaro.music;

import jtamaro.data.Sequences;

import static jtamaro.music.Chord.MAJOR_TRIAD;
import static jtamaro.music.Chord.MINOR_TRIAD;
import static jtamaro.music.Notes.note;
import static jtamaro.music.Octave.OCTAVE_4;


/**
 * https://en.wikipedia.org/wiki/Pitch_class
 */
public record PitchClass(int number, String symbol) {

  public static final PitchClass C = new PitchClass(0, "C");

  public static final PitchClass CS = new PitchClass(1, "C#");

  public static final PitchClass D = new PitchClass(2, "D");

  public static final PitchClass DS = new PitchClass(3, "D#");

  public static final PitchClass E = new PitchClass(4, "E");

  public static final PitchClass F = new PitchClass(5, "F");

  public static final PitchClass FS = new PitchClass(6, "F#");

  public static final PitchClass G = new PitchClass(7, "G");

  public static final PitchClass GS = new PitchClass(8, "G#");

  public static final PitchClass A = new PitchClass(9, "A");

  public static final PitchClass AS = new PitchClass(10, "A#");

  public static final PitchClass B = new PitchClass(11, "B");

  public Note in(Octave octave) {
    assert octave.number() >= -1;
    assert octave.number() <= 9;
    assert octave.number() < 9 || number <= 7;
    return note((octave.number() + 1) * 12 + number);
  }

  public static PitchClass[] PITCH_CLASSES = new PitchClass[]{
      C, CS, D, DS, E, F, FS, G, GS, A, AS, B
  };

  public static void main(String[] args) {
    for (PitchClass pc : PITCH_CLASSES) {
      System.out.println(pc);
      Note root = pc.in(OCTAVE_4);
      MusicIO.playChords(Sequences.of(
          Music.chord(Sequences.of(root)),
          MAJOR_TRIAD.on(root),
          MINOR_TRIAD.on(root)
      ), 60);
    }
  }

}
