package org.spreadsheet.io;

import org.spreadsheet.cell.Cell;
import org.spreadsheet.cell.HorizontalLineCell;
import org.spreadsheet.cell.NumberCell;
import org.spreadsheet.cell.ProductCell;
import org.spreadsheet.cell.StringCell;
import org.spreadsheet.cell.SumCell;
import org.spreadsheet.model.Spreadsheet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public Spreadsheet read(Path inputFile) throws IOException {
        try (InputStream input = Files.newInputStream(inputFile)) {
            return read(input);
        }
    }

    public Spreadsheet read(InputStream input) throws IOException {

        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                rows.add(line.split(",",  -1));
            }
        }

        int maxColumns = rows.stream()
                .mapToInt( r -> r.length)
                .max()
                .orElse(0);

        Spreadsheet spreadsheet = new Spreadsheet(rows.size(), maxColumns);

        for (int row = 0; row < rows.size(); row++) {

            String [] values = rows.get(row);

            for (int col = 0; col < maxColumns; col++) {
                String value = col <  values.length ? values[col].trim() : "";
                spreadsheet.setCell(row, col, createCell(value, spreadsheet));
            }
        }

        return spreadsheet;
    }

    private Cell createCell(String value, Spreadsheet spreadsheet) {
        if (value.isBlank()) {
            return new StringCell(spreadsheet, "");
        }

        if (value.equals("#hl")) {
            return new HorizontalLineCell(spreadsheet);
        }

        if (value.startsWith("#(sum")) {
            return new SumCell(spreadsheet, value);
        }

        if (value.startsWith("#(prod")) {
            return new ProductCell(spreadsheet, value);
        }

        try {
            return new NumberCell(spreadsheet, Double.parseDouble(value));
        } catch (NumberFormatException ignored) { }


        return new StringCell(spreadsheet, value);

    }
}
