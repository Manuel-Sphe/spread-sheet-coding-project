package org.spreadsheet.io;

import org.spreadsheet.cell.NumberCell;
import org.spreadsheet.cell.ProductCell;
import org.spreadsheet.cell.StringCell;
import org.spreadsheet.cell.SumCell;
import org.spreadsheet.model.Spreadsheet;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class CsvReaderTest {

    private final CsvReader reader = new CsvReader();

    @Test
    void readsInputCsvFromFilePath() throws IOException {
        Path input = Path.of("src/main/resources/input.csv");
        Spreadsheet sheet = reader.read(input);

        assertEquals(9, sheet.getRowCount());
        assertEquals(3, sheet.getColumnCount());
        assertEquals("Values", sheet.getCell(0, 0).display());
        assertEquals("Factor", sheet.getCell(0, 1).display());
        assertInstanceOf(NumberCell.class, sheet.getCell(2, 0));
        assertInstanceOf(ProductCell.class, sheet.getCell(2, 2));
        assertInstanceOf(SumCell.class, sheet.getCell(6, 2));
    }

    @Test
    void readsInputCsvFromClasspathStream() throws IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("input.csv")) {
            Spreadsheet sheet = reader.read(input);

            assertEquals(9, sheet.getRowCount());
            assertEquals(3, sheet.getColumnCount());
            assertEquals("Values", sheet.getCell(0, 0).display());
            assertEquals("Factor", sheet.getCell(0, 1).display());
            assertInstanceOf(NumberCell.class, sheet.getCell(2, 0));
            assertInstanceOf(ProductCell.class, sheet.getCell(2, 2));
            assertInstanceOf(SumCell.class, sheet.getCell(6, 2));
        }
    }

    @Test
    void preservesTrailingEmptyCells() throws IOException {
        Spreadsheet sheet = reader.read(new ByteArrayInputStream(
                ",,, \n,,Prod test:,#(prod A6 A7 B6)\n".getBytes(StandardCharsets.UTF_8)));

        assertEquals(4, sheet.getColumnCount());
        assertEquals("", sheet.getCell(0, 0).display());
        assertEquals("", sheet.getCell(0, 1).display());
        assertEquals("Prod test:", sheet.getCell(1, 2).display());
    }

    @Test
    void padsRowsToMaximumColumnCount() throws IOException {
        Spreadsheet sheet = reader.read(new ByteArrayInputStream(
                "a,b,c\n1,2\n".getBytes(StandardCharsets.UTF_8)));

        assertEquals(2, sheet.getRowCount());
        assertEquals(3, sheet.getColumnCount());
        assertInstanceOf(StringCell.class, sheet.getCell(1, 2));
        assertEquals("", sheet.getCell(1, 2).display());
    }

    @Test
    void treatsBlankValuesAsEmptyStringCells() throws IOException {
        Spreadsheet sheet = reader.read(new ByteArrayInputStream(
                " ,  \n".getBytes(StandardCharsets.UTF_8)));

        assertInstanceOf(StringCell.class, sheet.getCell(0, 0));
        assertEquals("", sheet.getCell(0, 0).display());
    }
}
