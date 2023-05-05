package battleship;

import java.util.HashMap;

public class Ship {

    private final String nameOfShip;
    private final int cells;
    private int shipPartsExist;
    private int column1;
    private int row1;
    private int column2;
    private int row2;
    private boolean isShipHorizontal;
    private final HashMap<Integer, Integer> occupiedByShip = new HashMap<>();


    public Ship(String nameOfShip, int cells) {
        this.nameOfShip = nameOfShip;
        this.cells = cells;
        this.shipPartsExist = cells;
    }


    public String getNameOfShip() {
        return nameOfShip;
    }

    public int getColumn1() {
        return column1;
    }

    public void setColumn1(int column1) {
        this.column1 = column1;
    }

    public int getRow1() {
        return row1;
    }

    public void setRow1(int row1) {
        this.row1 = row1;
    }

    public int getColumn2() {
        return column2;
    }

    public void setColumn2(int column2) {
        this.column2 = column2;
    }

    public int getRow2() {
        return row2;
    }

    public void setRow2(int row2) {
        this.row2 = row2;
    }

    public int getCells() {
        return cells;
    }

    public HashMap<Integer, Integer> getOccupiedByShip() {
        return occupiedByShip;
    }
    public boolean isSunk() {
        shipPartsExist--;
        return shipPartsExist == 0;
    }

    public boolean isHorizontal() {
        return isShipHorizontal;
    }

    public void setOrientation() {
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