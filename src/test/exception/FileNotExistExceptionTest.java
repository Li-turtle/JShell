package test.exception;

import exception.FileNotExistException;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileNotExistExceptionTest {
  private FileNotExistException e;

  @Test
  public void testGetMessage1() {
    // Test getMessage() with constructor 1
    e = new FileNotExistException("file");
    String expected = "'file': No such file or directory";
    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testGetMessage2() {
    // Test getMessage() with constructor 2
    String extraInfo = "extra ";
    e = new FileNotExistException("file", extraInfo);
    String expected = "extra 'file': No such file or directory";
    assertEquals(expected, e.getMessage());
  }

}