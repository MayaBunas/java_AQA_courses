package by.stqa.pft.homework2;

public class Homework2 {

  public static void main(String[] args) {

    Point p1 = new Point(0, 4);
    Point p2 = new Point(3, 0);
    System.out.println("Function: Distance between points with coordinates (" + p1.x + "," + p1.y + ") and (" + p2.x + "," + p2.y + ") = " + distance(p1, p2));
    System.out.println("Method: Distance between points with coordinates " + p1 + " and " + p2 + " = " + p1.distance(p2));

    p1 = new Point(0, 0);
    p2 = new Point(3, 4);
    System.out.println("Distance between points with coordinates " + p1 + " and " + p2 + " = " + p1.distance(p2));

    p1 = new Point(2.5, 2.5);
    p2 = new Point(2.5, 2.5);
    System.out.println("Distance between points with coordinates " + p1 + " and " + p2 + " = " + p1.distance(p2));

  }

  public static double distance(Point p1, Point p2) {
    return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
  }
}
