package org.spreadsheet.cell;

import org.spreadsheet.io.CsvReader;
import org.spreadsheet.model.Spreadsheet;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CellTest {

    @Test
    void stringCellDisplaysValue() {
        Spreadsheet sheet = new Spreadsheet(1, 1);
        Cell cell = new StringCell(sheet, "Hello");

        assertEquals("Hello", cell.display());
    }

    @Test
    void numberCellDisplaysAndIsNumeric() {
        Spreadsheet sheet = new Spreadsheet(1, 1);
        NumberCell cell = new NumberCell(sheet, 4.5);

        assertEquals("4.5", cell.display());
        assertTrue(cell.isNumeric());
        assertEquals(4.5, cell.getValue());
    }

    @Test
    void horizontalLineCellReturnsEmptyDisplayValue() {
        Spreadsheet sheet = new Spreadsheet(1, 1);
        Cell cell = new HorizontalLineCell(sheet);

        assertEquals("", cell.display());
    }

    @Test
    void sumCellAddsReferencedCells() throws IOException {
        Spreadsheet sheet = read("2,1.5\n#(sum A1 B1)");

        assertEquals("3.5", sheet.getCell(1, 0).display());
        assertInstanceOf(FormulaCell.class, sheet.getCell(1, 0));
    }

    @Test
    void productCellMultipliesReferencedCells() throws IOException {
        Spreadsheet sheet = read("2,3,1.5\n#(prod A1 B1 C1)");

        assertEquals("9.0", sheet.getCell(1, 0).display());
        assertInstanceOf(ProductCell.class, sheet.getCell(1, 0));
    }

    @Test
    void formulaCellEvaluatesForwardReferences() throws IOException {
        Spreadsheet sheet = read("#(sum A2)\n5");

        assertEquals("5.0", sheet.getCell(0, 0).display());
    }

    @Test
    void csvReaderCreatesExpectedCellTypes() throws IOException {
        Spreadsheet sheet = read("Values,2,#hl,#(sum A2),#(prod A2 B2)");

        assertInstanceOf(StringCell.class, sheet.getCell(0, 0));
        assertInstanceOf(NumberCell.class, sheet.getCell(0, 1));
        assertInstanceOf(HorizontalLineCell.class, sheet.getCell(0, 2));
        assertInstanceOf(SumCell.class, sheet.getCell(0, 3));
        assertInstanceOf(ProductCell.class, sheet.getCell(0, 4));
    }

    private Spreadsheet read(String csv) throws IOException {
        return new CsvReader().read(new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8)));
    }
}
