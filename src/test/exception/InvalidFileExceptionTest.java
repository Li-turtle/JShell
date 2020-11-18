package test.exception;

import exception.InvalidFileException;
import org.junit.Test;

import static org.junit.Assert.*;

public class InvalidFileExceptionTest {
  private InvalidFileException e;

  @Test
  public void testGetMessage1() {
    // Test getMessage() with constructor 1
    e = new InvalidFileException();
    String expected = "Invalid type of file";
    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testGetMessage2() {
    // Test getMessage() with constructor 2
    String errorInfo = "error";
    e = new InvalidFileException(errorInfo);
    String expected = "error";
    assertEquals(expected, e.getMessage());
  }

}