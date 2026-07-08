package sphe.com.cell;

import sphe.com.model.CellReference;
import sphe.com.model.Spreadsheet;

public class ProductCell extends FormulaCell {

    public ProductCell(Spreadsheet spreadsheet, String formula) {
        super(spreadsheet, formula);
    }

    @Override
    public String display() {
        return Double.toString(getValue());
    }

    @Override
    protected double getValue() {
        double product = 1;
        for (CellReference ref : references) {
            product *= numericalValue(spreadsheet.getCell(ref.row(), ref.column()));
        }
        return product;
    }


}
