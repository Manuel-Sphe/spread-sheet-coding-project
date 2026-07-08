package org.spreadsheet.renderer;

import org.spreadsheet.io.CsvReader;
import org.spreadsheet.model.Spreadsheet;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpreadsheetRendererTest {

    private final SpreadsheetRenderer renderer = new SpreadsheetRenderer();
    private final CsvReader reader = new CsvReader();

    @Test
    void rendersInputCsv() throws IOException {
        Spreadsheet sheet = load("input.csv");

        String expected = String.join(System.lineSeparator(),
                "Values    |Factor|     ",
                "----------|------|     ",
                "       2.0|   1.5|  3.0",
                "       3.0|   2.0|  6.0",
                "       4.5|   2.5|11.25",
                "          |      |-----",
                "          |Total:|20.25",
                "Sum test: |   4.5|     ",
                "Prod test:|   9.0|     "
        ) + System.lineSeparator();

        assertEquals(expected, renderer.render(sheet));
    }

    @Test
    void rendersInput2Csv() throws IOException {
        Spreadsheet sheet = load("input2.csv");

        String expected = String.join(System.lineSeparator(),
                "      |      |          |   ",
                "Total:| 20.25|Sum test: |4.5",
                "      |      |Prod test:|9.0",
                "------|------|----------|   ",
                "Values|Factor|          |   ",
                "   2.0|   1.5|       3.0|   ",
                "   3.0|   2.0|       6.0|   ",
                "   4.5|   2.5|     11.25|   "
        ) + System.lineSeparator();

        assertEquals(expected, renderer.render(sheet));
    }

    @Test
    void rendersEmptySpreadsheetAsEmptyString() {
        assertEquals("", renderer.render(new Spreadsheet(0, 0)));
    }

    @Test
    void rightAlignsNumbersAndLeftAlignsStrings() throws IOException {
        Spreadsheet sheet = reader.read(new ByteArrayInputStream(
                "Hi,4\n#hl,#hl".getBytes(StandardCharsets.UTF_8)));

        String expected = String.join(System.lineSeparator(),
                "Hi|4.0",
                "--|---"
        ) + System.lineSeparator();

        assertEquals(expected, renderer.render(sheet));
    }

    private Spreadsheet load(String resource) throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream(resource);
        return reader.read(input);
    }
}
