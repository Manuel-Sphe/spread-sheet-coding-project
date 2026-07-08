package sphe.com.renderer;

import sphe.com.cell.Cell;
import sphe.com.cell.HorizontalLineCell;
import sphe.com.model.Spreadsheet;

public class SpreadsheetRenderer {

    public String render(Spreadsheet spreadsheet) {

        int rows = spreadsheet.getRowCount();
        int cols = spreadsheet.getColumnCount();

        String[][] values = new String[rows][cols];
        int[] columnWidths = new int[cols];

        // Pass 1: evaluate cells and determine column widths
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                Cell cell = spreadsheet.getCell(row, col);

                values[row][col] = cell.display();

                // Horizontal lines don't contribute to width
                if (!(cell instanceof HorizontalLineCell)) {
                    columnWidths[col] = Math.max(
                            columnWidths[col],
                            values[row][col].length());
                }
            }
        }

        // Pass 2: render
        StringBuilder output = new StringBuilder();

        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                Cell cell = spreadsheet.getCell(row, col);

                if (cell instanceof HorizontalLineCell) {

                    output.append("-".repeat(columnWidths[col]));

                } else if (cell.isNumeric()) {

                    output.append(String.format(
                            "%" + columnWidths[col] + "s",
                            values[row][col]));

                } else {

                    output.append(String.format(
                            "%-" + columnWidths[col] + "s",
                            values[row][col]));
                }

                if (col < cols - 1) {
                    output.append("|");
                }
            }

            output.append(System.lineSeparator());
        }

        return output.toString();
    }
}