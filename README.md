# spread-sheet-coding-project

A Java 17 command-line application that reads a CSV spreadsheet definition, evaluates all cell formulas, and writes the formatted result to a text file.

## Requirements

| Requirement | Status |
|-------------|--------|
| Java command-line app with input + output file parameters | Yes |
| Input: CSV spreadsheet definition | Yes |
| Output: formatted text file after calculation | Yes |
| Standard Java 17 API only (no 3rd-party runtime deps) | Yes |
| Unit tests for all functionality | Yes |
| Gradle build + test runner | Yes |

## Prerequisites

- Java 17+ (Gradle toolchain can provision this automatically)
- Gradle 8.5+ (wrapper included)

## Usage

```bash
./gradlew build

java -jar build/libs/spread-sheet-coding-project-1.0-SNAPSHOT.jar input.csv output.txt
```

Or with Gradle:

```bash
./gradlew run --args="src/main/resources/input.csv output.txt"
```

### Arguments

1. **input.csv** — path to the CSV spreadsheet definition
2. **output.txt** — path where the rendered spreadsheet is written

Example:

```bash
./gradlew run --args="src/main/resources/input.csv build/output.txt"
cat build/output.txt
```

## Build & Test

```bash
./gradlew test
```

Test report: `build/reports/tests/test/index.html`

## Package Structure

```
org.spreadsheet
├── Main.java                    # CLI entry point (input file, output file)
├── SpreadsheetApplication.java  # Orchestrates read → render → write
├── io/
│   └── CsvReader                # Reads CSV from Path or InputStream
├── model/
│   ├── Spreadsheet              # Cell grid
│   └── CellReference            # A1-style coordinate parsing
├── cell/
│   ├── Cell                     # Abstract base
│   ├── StringCell
│   ├── NumberCell
│   ├── FormulaCell              # Abstract formula base
│   ├── SumCell
│   ├── ProductCell
│   └── HorizontalLineCell
└── renderer/
    └── SpreadsheetRenderer      # Column widths + formatted output
```

## Class Diagram

```mermaid
classDiagram
    direction TB

    namespace org_spreadsheet {
        class Main {
            +main(String[] args)$
        }
        class SpreadsheetApplication {
            -CsvReader csvReader
            -SpreadsheetRenderer renderer
            +run(Path inputFile, Path outputFile)
        }
    }

    namespace org_spreadsheet_io {
        class CsvReader {
            +read(Path inputFile) Spreadsheet
            +read(InputStream input) Spreadsheet
            -createCell(String value, Spreadsheet spreadsheet) Cell
        }
    }

    namespace org_spreadsheet_model {
        class Spreadsheet {
            -Cell[][] cells
            +Spreadsheet(int rows, int cols)
            +getCell(int row, int col) Cell
            +setCell(int row, int col, Cell cell)
            +getRowCount() int
            +getColumnCount() int
        }
        class CellReference {
            <<record>>
            +row() int
            +column() int
            +parse(String ref)$ CellReference
        }
    }

    namespace org_spreadsheet_renderer {
        class SpreadsheetRenderer {
            +render(Spreadsheet spreadsheet) String
        }
    }

    namespace org_spreadsheet_cell {
        class Cell {
            <<abstract>>
            #Spreadsheet spreadsheet
            +display() String
            +isNumeric() boolean
        }
        class FormulaCell {
            <<abstract>>
            #List~CellReference~ references
            +display() String
            +getValue() double
            #numericalValue(Cell cell) double
            -parseReferences(String formula) List~CellReference~
        }
        class StringCell {
            -String value
            +display() String
        }
        class NumberCell {
            -double value
            +display() String
            +getValue() double
        }
        class SumCell {
            +getValue() double
        }
        class ProductCell {
            +getValue() double
        }
        class HorizontalLineCell {
            +display() String
        }
    }

  Main --> SpreadsheetApplication : delegates to
  SpreadsheetApplication --> CsvReader : reads input
  SpreadsheetApplication --> SpreadsheetRenderer : renders
  SpreadsheetApplication ..> Spreadsheet : produces

  CsvReader --> Spreadsheet : builds
  CsvReader ..> Cell : creates

  Spreadsheet "1" *-- "many" Cell : contains
  Spreadsheet --> CellReference : used by formulas

  SpreadsheetRenderer --> Spreadsheet : reads

  Cell <|-- StringCell
  Cell <|-- NumberCell
  Cell <|-- FormulaCell
  Cell <|-- HorizontalLineCell
  FormulaCell <|-- SumCell
  FormulaCell <|-- ProductCell

  FormulaCell --> CellReference : parses
  FormulaCell --> Spreadsheet : resolves cells
```

## Processing Flow

```mermaid
flowchart LR
    A[input.csv] --> B[Main]
    B --> C[SpreadsheetApplication]
    C --> D[CsvReader]
    D --> E[Spreadsheet + Cells]
    C --> F[SpreadsheetRenderer]
    F --> G[Formatted text]
    G --> H[output.txt]

    subgraph evaluation [Lazy Evaluation]
        I[cell.display]
        J[FormulaCell.getValue]
        K[resolve referenced cells]
        I --> J --> K
    end

    F --> I
```

## Cell Types

| Raw value | Class | Notes |
|-----------|-------|-------|
| `Hello` | `StringCell` | Left-aligned text |
| `2`, `4.5` | `NumberCell` | Right-aligned number |
| `#hl` | `HorizontalLineCell` | Dashes fill column width |
| `#(sum A1 B2)` | `SumCell` | Sum of referenced cells |
| `#(prod A1 B2)` | `ProductCell` | Product of referenced cells |

## Example Files

| File | Description |
|------|-------------|
| `src/main/resources/input.csv` | Primary example spreadsheet |
| `src/main/resources/input2.csv` | Alternate layout (formulas before data) |

## Design

- **No third-party runtime dependencies** — production code uses only the Java 17 standard library.
- **JUnit 5** is used at test time only (via Gradle).
- **`SpreadsheetApplication`** reads the input CSV, renders the spreadsheet, and writes the output file.
- **`FormulaCell`** evaluates formulas lazily; cells can reference rows defined later in the CSV.

## License

Coding exercise / reference implementation.
