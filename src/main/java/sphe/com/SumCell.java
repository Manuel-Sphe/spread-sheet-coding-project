package sphe.com;

public class SumCell extends FormulaCell {

    protected SumCell(Spreadsheet spreadsheet, String formula) {
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
