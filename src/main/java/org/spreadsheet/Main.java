package org.spreadsheet;

import java.io.IOException;
import java.nio.file.Path;

public class Main {

    private static final String USAGE = "Usage: java -jar spread-sheet-coding-project.jar <input.csv> <output.txt>";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println(USAGE);
            System.exit(1);
        }

        try {
            new SpreadsheetApplication().run(Path.of(args[0]), Path.of(args[1]));
        } catch (IOException e) {
            System.err.println("Failed to process spreadsheet: " + e.getMessage());
            System.exit(1);
        }
    }
}
