package test.fs;

import static org.junit.Assert.assertEquals;

import fs.Directory;
import fs.File;
import org.junit.Before;
import org.junit.Test;

public class FileTest {

  private File file;

  /**
   * setUp method of this JUnitTest.
   * 
   * @throws Exception various Exception
   */
  @Before
  public void setUp() {
    file = new Directory("/");
    file.setParent((Directory) file);
  }

  @Test
  public void setParentTest() {
    Directory par = new Directory("par");
    file.setParent(par);
    assertEquals(par, file.getParent());
  }

  @Test
  public void getParentTest() {
    Directory par = new Directory("par");
    file.setParent(par);
    assertEquals(par, file.getParent());
  }

  @Test
  public void setNameTest() {
    file.setName("PPT");
    assertEquals("PPT", file.getName());
  }

  @Test
  public void getNameTest() {
    assertEquals("/", file.getName());
  }

  @Test
  public void toStringTest() {
    assertEquals("/", file.toString());
  }
}
