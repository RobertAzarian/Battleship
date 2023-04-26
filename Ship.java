package battleship;

import java.util.Scanner;

public class Ship {
    private final Scanner scanner = new Scanner(System.in);
    private final String nameOfShip;
    private final int cells;
    private int column1;
    private int raw1;
    private int column2;
    private int raw2;
    private static final boolean[][] occupiedCells = new boolean[10][10];


    public Ship(String nameOfShip, int cells) {
        this.nameOfShip = nameOfShip;
        this.cells = cells;
        System.out.printf("Enter the coordinates of the %s (%d cells):\n\n", nameOfShip, cells);
        setCoordinates();
    }

    public int getColumn1() {
        return column1;
    }

    public int getRaw1() {
        return raw1;
    }

    public int getColumn2() {
        return column2;
    }

    public int getRaw2() {
        return raw2;
    }


    private void setCoordinates() {
        do {
            String lineOfCoordinates = scanner.nextLine();
            System.out.println();
            String[] firstAndSecondPoints = lineOfCoordinates.split(" ");
            this.raw1 = firstAndSecondPoints[0].charAt(0) - 65;
            this.column1 = Integer.parseInt(firstAndSecondPoints[0].substring(1)) - 1;
            this.raw2 = firstAndSecondPoints[1].charAt(0) - 65;
            this.column2 = Integer.parseInt(firstAndSecondPoints[1].substring(1)) - 1;
        } while (!isInputCoordinatesCorrect());
        occupyCells();
    }

    private boolean isInputCoordinatesCorrect() {
        if ((raw1 < ('A' - 65) || raw1 > ('J' - 65)) || (raw2 < ('A' - 65) || raw2 > ('J' - 65)) ||    // checking range
                (column1 < 0 || column1 > 9) || (column2 < 0 || column2 > 9) ||
                ((raw1 != raw2) && (column1 != column2))) {                                          // is ship straight
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        } else if (raw1 == raw2) {
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
                if (occupiedCells[raw1][i]) {
                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                    return false;
                }
            }

        } else {                                            // column1 == column2
            if ((Math.abs(raw1 - raw2) + 1) != cells) {
                System.out.println("Error! Wrong length of the Submarine! Try again:\n");
                return false;
            }
            int up = raw1;
            int down = raw2;
            if (raw1 > raw2) {
                up = raw2;
                down = raw1;
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
        if (raw1 == raw2) {
            int left = column1;
            int right = column2;
            if (column1 > column2) {
                left = column2;
                right = column1;
            }
            for (int i = (left - 1); i <= (right + 1); i++) {
                if (i < 0 || i > 9) {
                    continue;
                }
                for (int j = (raw1 - 1); j <= (raw1 + 1); j++) {
                    if (j < 0 || j > 9) {
                        continue;
                    }
                    occupiedCells[j][i] = true;
                }
            }
        } else if (column1 == column2) {
            int up = raw1;
            int down = raw2;
            if (raw1 > raw2) {
                up = raw2;
                down = raw1;
            }
            for (int i = (up - 1); i <= (down + 1); i++) {
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
