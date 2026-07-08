package org.spreadsheet.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CellReferenceTest {

    @Test
    void parsesSingleLetterColumn() {
        CellReference reference = CellReference.parse("A3");

        assertEquals(2, reference.row());
        assertEquals(0, reference.column());
    }

    @Test
    void parsesColumnB() {
        CellReference reference = CellReference.parse("B5");

        assertEquals(4, reference.row());
        assertEquals(1, reference.column());
    }

    @Test
    void parsesColumnC() {
        CellReference reference = CellReference.parse("c6");

        assertEquals(5, reference.row());
        assertEquals(2, reference.column());
    }
}
