package sphe.com;

public class SpreadsheetRenderer {

    public String render(Spreadsheet spreadsheet) {

        int rows = spreadsheet.getRowCount();
        int cols = spreadsheet.getColCount();

        String[][] values = new String[rows][cols];

        // Determine a single width for every column
        int width = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                Cell cell = spreadsheet.getCell(row, col);

                String value = cell.display();
                values[row][col] = value;

                if (!(cell instanceof HorizontalCell)) {
                    width = Math.max(width, value.length());
                }
            }
        }

        StringBuilder out = new StringBuilder();

        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                Cell cell = spreadsheet.getCell(row, col);

                if (cell instanceof HorizontalCell) {

                    out.append("-".repeat(width));

                } else if (cell.isNumeric()) {

                    out.append(String.format(
                            "%" + width + "s",
                            values[row][col]
                    ));

                } else {

                    out.append(String.format(
                            "%-" + width + "s",
                            values[row][col]
                    ));
                }

                // Separator only between columns
                if (col < cols - 1) {
                    out.append("|");
                }
            }

            // Don't add a blank line after the last row
            if (row < rows - 1) {
                out.append(System.lineSeparator());
            }
        }

        return out.toString();
    }
}