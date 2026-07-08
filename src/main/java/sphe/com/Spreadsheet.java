package sphe.com;

import java.lang.invoke.CallSite;

public class Spreadsheet {

    private final Cell[][] cells;

    public Spreadsheet(int rows, int cols) {
        this.cells = new Cell[rows][cols];
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public void setCell(int row, int col, Cell cell) {
        cells[row][col] = cell;
    }

    public int getRowCount() {
        return cells.length;
    }
    public int getColCount() {
        return cells.length == 0  ? 0 : cells[0].length;
    }

}
