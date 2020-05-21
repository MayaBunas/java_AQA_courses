package by.stqa.pft.homework2;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testZeroDistance() {
    Point p1 = new Point(2.5, 2.5);
    Point p2 = new Point(2.5, 2.5);
    Assert.assertEquals(p1.distance(p2), 0.0);
  }

  @Test
  public void testIntPointsDistance() {
    Point p1 = new Point(0, 4);
    Point p2 = new Point(3, 0);
    Assert.assertEquals(p1.distance(p2), 5.0);
  }

  @Test
  public void testNegativePointsDistance() {
    Point p1 = new Point(0, -4);
    Point p2 = new Point(-3, 0);
    Assert.assertEquals(p1.distance(p2), 5.0);
  }

  @Test
  public void testDoublePointsDistance() {
    Point p1 = new Point(1.5, 2.5);
    Point p2 = new Point(3.5, 2.5);
    Assert.assertEquals(p1.distance(p2), 2.0);
  }
}
