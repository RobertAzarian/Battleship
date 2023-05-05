package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Field {

    private final int playerId;
    private static int countOfPlayers = 0;
    private static final Scanner scanner = new Scanner(System.in);
    private final char[][] field = new char[10][10];
    private final char[][] fieldForRival = new char[10][10];
    private int countOfSunkShips = 0;
    private boolean areShipsDestroyed = false;
    private char[][] tmpField = field;          //  [field / rivalField] for printing
    private final boolean[][] occupiedCells = new boolean[10][10];
    Ship[] ships = new Ship[5];

    public Field() {
        countOfPlayers++;
        playerId = countOfPlayers;

        for (char[] chars : field) {
            Arrays.fill(chars, '~');
        }
        for (char[] chars : fieldForRival) {
            Arrays.fill(chars, '~');
        }
        ships[0] = new Ship("Aircraft Carrier", 5);         // 5 cells
        ships[1] = new Ship("Battleship", 4);               // 4 cells
        ships[2]  = new Ship("Submarine", 3);               // 3 cells
        ships[3] = new Ship("Cruiser", 3);                  // 3 cells
        ships[4] = new Ship("Destroyer", 2);                // 2 cells
    }

    public int getPlayerId() {
        return playerId;
    }

    public boolean areAllShipsDestroyed() {
        return areShipsDestroyed;
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
        tmpField = field;
    }

    public void printFieldForRival() {
        tmpField = fieldForRival;
        printField();
    }


    public void placeShips() {
        System.out.printf("Player %d, place your ships on the game field\n\n", playerId);
        printField();
        System.out.println();

        for (Ship ship : ships) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n\n",
                    ship.getNameOfShip(), ship.getCells());
            addShipToField(ship);
            printField();
            System.out.println();
        }

        System.out.print("Press Enter and pass the move to another player");
        scanner.nextLine();
        System.out.println("...");
    }


    public void getAShot() {
        String LineOfShotCoordinates;
        boolean isShot = false;
        while(!isShot) {
            LineOfShotCoordinates = scanner.nextLine();
            System.out.println();

            int row;
            int column;
            try {
                row = LineOfShotCoordinates.charAt(0) - 65;
                column = Integer.parseInt(LineOfShotCoordinates.substring(1)) - 1;
            } catch (RuntimeException e) {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
                continue;
            }

            if ((row < 0 || row > ('J' - 65)) || (column < 0 || column > 9)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            } else {
                isShot = true;
                if (field[row][column] == 'O') {
                    field[row][column] = 'X';
                    fieldForRival[row][column] = 'X';
                    for (Ship ship : ships) {
                        if (ship.isHorizontal()) {
                            int shipRow = ship.getRow1();
                            for (int shipColumn : ship.getOccupiedByShip().keySet()) {
                                if ((row == shipRow) && (column == shipColumn)) {
                                    if (ship.isSunk()) {
                                        countOfSunkShips++;
                                        if (countOfSunkShips == ships.length) {
                                            System.out.println("You sank the last ship. You won. Congratulations!");
                                            areShipsDestroyed = true;
                                            return;
                                        }
                                        System.out.println("You sank a ship!");
                                    } else {
                                        System.out.println("You hit a ship!");
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
                                            System.out.println("You sank the last ship. You won. Congratulations!");
                                            areShipsDestroyed = true;
                                            return;
                                        }
                                        System.out.println("You sank a ship!");
                                    } else {
                                        System.out.println("You hit a ship!");
                                    }
                                    return;
                                }
                            }
                        }
                    }
                } else if (field[row][column] == 'X') {
                    System.out.println("You hit a ship!");
                } else {
                    field[row][column] = 'M';
                    fieldForRival[row][column] = 'M';
                    System.out.println("You missed.");
                }
            }
        }
    }


    private void addShipToField(Ship ship) {
        setCoordinates(ship);

        int column1 = ship.getColumn1();
        int row1 = ship.getRow1();
        int column2 = ship.getColumn2();
        int row2 = ship.getRow2();

        if (row1 == row2) {
            int left = column1;
            int right = column2;
            if (column1 > column2) {
                left = column2;
                right = column1;
            }
            for (int i = left; i <= right; i++) {
                field[row1][i] = 'O';
            }
        } else {                    // column1 == column2
            int up = row1;
            int down = row2;
            if (row1 > row2) {
                up = row2;
                down = row1;
            }
            for (int i = up; i <= down; i++) {
                field[i][column1] = 'O';
            }
        }
        occupyCells(ship);
    }


    private void setCoordinates(Ship ship) {
        boolean areAllCorrect = false;
        do {
            String lineOfCoordinates = scanner.nextLine();
            System.out.println();
            String[] firstAndSecondPoints = lineOfCoordinates.split(" ");
            try {
                ship.setRow1(firstAndSecondPoints[0].charAt(0) - 65);
                ship.setColumn1(Integer.parseInt(firstAndSecondPoints[0].substring(1)) - 1);
                ship.setRow2(firstAndSecondPoints[1].charAt(0) - 65);
                ship.setColumn2(Integer.parseInt(firstAndSecondPoints[1].substring(1)) - 1);
            } catch (RuntimeException e) {
                System.out.println("Error! Wrong ship location! Try again:\n");
                continue;
            }
            areAllCorrect = isInputCoordinatesCorrect(ship);
        } while (!areAllCorrect);
        ship.setOrientation();

    }


    private boolean isInputCoordinatesCorrect(Ship ship) {
        int row1 = ship.getRow1();
        int column1 = ship.getColumn1();
        int row2 = ship.getRow2();
        int column2 = ship.getColumn2();
        int cells = ship.getCells();

        if ((row1 < ('A' - 65) || row1 > ('J' - 65)) || (row2 < ('A' - 65) || row2 > ('J' - 65)) ||   // checking range
                (column1 < 0 || column1 > 9) || (column2 < 0 || column2 > 9) ||
                ((row1 != row2) && (column1 != column2))) {                                         // is ship straight
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        } else if (row1 == row2) {
            if ((Math.abs(column1 - column2) + 1) != cells) {
                System.out.println("Error! Wrong length of the Submarine! Try again:\n");
                return false;
            }
            int left = column1;
            int right = column2;
            if (column1 > column2) {
                left = column2;
                right = column1;
            }
            for (int i = left; i <= right; i++) {
                if (occupiedCells[row1][i]) {
                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                    return false;
                }
            }

        } else {                                            // column1 == column2
            if ((Math.abs(row1 - row2) + 1) != cells) {
                System.out.println("Error! Wrong length of the Submarine! Try again:\n");
                return false;
            }
            int up = row1;
            int down = row2;
            if (row1 > row2) {
                up = row2;
                down = row1;
            }
            for (int i = up; i <= down; i++) {
                if (occupiedCells[i][column1]) {
                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                    return false;
                }
            }
        }
        return true;
    }


    private void occupyCells(Ship ship) {
        int row1 = ship.getRow1();
        int column1 = ship.getColumn1();
        int row2 = ship.getRow2();
        int column2 = ship.getColumn2();

        if (ship.isHorizontal()) {
            for (int i = column1; i <= column2; i++) {
                ship.getOccupiedByShip().put(i, row1);
            }

            for (int i = (column1 - 1); i <= (column2 + 1); i++) {
                if (i < 0 || i > 9) {
                    continue;
                }
                for (int j = (row1 - 1); j <= (row1 + 1); j++) {
                    if (j < 0 || j > 9) {
                        continue;
                    }
                    occupiedCells[j][i] = true;
                }
            }

        } else {
            for (int i = row1; i <= row2; i++) {
                ship.getOccupiedByShip().put(i, column1);
            }

            for (int i = (row1 - 1); i <= (row2 + 1); i++) {
                if (i < 0 || i > 9) {
                    continue;
                }
                for (int j = (column1 - 1); j <= (column1 + 1); j++) {
                    if (j < 0 || j > 9) {
                        continue;
                    }
                    occupiedCells[i][j] = true;
                }
            }
        }
    }
}
