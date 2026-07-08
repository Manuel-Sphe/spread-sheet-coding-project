package sphe.com;

public abstract class Cell {

    protected final Spreadsheet spreadsheet;

    protected Cell(Spreadsheet spreadsheet) {
        this.spreadsheet = spreadsheet;
    }

    public abstract String display();

    public boolean isNumeric() {
        return false;
    }
}

