package battleship;

import java.util.HashMap;
import java.util.Scanner;

public class Ship {
    private final Scanner scanner = new Scanner(System.in);
    private final String nameOfShip;
    private final int cells;
    private int shipPartsExist;
    private int column1;
    private int row1;
    private int column2;
    private int row2;
    private boolean isShipHorizontal;
    private final HashMap<Integer, Integer> occupiedByShip = new HashMap<>();
    private static final boolean[][] occupiedCells = new boolean[10][10];


    public Ship(String nameOfShip, int cells) {
        this.nameOfShip = nameOfShip;
        this.cells = cells;
        this.shipPartsExist = cells;
        System.out.printf("Enter the coordinates of the %s (%d cells):\n\n", nameOfShip, cells);
        setCoordinates();
        isShipHorizontal();
        occupyCells();
    }


    public int getColumn1() {
        return column1;
    }
    public int getRow1() {
        return row1;
    }
    public int getColumn2() {
        return column2;
    }
    public int getRow2() {
        return row2;
    }
    public boolean isHorizontal() {
        return isShipHorizontal;
    }
    public HashMap<Integer, Integer> getOccupiedByShip() {
        return occupiedByShip;
    }
    public boolean isSunk() {
        shipPartsExist--;
        return shipPartsExist == 0;
    }


    private void setCoordinates() {
        do {
            String lineOfCoordinates = scanner.nextLine();
            System.out.println();
            String[] firstAndSecondPoints = lineOfCoordinates.split(" ");
            this.row1 = firstAndSecondPoints[0].charAt(0) - 65;
            this.column1 = Integer.parseInt(firstAndSecondPoints[0].substring(1)) - 1;
            this.row2 = firstAndSecondPoints[1].charAt(0) - 65;
            this.column2 = Integer.parseInt(firstAndSecondPoints[1].substring(1)) - 1;
        } while (!isInputCoordinatesCorrect());
    }

    private boolean isInputCoordinatesCorrect() {
        if ((row1 < ('A' - 65) || row1 > ('J' - 65)) || (row2 < ('A' - 65) || row2 > ('J' - 65)) ||    // checking range
                (column1 < 0 || column1 > 9) || (column2 < 0 || column2 > 9) ||
                ((row1 != row2) && (column1 != column2))) {                                          // is ship straight
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

    private void occupyCells() {
        if (isShipHorizontal) {
            for (int i = column1; i <= column2; i++) {
                occupiedByShip.put(i, getRow1());
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
                occupiedByShip.put(i, getColumn1());
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

    private void isShipHorizontal() {
        int tmp;
        if (row1 == row2) {
            int left = column1;
            int right = column2;
            if (left > right) {
                tmp = column1;
                column1 = column2;
                column2 = tmp;
            }
            isShipHorizontal = true;
        } else {
            int up = row1;
            int down = row2;
            if (up > down) {
                tmp = row1;
                row1 = row2;
                row2 = tmp;
            }
            isShipHorizontal = false;
        }
    }
}
