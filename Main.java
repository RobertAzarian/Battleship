package battleship;

public class Main {

    public static void main(String[] args) {
        Field field1 = new Field();
        field1.printField();
        field1.placeShips();
        System.out.println("The game starts!\n");
        field1.printField();
        System.out.println("Take a shot!\n");
        field1.takeAShot();
    }
}