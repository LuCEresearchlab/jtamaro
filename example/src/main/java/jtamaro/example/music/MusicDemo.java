package jtamaro.example.music;

import jtamaro.data.Sequence;
import jtamaro.data.Sequences;
import jtamaro.music.AbsoluteChord;
import jtamaro.music.Chord;
import jtamaro.music.GenericInterval;
import jtamaro.music.Instrument;
import jtamaro.music.Interval;
import jtamaro.music.IntervalQuality;
import jtamaro.music.Music;
import jtamaro.music.MusicIO;
import jtamaro.music.Note;
import jtamaro.music.Notes;
import jtamaro.music.Percussion;
import jtamaro.music.PitchClass;
import jtamaro.music.RelativeChord;
import jtamaro.music.Scales;

import static jtamaro.music.Chord.MAJOR_TRIAD;
import static jtamaro.music.Chord.MINOR_TRIAD;
import static jtamaro.music.Notes.C4;
import static jtamaro.music.Octave.OCTAVE_4;

@SuppressWarnings("unused")
public class MusicDemo {

  static void main() {
  }

  public static void chord() {
    for (Chord ch : Chord.CHORDS) {
      System.out.println(ch);
      AbsoluteChord absoluteChord = ch.on(C4);
      Sequence<AbsoluteChord> seq = absoluteChord.notes().map(n -> Music.chord(Sequences.of(n)));
      MusicIO.playChords(Sequences.cons(absoluteChord, seq), 80);
    }
  }

  public static void genericInterval() {
    for (GenericInterval gi : GenericInterval.values()) {
      System.out.println(gi + " " + gi.getStaffPositions() + " " + gi.getName());
    }
  }

  public static void instrument() {
    for (Instrument instrument : Instrument.values()) {
      System.out.println(instrument);
      Note root = Notes.C4;
      MusicIO.playChords(
          Sequences.of(
              Music.chord(Sequences.of(root)),
              Chord.MAJOR_TRIAD.on(root)
          ),
          80,
          0,
          instrument
      );
    }
  }

  public static void interval() {
    // Table shown in the last step of this page:
    // https://www.musictheory.net/lessons/31
    System.out.println("Number of semitones for the different specific intervals:");
    System.out.print('\t');
    for (IntervalQuality q : IntervalQuality.values()) {
      System.out.print(q.getName() + '\t');
    }
    System.out.println();
    for (GenericInterval gi : GenericInterval.values()) {
      System.out.print(gi.getName() + '\t');
      for (IntervalQuality q : IntervalQuality.values()) {
        Interval i = Interval.get(gi, q);
        //String cell = i==null ? "" : i.getShortName();
        String cell = i == null ? "" : "" + i.semitones();
        System.out.print(cell + '\t');
      }
      System.out.println();
    }

    System.out.println("All intervals with their intervallic inversions:");
    System.out.println("Interval\tInversion\t");
    for (Interval interval : Interval.INTERVALS) {
      System.out.println(interval + "\t" + interval.invert());
    }

    System.out.println("Playing intervals:");
    final Note root = C4;
    for (Interval interval : Interval.INTERVALS) {
      MusicIO.playChords(Sequences.of(
          interval.dyad(root),
          interval.invert().dyad(root)
      ));
    }
  }

  public static void intervalQuality() {
    for (IntervalQuality q : IntervalQuality.values()) {
      System.out.println(q + " Inverse: " + q.invert());
    }
  }

  public static void percussion() {
    // TODO: It seems that Java's default sound bank (Gervill?)
    //       only includes a subset of the General MIDI percussion sounds.
    //       https://www.midi.org/specifications-old/item/gm-level-1-sound-set

    System.out.println("Play a piece:");
    MusicIO.playDrumChords(
        Sequences.of(
            Music.chord(Sequences.of(Percussion.ACOUSTIC_BASS_DRUM.asNote())),
            Music.chord(Sequences.of(Percussion.ACOUSTIC_SNARE.asNote())),
            Music.chord(Sequences.of(Percussion.ACOUSTIC_BASS_DRUM.asNote())),
            Music.chord(Sequences.of(Percussion.ACOUSTIC_SNARE.asNote())),
            Music.chord(Sequences.of(Percussion.CLOSED_HI_HAT.asNote())),
            Music.chord(Sequences.of(Percussion.OPEN_HI_HAT.asNote())),
            Music.chord(Sequences.of(Percussion.LOW_FLOOR_TOM.asNote())),
            Music.chord(Sequences.of(Percussion.HIGH_FLOOR_TOM.asNote())),
            Music.chord(Sequences.of(Percussion.LOW_TOM.asNote())),
            Music.chord(Sequences.of(Percussion.LOW_MID_TOM.asNote())),
            Music.chord(Sequences.of(Percussion.HI_MID_TOM.asNote())),
            Music.chord(Sequences.of(Percussion.HIGH_TOM.asNote())),
            Music.chord(Sequences.of(Percussion.CRASH_CYMBAL_1.asNote())),
            Music.chord(Sequences.of(
                Percussion.LOW_FLOOR_TOM.asNote(),
                Percussion.HIGH_TOM.asNote()
            ))
        ),
        80
    );

    System.out.println("Play each percussion instrument individually:");
    for (Percussion percussion : Percussion.values()) {
      System.out.println(percussion);
      MusicIO.playDrumNotes(
          Sequences.of(percussion.asNote()),
          80
      );
    }
  }

  public static void pitchClass() {
    for (PitchClass pc : PitchClass.PITCH_CLASSES) {
      System.out.println(pc);
      Note root = pc.in(OCTAVE_4);
      MusicIO.playChords(Sequences.of(
          Music.chord(Sequences.of(root)),
          MAJOR_TRIAD.on(root),
          MINOR_TRIAD.on(root)
      ), 60);
    }
  }

  public static void relativeChord() {
    // https://blog.landr.com/common-chord-progressions/
    MusicIO.playChords(Sequences.of(
        RelativeChord.I_CHORD,
        RelativeChord.V_CHORD,
        RelativeChord.vi_CHORD,
        RelativeChord.iii_CHORD,
        RelativeChord.IV_CHORD,
        RelativeChord.I_CHORD,
        RelativeChord.IV_CHORD,
        RelativeChord.V_CHORD
    ).map(Scales.C_MAJOR::in), 60);
  }
}
