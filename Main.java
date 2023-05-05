package battleship;

import java.util.Scanner;


public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Field field1 = new Field();
    static Field field2 = new Field();

    public static void main(String[] args) {
        preparingForTheGame();
        inGame();
    }


    public static void preparingForTheGame() {
        field1.placeShips();
        field2.placeShips();
        System.out.println();
    }

    public static void inGame() {
        int numberOfMoves = 0;
        Field tmpField1;
        Field tmpField2;

        while (true) {
            numberOfMoves++;
            if (numberOfMoves % 2 == 1) {
                tmpField1 = field1;
                tmpField2 = field2;
            } else {
                tmpField1 = field2;
                tmpField2 = field1;
            }
            tmpField2.printFieldForRival();
            System.out.println("---------------------");
            tmpField1.printField();
            System.out.println();

            System.out.printf("Player %d, it's your turn:\n\n", tmpField1.getPlayerId());

            tmpField2.getAShot();
            if (tmpField1.areAllShipsDestroyed()) {
                break;
            }
            System.out.print("Press Enter and pass the move to another player");
            scanner.nextLine();
            System.out.println("...\n");
        }
    }
}