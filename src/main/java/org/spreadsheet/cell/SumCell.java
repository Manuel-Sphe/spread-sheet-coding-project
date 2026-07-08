package org.spreadsheet.cell;

import org.spreadsheet.model.CellReference;
import org.spreadsheet.model.Spreadsheet;

public class SumCell extends FormulaCell {

    public SumCell(Spreadsheet spreadsheet, String formula) {
        super(spreadsheet, formula);
    }

    @Override
    public double getValue() {

        double sum = 0;

        for (CellReference ref : references) {
            sum += numericalValue(spreadsheet.getCell(ref.row(), ref.column()));
        }

        return sum;
    }

}
