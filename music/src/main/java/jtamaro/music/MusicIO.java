package jtamaro.music;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import jtamaro.data.Sequence;

import static jtamaro.data.Sequences.cons;
import static jtamaro.data.Sequences.of;
import static jtamaro.music.Music.chord;
import static jtamaro.music.Music.timed;

public final class MusicIO {

  private static final Logger LOGGER = Logger.getLogger(MusicIO.class.getSimpleName());

  public static void play(Sequence<TimedChord> song, int bpm) {
    play(song, bpm, 0, Instrument.ACOUSTIC_GRAND_PIANO);
  }

  public static void play(Sequence<TimedChord> song, int bpm, int channel, Instrument instrument) {
    LOGGER.info("IO.play:");
    final int msPerBeat = 60 * 1000 / bpm;
    try (Receiver receiver = MidiSystem.getReceiver()) {
      receiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE,
          channel,
          instrument.internalPcNumber(),
          0), -1);
      for (TimedChord c : cons(timed(2, chord(of())), song)) {
        LOGGER.info(c.toString());
        c.play(receiver, channel, msPerBeat);
      }
    } catch (MidiUnavailableException | InvalidMidiDataException ex) {
      LOGGER.log(Level.SEVERE, "Failed to play song", ex);
    }
    try {
      LOGGER.info("waiting at end of play.");
      Thread.sleep(4 * msPerBeat);
    } catch (InterruptedException ex) {
      LOGGER.log(Level.SEVERE, "Couldn't sleep. Music too loud?", ex);
    }
  }

  public static void playChords(Sequence<AbsoluteChord> chords) {
    playChords(chords, 120);
  }

  public static void playChords(Sequence<AbsoluteChord> chords, int bpm) {
    playChords(chords, bpm, 0, Instrument.ACOUSTIC_GRAND_PIANO);
  }

  public static void playDrumChords(Sequence<AbsoluteChord> chords, int bpm) {
    // MIDI channel 10 (internally channel 9) contains the drums
    playChords(chords, bpm, 9, Instrument.ACOUSTIC_GRAND_PIANO);
  }

  public static void playChords(Sequence<AbsoluteChord> chords, int bpm, int channel, Instrument instrument) {
    play(chords.map(chord -> timed(1, chord)), bpm, channel, instrument);
  }

  public static void playNotes(Sequence<Note> notes) {
    playNotes(notes, 120);
  }

  public static void playNotes(Sequence<Note> notes, Instrument instrument) {
    playNotes(notes, 120, 0, instrument);
  }

  public static void playNotes(Sequence<Note> notes, int bpm) {
    playNotes(notes, bpm, 0, Instrument.ACOUSTIC_GRAND_PIANO);
  }

  public static void playDrumNotes(Sequence<Note> notes, int bpm) {
    // MIDI channel 10 (internally channel 9) contains the drums
    playNotes(notes, bpm, 9, Instrument.ACOUSTIC_GRAND_PIANO);
  }

  public static void playNotes(Sequence<Note> notes, int bpm, int channel, Instrument instrument) {
    play(notes.map(n -> timed(1, chord(of(n)))), bpm, channel, instrument);
  }

  private MusicIO() {
  }
}
