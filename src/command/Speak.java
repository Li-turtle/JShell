package command;

import exception.ArityMismatchException;
import exception.InvalidFormatException;
import java.io.Serializable;
import util.ArgParser;
import util.IO;
import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

/**
 * The Command class to convert text to audible speech using free TTs from
 * https://freetts.sourceforge.io/docs/index.php
 *
 * @author jiayu lu jiay.lu@mail.utoronto.ca
 */
public class Speak implements Command, Serializable {

  /**
   * The documentation of Speak command.
   */
  private String doc = "NAME\n"
      + "    speak -  convert text to audible speech\n\n"
      + "SYNOPISIS\n"
      + "    speak [STRING]\n\n"
      + "DESCRIPTION\n"
      + "    speak the text if the text is detected in the first line.\n"
      + "    Otherwise, speak text until QUIT end marker\n";

  /**
   * The synthesizer that make sounds.
   */
  private transient Synthesizer synthesizer;

  /**
   * Init Speak class with working directory.
   */
  public Speak() {
    try {
      initSpeaker();
    } catch (Exception e) {
      // Blank here
      e.printStackTrace();
    }
  }

  /**
   * Return the documentation of Speak.
   *
   * @return The documentation for Speak
   */
  @Override
  public String getDoc() {
    return doc;
  }

  /**
   * Get prepared for making sounds.
   *
   * @throws Exception various exceptions
   */
  private void initSpeaker() throws Exception {
    System.setProperty(
        "freetts.voices",
        "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory"
    );

    // Register Engine
    Central.registerEngineCentral(
        "com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");

    SynthesizerModeDesc desc = new SynthesizerModeDesc(null,
        "general", Locale.US, null, null);
    synthesizer = Central.createSynthesizer(desc);

    // Allocate synthesizer
    synthesizer.allocate();

    desc = (SynthesizerModeDesc) synthesizer.getEngineModeDesc();
    // Resume Synthesizer
    synthesizer.resume();
  }

  /**
   * Make the sound of text.
   *
   * @param text The text of the sound
   * @throws Exception various exceptions
   */
  private void speak(String text) throws Exception {
    synthesizer.speakPlainText(text, null);
    synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
  }

  /**
   * Free the synthesizer after speak.
   *
   * @throws Exception various exceptions
   */
  private void freeSpeaker() throws Exception {
    synthesizer.deallocate();
  }

  /**
   * Execute Speak command with args.
   *
   * @param args Arguments that passed to the command
   * @throws Exception number of arguments invalid or invalid format of string
   */
  @Override
  public void exec(String[] args, IO io) throws Exception {
    String speakString = "";
    if (args.length == 1) {
      for (; ; ) {
        String readLine = IO.readLine();
        speakString += readLine + " ";
        // If quit detected, then break the loop
        if (readLine.endsWith(" QUIT") || readLine.trim().equals("QUIT")) {
          break;
        }
      }
      speakString = speakString.substring(0,
           Math.max((speakString.length() - 6), 0));
    } else if (args.length == 2) {
      if (!ArgParser.isString(args[1])) {
        throw new InvalidFormatException(args[1]);
      }
      speakString += ArgParser.getContent(args[1]);
    } else {
      throw new ArityMismatchException();
    }
    try {
      speak(speakString.trim());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
