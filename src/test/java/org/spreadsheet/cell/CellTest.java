package org.spreadsheet.cell;

import org.spreadsheet.model.Spreadsheet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class CellTest {

    @Test
    void stringCellDisplaysValue() {
        Cell cell = new StringCell("Hello");

        assertEquals("Hello", cell.display(new Spreadsheet(List.of(List.of()))));
    }

    @Test
    void numberCellFormatsWholeNumbersWithOneDecimal() {
        Cell cell = new NumberCell(2);

        assertEquals("2.0", cell.display(new Spreadsheet(List.of(List.of()))));
    }

    @Test
    void numberCellKeepsFractionalValues() {
        Cell cell = new NumberCell(4.5);

        assertEquals("4.5", cell.display(new Spreadsheet(List.of(List.of()))));
    }

    @Test
    void horizontalLineCellReturnsEmptyDisplayValue() {
        Cell cell = new HorizontalLineCell();

        assertEquals("", cell.display(new Spreadsheet(List.of(List.of()))));
    }

    @Test
    void sumCellAddsReferencedCells() {
        Spreadsheet sheet = new Spreadsheet(List.of(
                List.of("2", "1.5"),
                List.of("#(sum A1 B1)")
        ));

        assertEquals("3.5", sheet.getCell("A2").display(sheet));
    }

    @Test
    void productCellMultipliesReferencedCells() {
        Spreadsheet sheet = new Spreadsheet(List.of(
                List.of("2", "3", "1.5"),
                List.of("#(prod A1 B1 C1)")
        ));

        assertEquals("9.0", sheet.getCell("A2").display(sheet));
    }

    @Test
    void spreadsheetParsesCellTypes() {
        Spreadsheet sheet = new Spreadsheet(List.of(
                List.of("Values", "2", "#hl", "#(sum A2)", "#(prod A2 B2)")
        ));

        assertInstanceOf(StringCell.class, sheet.getCellAt(0, 0));
        assertInstanceOf(NumberCell.class, sheet.getCellAt(0, 1));
        assertInstanceOf(HorizontalLineCell.class, sheet.getCellAt(0, 2));
        assertInstanceOf(SumCell.class, sheet.getCellAt(0, 3));
        assertInstanceOf(ProductCell.class, sheet.getCellAt(0, 4));
        assertInstanceOf(FormulaCell.class, sheet.getCellAt(0, 3));
        assertInstanceOf(FormulaCell.class, sheet.getCellAt(0, 4));
    }
}
