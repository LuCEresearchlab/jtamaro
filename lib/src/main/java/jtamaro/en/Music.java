package jtamaro.en;

import static jtamaro.en.Sequences.of;

import jtamaro.en.music.AbsoluteChord;
import jtamaro.en.music.Note;
import jtamaro.en.music.Octave;
import jtamaro.en.music.PitchClass;
import jtamaro.en.music.TimedChord;


/**
 * Pitch Classes and Tone Names
 *
 * <p>C-D-E-F-G-A-B (in German: B is written as H)
 *
 * <p>Octaves
 * <ul>
 *   <li>middle C: SPN: C4, Helmholz: c'</li>
 *   <li>A440: SPN: A4, Helmholz: a'</li>
 *   <li>C-1 = C,,, = Double Contra</li>
 *   <li>C0 = C,, = Sub Contra</li>
 *   <li>C1 = C, = Contra</li>
 *   <li>C2 = C, = Great</li>
 *   <li>C3 = c, = Small</li>
 *   <li>C4 = c' = 1 Line</li>
 *   <li>C5 = c'' = 2 Line</li>
 *   <li>C6 = c''' = 3 Line</li>
 *   <li>C7 = c'''' = 4 Line</li>
 *   <li>C8 = c''''' = 5 Line</li>
 *   <li>C9 = c'''''' = 6 Line</li>
 * </ul>
 *
 * <p>Scientific Pitch Notation: <code>C0, F♯4, D♭6</code>
 *
 * <p>Helmholtz pitch notation: <code>f♯'</code>
 *
 *
 * @see <a href="https://en.wikipedia.org/wiki/Pitch_(music)">Pitch</a>
 * @see <a href="https://en.wikipedia.org/wiki/Scientific_pitch_notation">Scientific notation</a>
 * @see <a href="https://en.wikipedia.org/wiki/Helmholtz_pitch_notation">Helmholtz notation</a>
 */
public abstract class Music {

  private Music() {
  }

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
