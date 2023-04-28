package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Field {

    private static final Scanner scanner = new Scanner(System.in);
    private static int countOfSunkShips = 0;
    private final char[][] field = new char[10][10];
    private final char[][] fieldForRival = new char[10][10];
    private char[][] tmpField = field;          //  [field / rivalField] for printing

    Ship[] ships = new Ship[5];
    public static boolean areShipsDestroyed = false;

    public Field() {
        for (char[] chars : field) {
            Arrays.fill(chars, '~');
        }
        for (char[] chars : fieldForRival) {
            Arrays.fill(chars, '~');
        }
    }


    public void printField() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char row = 'A';
        for (char[] chars : tmpField) {
            System.out.print(row);
            for (char ch : chars) {
                System.out.print(" " + ch);
            }
            System.out.println();
            row++;
        }
        System.out.println();
        tmpField = field;
    }

    public void printFieldForRival() {
        tmpField = fieldForRival;
        printField();
    }

    public void placeShips() {
        ships[0] = new Ship("Aircraft Carrier", 5);    // 5 cells
        addShipToField(ships[0]);
        printField();
        ships[1] = new Ship("Battleship", 4);               // 4 cells
        addShipToField(ships[1]);
        printField();
        ships[2]  = new Ship("Submarine", 3);                // 3 cells
        addShipToField(ships[2]);
        printField();
        ships[3] = new Ship("Cruiser", 3);                     // 3 cells
        addShipToField(ships[3]);
        printField();
        ships[4] = new Ship("Destroyer", 2);                 // 2 cells
        addShipToField(ships[4]);
        printField();
    }

    public void playerTurn() {
        while (!areShipsDestroyed) {
            takeAShot();
        }
    }


    private void takeAShot() {
        String LineOfShotCoordinates;
        boolean isShot = false;
        while(!isShot) {
            LineOfShotCoordinates = scanner.nextLine();
            System.out.println();
            int row = LineOfShotCoordinates.charAt(0) - 65;
            int column = Integer.parseInt(LineOfShotCoordinates.substring(1)) - 1;

            if ((row < 0 || row > ('J' - 65)) || (column < 0 || column > 9)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            } else {
                isShot = true;
                if (field[row][column] == 'O') {
                    field[row][column] = 'X';
                    fieldForRival[row][column] = 'X';
                    printFieldForRival();
                    for (Ship ship : ships) {
                        if (ship.isHorizontal()) {
                            int shipRow = ship.getRow1();
                            for (int shipColumn : ship.getOccupiedByShip().keySet()) {
                                if ((row == shipRow) && (column == shipColumn)) {
                                    if (ship.isSunk()) {
                                        countOfSunkShips++;
                                        if (countOfSunkShips == ships.length) {
                                            System.out.println("You sank the last ship. You won. Congratulations!\n");
                                            areShipsDestroyed = true;
                                            return;
                                        }
                                        System.out.println("You sank a ship! Specify a new target:\n");
                                    } else {
                                        System.out.println("You hit a ship! Try again:\n");
                                    }
                                    return;
                                }
                            }
                        } else {
                            int shipColumn = ship.getColumn1();
                            for (int shipRow : ship.getOccupiedByShip().keySet()) {
                                if ((row == shipRow) && (column == shipColumn)) {
                                    if (ship.isSunk()) {
                                        countOfSunkShips++;
                                        if (countOfSunkShips == ships.length) {
                                            System.out.println("You sank the last ship. You won. Congratulations!\n");
                                            areShipsDestroyed = true;
                                            return;
                                        }
                                        System.out.println("You sank a ship! Specify a new target:\n");
                                    } else {
                                        System.out.println("You hit a ship!\n");
                                    }
                                    return;
                                }
                            }
                        }
                    }
                } else if (field[row][column] == 'X') {
                    printFieldForRival();
                    System.out.println("You hit a ship! Try again:\n");
                } else {
                    field[row][column] = 'M';
                    fieldForRival[row][column] = 'M';
                    printFieldForRival();
                    System.out.println("You missed. Try again:\n");
                }
            }
        }
    }

    private void addShipToField(Ship ship) {
        int column1 = ship.getColumn1();
        int raw1 = ship.getRow1();
        int column2 = ship.getColumn2();
        int raw2 = ship.getRow2();

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
