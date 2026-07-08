package sphe.com;

import sphe.com.io.CsvReader;
import sphe.com.model.Spreadsheet;
import sphe.com.renderer.SpreadsheetRenderer;

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