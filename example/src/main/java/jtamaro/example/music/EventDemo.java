package jtamaro.example.music;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import jtamaro.data.Sequence;

import static jtamaro.data.Sequences.of;

public class EventDemo {

  private static final Logger LOG = Logger.getLogger(EventDemo.class.getName());

  static void main() {
    Sequence<Event> song = of(
        noteOn(60, 127),
        tick(),
        noteOff(60),
        noteOn(60 + 12, 127),
        tick(),
        noteOff(60 + 12),
        noteOn(60 + 12 + 12, 127),
        tick(),
        noteOff(60 + 12 + 12),
        tick(),
        noteOn(60, 127),
        noteOn(60 + 12, 127),
        noteOn(60 + 12 + 12, 127),
        tick()
    );
    play(song, 120);
  }

  static Event noteOn(int noteNumber, int velocity) {
    assert noteNumber >= 0 && noteNumber <= 127;
    assert velocity >= 0;
    return new NoteOn(noteNumber, velocity);
  }

  static Event noteOff(int noteNumber) {
    assert noteNumber >= 0 && noteNumber <= 127;
    return new NoteOff(noteNumber);
  }

  static Event tick() {
    return new Tick();
  }

  static void play(Sequence<Event> song, int bpm) {
    final int msPerTick = 60 * 1000 / bpm;
    try (Receiver receiver = MidiSystem.getReceiver()) {
      for (Event event : song) {
        event.perform(receiver, msPerTick);
        //Thread.sleep(1000);
      }
    } catch (InterruptedException
             | InvalidMidiDataException
             | MidiUnavailableException ex) {
      LOG.log(Level.SEVERE, ex.getMessage(), ex);
    }
  }

  public static void m() {
    try {
      final Receiver receiver = MidiSystem.getReceiver();
      final ShortMessage message = new ShortMessage();
      // Start playing the note Middle C (60), moderately loud (velocity = 93).
      message.setMessage(ShortMessage.NOTE_ON, 0, 60, 93);
      final long timeStamp = -1;
      receiver.send(message, timeStamp);
      Thread.sleep(1000);
    } catch (InterruptedException
             | InvalidMidiDataException
             | MidiUnavailableException ex) {
      LOG.log(Level.SEVERE, ex.getMessage(), ex);
    }
  }

  public static void find() {
    final List<MidiDevice.Info> synthInfos = new ArrayList<>();
    for (MidiDevice.Info deviceInfo : MidiSystem.getMidiDeviceInfo()) {
      System.out.println("Device: " + deviceInfo);
      try {
        final MidiDevice device = MidiSystem.getMidiDevice(deviceInfo);
        if (device instanceof Synthesizer) {
          System.out.println("  Synthesizer");
          synthInfos.add(deviceInfo);
        }
      } catch (MidiUnavailableException ex) {
        LOG.log(Level.SEVERE, ex.getMessage(), ex);
      }
    }
    if (!synthInfos.isEmpty()) {
      final MidiDevice.Info deviceInfo = synthInfos.getFirst();
      try {
        final MidiDevice device = MidiSystem.getMidiDevice(deviceInfo);
        if (!(device.isOpen())) {
          device.open();
          System.out.println("First Synthesizer opened.");
        }
      } catch (MidiUnavailableException ex) {
        LOG.log(Level.SEVERE, ex.getMessage(), ex);
      }
    }
  }

  sealed interface Event {

    void perform(Receiver receiver, int msPerTick)
        throws InvalidMidiDataException, InterruptedException;
  }

  record NoteOn(int noteNumber, int velocity) implements Event {

    public void perform(Receiver receiver, int msPerTick) throws InvalidMidiDataException {
      final ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.NOTE_ON, 0, noteNumber, velocity);
      final long timeStamp = -1;
      receiver.send(message, timeStamp);
    }
  }

  record NoteOff(int noteNumber) implements Event {

    public void perform(Receiver receiver, int msPerTick) throws InvalidMidiDataException {
      final ShortMessage message = new ShortMessage();
      message.setMessage(ShortMessage.NOTE_OFF, 0, noteNumber);
      final long timeStamp = -1;
      receiver.send(message, timeStamp);
    }
  }

  record Tick() implements Event {

    public void perform(Receiver receiver, int msPerTick) throws InterruptedException {
      System.out.println("tick -- " + System.nanoTime());
      Thread.sleep(msPerTick);
    }
  }
}
