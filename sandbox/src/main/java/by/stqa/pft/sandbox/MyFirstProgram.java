package by.stqa.pft.sandbox;

public class MyFirstProgram {
  public static void main(String[] args) {

    hello("Beauty");
    hello("User");
    hello("Maya");

    double l = 10;
    System.out.println("Area of a square with side " + l + " = " + area(l));

    double a = 4;
    double b = 6;
    System.out.println("Area of a rectangle with sides " + a + " and " + b + " = " + area(a, b));
  }

  public static void hello(String somebody) {
    System.out.println("Hello, " + somebody + "!");
  }

  public static double area(double l) {
    return l * l;
  }

  public static double area(double a, double b) {
    return a * b;
  }
}