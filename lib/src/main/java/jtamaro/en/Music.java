package jtamaro.en;

import static jtamaro.en.Sequences.of;

import jtamaro.en.music.AbsoluteChord;
import jtamaro.en.music.Note;
import jtamaro.en.music.Octave;
import jtamaro.en.music.PitchClass;
import jtamaro.en.music.TimedChord;


/**
 * Pitch Classes and Tone Names
 * https://en.wikipedia.org/wiki/Pitch_(music)
 * C-D-E-F-G-A-B (in German: B is written as H)
 * 
 * Octaves
 * middle C: SPN: C4, Helmholz: c'
 * A440: SPN: A4, Helmholz: a'
 * C-1 = C,,, = Double Contra
 * C0 = C,, = Sub Contra
 * C1 = C, = Contra
 * C2 = C, = Great
 * C3 = c, = Small
 * C4 = c' = 1 Line
 * C5 = c'' = 2 Line
 * C6 = c''' = 3 Line
 * C7 = c'''' = 4 Line
 * C8 = c''''' = 5 Line
 * C9 = c'''''' = 6 Line
 * 
 * Scientific Pitch Notation
 * C0, F♯4, D♭6
 * https://en.wikipedia.org/wiki/Scientific_pitch_notation
 * 
 * Helmholtz pitch notation
 * f♯'
 * https://en.wikipedia.org/wiki/Helmholtz_pitch_notation
 */
public abstract class Music {
  
  private Music() {}

  public Note note(int midiNoteNumber) {
    return new Note(midiNoteNumber);
  }

  public Note note(PitchClass pitchClass, Octave octave) {
    return pitchClass.in(octave);
  }


  public static AbsoluteChord chord(Sequence<Note> notes) {
    return new AbsoluteChord(notes);
  }

  public static TimedChord timed(int beats, AbsoluteChord chord) {
    assert beats > 0;
    return new TimedChord(beats, chord);
  }

  public static TimedChord pause(int beats) {
    assert beats > 0;
    return new TimedChord(beats, new AbsoluteChord(of()));
  }

}
