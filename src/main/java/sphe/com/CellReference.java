package sphe.com;

public record CellReference(int row, int column) {

    public static CellReference parse(String ref) {

        ref = ref.trim().toUpperCase();

        int split = 0;

        while (Character.isLetter(ref.charAt(split))) {
            split++;
        }

        String columnPart = ref.substring(0, split);
        String rowPart = ref.substring(split);

        int column = 0;

        for (char c : columnPart.toCharArray()) {
            column = column * 26 + c - 'A' + 1;
        }

        column--;

        int row = Integer.parseInt(rowPart) -1 ;
        return new CellReference(row, column);
    }
}
