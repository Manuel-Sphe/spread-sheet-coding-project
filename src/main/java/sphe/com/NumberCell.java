package sphe.com;

public class NumberCell extends Cell {

    private final double value;

    public NumberCell(Spreadsheet spreadsheet, double value) {
        super(spreadsheet);
        this.value = value;
    }

    @Override
    public String display() {
        return Double.toString(value);
    }

    @Override
    public boolean isNumeric() {
        return true;
    }
    
    public double getValue() {
        return value;
    }



}
