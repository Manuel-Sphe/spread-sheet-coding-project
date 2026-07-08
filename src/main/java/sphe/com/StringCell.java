package sphe.com;

public class StringCell extends Cell {

    private final String value;

    public StringCell(Spreadsheet spreadsheet, String value) {
        super(spreadsheet);
        this.value = value;
    }


    @Override
    public String display() {
        return value;
    }
}
