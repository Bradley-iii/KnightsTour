package com.example.knightstour2023;

public class Location {
    private int row;
    private int col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "Location{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

    public boolean equals(Location other) {
        return this.row == other.row && this.col == other.col;
    }
}
