package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Field {

    static Scanner scanner = new Scanner(System.in);
    char[][] field = new char[10][10];

    public Field() {
        for (char[] chars : field) {
            Arrays.fill(chars, '~');
        }
    }


    public void printField() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char row = 'A';
        for (char[] chars : field) {
            System.out.print(row);
            for (char ch : chars) {
                System.out.print(" " + ch);
            }
            System.out.println();
            row++;
        }
        System.out.println();
    }

    public void placeShips() {
        Ship aircraftCarrier = new Ship("Aircraft Carrier", 5);    // 5 cells
        addShipToField(aircraftCarrier);
        printField();
        Ship battleship = new Ship("Battleship", 4);               // 4 cells
        addShipToField(battleship);
        printField();
        Ship submarine  = new Ship("Submarine", 3);                // 3 cells
        addShipToField(submarine);
        printField();
        Ship cruiser = new Ship("Cruiser", 3);                     // 3 cells
        addShipToField(cruiser);
        printField();
        Ship destroyer = new Ship("Destroyer", 2);                 // 2 cells
        addShipToField(destroyer);
        printField();
    }

    public void takeAShot() {
        String LineOfShotCoordinates;
        boolean isShot = false;
        while(!isShot) {
            LineOfShotCoordinates = scanner.nextLine();
            System.out.println();
            int raw = LineOfShotCoordinates.charAt(0) - 65;
            int column = Integer.parseInt(LineOfShotCoordinates.substring(1)) - 1;

            if ((raw < 0 || raw > ('J' - 65)) || (column < 0 || column > 9)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            } else {
                isShot = true;
                if (field[raw][column] == 'O') {
                    field[raw][column] = 'X';
                    printField();
                    System.out.println("You hit a ship!\n");
                } else {
                    field[raw][column] = 'M';
                    printField();
                    System.out.println("You missed!\n");
                }
            }
        }

    }

    private void addShipToField(Ship ship) {
        int column1 = ship.getColumn1();
        int raw1 = ship.getRaw1();
        int column2 = ship.getColumn2();
        int raw2 = ship.getRaw2();

        if (raw1 == raw2) {
            int left = column1;
            int right = column2;
            if (column1 > column2) {
                left = column2;
                right = column1;
            }
            for (int i = left; i <= right; i++) {
                field[raw1][i] = 'O';
            }
        } else {                    // column1 == column2
            int up = raw1;
            int down = raw2;
            if (raw1 > raw2) {
                up = raw2;
                down = raw1;
            }
            for (int i = up; i <= down; i++) {
                field[i][column1] = 'O';
            }
        }
    }
}
