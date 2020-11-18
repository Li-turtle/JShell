package test.exception;

import exception.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InvalidFormatExceptionTest {
  private InvalidFormatException e;

  @Before
  public void setUp() {
    e = new InvalidFormatException("message");
  }

  @Test
  public void testGetMessage() {
    // test getMessage()
    String expected = "'message': Incorrect format";
    assertEquals(expected, e.getMessage());
  }

}