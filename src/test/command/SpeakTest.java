package test.command;

import static org.junit.Assert.*;
import fs.File;
import org.junit.Before;
import org.junit.Test;
import command.Cat;
import command.Speak;
import exception.ArityMismatchException;
import exception.InvalidFormatException;
import fs.FileSystem;
import test.command.CatTest.MockFileSystem;
import util.IO;

public class SpeakTest {

  private Speak speak;
  private IO io;

  /**
   * setUp method of this JUnitTest.
   * 
   * @throws Exception various Exception
   */
  @Before
  public void setUp() throws Exception {
    speak = new Speak();
    io = new IO();
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = speak.getDoc();
    String expected
        = "NAME\n"
            + "    speak -  convert text to audible speech\n\n"
            + "SYNOPISIS\n"
            + "    speak [STRING]\n\n"
            + "DESCRIPTION\n"
            + "    speak the text if the text is detected in the first line.\n"
            + "    Otherwise, speak text until QUIT end marker\n";
    assertEquals(expected, rst);
  }
  
  @Test
  public void testExecute1() throws Exception {
    // Test normal running
    speak.exec(new String[]{"speak", "\"hello\""}, io);
  }
  
  @Test(expected = ArityMismatchException.class)
  public void testInArg() throws Exception {
    // Test when passing incorrect number of args
    speak.exec(new String[]{"speak", "\"hello\"", "world"}, io);
  }

  @Test (expected = InvalidFormatException.class)
  public void testNoArg() throws Exception {
    // Test when passing arguments that are not String
    speak.exec(new String[]{"speak", "hello"}, io);
  }

}
