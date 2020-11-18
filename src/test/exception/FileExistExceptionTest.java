package test.exception;

import exception.FileExistException;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileExistExceptionTest {
  private FileExistException e;

  @Test
  public void testGetMessage1() {
    // Test getMessage() with constructor 1
    e = new FileExistException("file");
    String expected = "'file': File exists";
    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testGetMessage2() {
    // Test getMessage() with constructor 2
    String extraInfo = "extra ";
    e = new FileExistException("file", extraInfo);
    String expected = "extra 'file': File exists";
    assertEquals(expected, e.getMessage());
  }
}