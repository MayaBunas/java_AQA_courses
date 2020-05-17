package by.stqa.pft.sandbox;

public class MyFirstProgram {
  public static void main(String[] args) {

    hello("Beauty");
    hello("User");
    hello("Maya");

    Square s = new Square(5);
    System.out.println("Area of a square with side " + s.l + " = " + s.area());

    Rectangle r = new Rectangle(4, 6);
    System.out.println("Area of a rectangle with sides " + r.a + " and " + r.b + " = " + r.area());
  }

  public static void hello(String somebody) {
    System.out.println("Hello, " + somebody + "!");
  }

}