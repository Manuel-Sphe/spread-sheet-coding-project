package org.spreadsheet;

import org.spreadsheet.io.CsvReader;
import org.spreadsheet.model.Spreadsheet;
import org.spreadsheet.renderer.SpreadsheetRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SpreadsheetApplication {

    private final CsvReader csvReader;
    private final SpreadsheetRenderer renderer;

    public SpreadsheetApplication() {
        this(new CsvReader(), new SpreadsheetRenderer());
    }

    SpreadsheetApplication(CsvReader csvReader, SpreadsheetRenderer renderer) {
        this.csvReader = csvReader;
        this.renderer = renderer;
    }

    public void run(Path inputFile, Path outputFile) throws IOException {
        Spreadsheet sheet = csvReader.read(inputFile);
        String output = renderer.render(sheet);
        Files.writeString(outputFile, output);
    }
}
