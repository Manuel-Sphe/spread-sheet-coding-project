package org.spreadsheet;

import org.spreadsheet.io.CsvReader;
import org.spreadsheet.model.Spreadsheet;
import org.spreadsheet.renderer.SpreadsheetRenderer;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {


        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("input.csv");

        Spreadsheet sheet = new CsvReader().read(inputStream);

        String out = new SpreadsheetRenderer().render(sheet);

        System.out.println(out);
    }
}