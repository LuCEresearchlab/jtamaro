package jtamaro.music;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Scientific_pitch_notation">Wikipedia: Scientific
 * pitch notation</a>
 * @see <a href="https://en.wikipedia.org/wiki/Pitch_class">Wikipedia: Pitch class</a>
 */
public record Note(int noteNumber) {

  public void on(Receiver receiver, int channel) {
    try {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.NOTE_ON, channel, noteNumber, 127);
      long timeStamp = -1;
      receiver.send(message, timeStamp);
    } catch (InvalidMidiDataException ex) {
      ex.printStackTrace();
    }
  }

  public void off(Receiver receiver, int channel) {
    try {
      ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.NOTE_OFF, channel, noteNumber);
      long timeStamp = -1;
      receiver.send(message, timeStamp);
    } catch (InvalidMidiDataException ex) {
      ex.printStackTrace();
    }
  }

  public Note transpose(int semitones) {
    return new Note(noteNumber + semitones);
  }

  public String toString() {
    return "" + noteNumber;
  }

}
