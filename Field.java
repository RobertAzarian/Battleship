package battleship;

import java.util.Arrays;

public class Field {
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
