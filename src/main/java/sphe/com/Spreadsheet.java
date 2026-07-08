package sphe.com;

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

    public Cell getCell(int reference) {
        return null;
    }
}
