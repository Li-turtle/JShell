package test.exception;

import exception.StackEmptyException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StackEmptyExceptionTest {
  private StackEmptyException e;

  @Before
  public void setUP() {
    e = new StackEmptyException();
  }

  @Test
  public void testGetMessage() {
    // test getMessage()
    String expected = "Stack empty: cannot pop elements";
    assertEquals(expected, e.getMessage());
  }
}