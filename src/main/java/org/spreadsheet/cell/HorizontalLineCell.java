package org.spreadsheet.cell;

import org.spreadsheet.model.Spreadsheet;

public class HorizontalLineCell extends Cell {

    public HorizontalLineCell(Spreadsheet spreadsheet) {
        super(spreadsheet);
    }

    @Override
    public String display() {
        return "";
    }
}
