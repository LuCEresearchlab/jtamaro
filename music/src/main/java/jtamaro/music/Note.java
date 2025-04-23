package jtamaro.music;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Music note.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Scientific_pitch_notation">Wikipedia: Scientific
 * pitch notation</a>
 * @see <a href="https://en.wikipedia.org/wiki/Pitch_class">Wikipedia: Pitch class</a>
 */
public record Note(int noteNumber) {

  private static final Logger LOGGER = Logger.getLogger(Note.class.getSimpleName());

  public void on(Receiver receiver, int channel) {
    try {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.NOTE_ON, channel, noteNumber, 127);
      long timeStamp = -1;
      receiver.send(message, timeStamp);
    } catch (InvalidMidiDataException ex) {
      LOGGER.log(Level.WARNING, "Failed turn on note " + noteNumber, ex);
    }
  }

  public void off(Receiver receiver, int channel) {
    try {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.NOTE_OFF, channel, noteNumber);
      long timeStamp = -1;
      receiver.send(message, timeStamp);
    } catch (InvalidMidiDataException ex) {
      LOGGER.log(Level.WARNING, "Failed turn off note " + noteNumber, ex);
    }
  }

  public Note transpose(int semitones) {
    return new Note(noteNumber + semitones);
  }

  public String toString() {
    return "" + noteNumber;
  }

}
