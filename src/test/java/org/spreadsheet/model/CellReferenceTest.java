package org.spreadsheet.model;

import org.junit.jupiter.api.Test;

import org.spreadsheet.cell.NumberCell;
import org.spreadsheet.cell.StringCell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class SpreadsheetTest {

    @Test
    void storesAndReturnsCellsByCoordinate() {
        Spreadsheet sheet = new Spreadsheet(2, 2);
        StringCell topLeft = new StringCell(sheet, "label");
        NumberCell bottomRight = new NumberCell(sheet, 42);

        sheet.setCell(0, 0, topLeft);
        sheet.setCell(1, 1, bottomRight);

        assertSame(topLeft, sheet.getCell(0, 0));
        assertSame(bottomRight, sheet.getCell(1, 1));
    }

    @Test
    void reportsRowAndColumnCounts() {
        Spreadsheet sheet = new Spreadsheet(3, 4);

        assertEquals(3, sheet.getRowCount());
        assertEquals(4, sheet.getColumnCount());
    }

    @Test
    void emptySpreadsheetHasNoColumns() {
        Spreadsheet sheet = new Spreadsheet(0, 0);

        assertEquals(0, sheet.getRowCount());
        assertEquals(0, sheet.getColumnCount());
    }
}
