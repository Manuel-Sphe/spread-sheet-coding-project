package org.spreadsheet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpreadsheetApplicationTest {

    @TempDir
    Path tempDir;

    @Test
    void writesRenderedSpreadsheetToOutputFile() throws IOException {
        Path input = tempDir.resolve("input.csv");
        Path output = tempDir.resolve("output.txt");

        Files.writeString(input, String.join(System.lineSeparator(),
                "Values,Factor",
                "#hl,#hl",
                "2,1.5,#(prod A3 B3)",
                "3,2,#(prod A4 B4)",
                "4.5,2.5,#(prod A5 B5)",
                ",,#hl",
                ",Total:,#(sum C3 C4 C5)",
                "Sum test:,#(sum A3 B5)",
                "Prod test:,#(prod A3 A4 B3)"
        ));

        new SpreadsheetApplication().run(input, output);

        assertTrue(Files.exists(output));
        String rendered = Files.readString(output);
        assertTrue(rendered.contains("Values"));
        assertTrue(rendered.contains("20.25"));
        assertTrue(rendered.contains("Prod test:|   9.0|"));
    }

    @Test
    void processesInput2LayoutFromFile() throws IOException {
        Path input = tempDir.resolve("input2.csv");
        Path output = tempDir.resolve("output2.txt");

        try (InputStream in = getClass().getClassLoader().getResourceAsStream("input2.csv")) {
            Files.copy(in, input);
        }

        new SpreadsheetApplication().run(input, output);

        String rendered = Files.readString(output);
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

        assertEquals(expected, rendered);
    }
}
