package sphe.com.cell;

import sphe.com.model.CellReference;
import sphe.com.model.Spreadsheet;

import java.util.ArrayList;
import java.util.List;

public abstract class FormulaCell extends Cell {

    protected final List<CellReference> references;

    protected FormulaCell(Spreadsheet spreadsheet, String formula) {
        super(spreadsheet);
        this.references = parseReferences(formula);
    }

    @Override
    public String display() {
        return Double.toString(getValue());
    }

    @Override
    public boolean isNumeric() {
        return true;
    }

    protected abstract double getValue();

    private List<CellReference> parseReferences(String formula) {

        // #(sum A3 B3 C3)
        // #(prod A3 B3)

        int start = formula.indexOf("(");
        int end = formula.indexOf(")");

        String body = formula.substring(start + 1, end);

        String [] tokens = body.split("\\s+");

        List<CellReference> refs = new ArrayList<>();

        // tokens[0] is "sum" or "prod"
        for (int i = 1; i < tokens.length; i++) {
            refs.add(
                    CellReference.parse(tokens[i])
            );
        }

        return refs;
    }

    protected double numericalValue(Cell cell) {

        if (cell instanceof NumberCell number) {
            return number.getValue();
        }

        if (cell instanceof FormulaCell formula) {
            return formula.getValue();
        }

        throw new IllegalArgumentException("Cell is not numeric.");
    }
}
