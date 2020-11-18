package test.exception;

import exception.ArityMismatchException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArityMismatchExceptionTest {
  private ArityMismatchException e;

  @Test
  public void testGetMessage1() {
    // Test getMessage() with constructor1
    e = new ArityMismatchException();
    String expected = "cannot match the number of arguments";
    assertEquals(expected, e.getMessage());
  }

  @Test
  public void testGetMessage2() {
    // Test getMessage() with constructor2
    String extraInfo = "extra";
    e = new ArityMismatchException(extraInfo);
    String expected = "extra: cannot match the number of arguments";
    assertEquals(expected, e.getMessage());
  }
}