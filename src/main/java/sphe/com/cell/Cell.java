package sphe.com.cell;

import sphe.com.model.Spreadsheet;

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

