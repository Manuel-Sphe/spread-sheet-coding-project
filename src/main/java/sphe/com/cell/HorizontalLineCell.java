package sphe.com.cell;

import sphe.com.model.Spreadsheet;

public class HorizontalLineCell extends Cell {

    public HorizontalLineCell(Spreadsheet spreadsheet) {
        super(spreadsheet);
    }

    @Override
    public String display() {
        return "";
    }
}
