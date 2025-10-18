# CalculatoR

A fully-featured calculator application built with Java Swing, inspired by the Windows Calculator. This calculator supports simple arithmetic, scientific calculations, and function graphing.

## Features

### Simple Calculator
- Basic arithmetic operations (+, -, *, /)
- Parentheses support
- Clear and backspace functionality
- Decimal number support

### Scientific Calculator
- All simple calculator features
- Trigonometric functions (sin, cos, tan, asin, acos, atan)
- Logarithmic functions (ln, log)
- Power and root operations (^, sqrt)
- Exponential function (exp)
- Absolute value (abs)
- Mathematical constants (pi, e)

### Graph Calculator
- Plot mathematical functions
- Customizable x-axis range
- Adjustable sample density
- Real-time function evaluation
- Support for all scientific functions in graphs

## Technical Details

- **Language**: Java 17
- **GUI Framework**: Java Swing
- **Build Tool**: Maven
- **Testing**: JUnit 5
- **Architecture**: Clean separation of concerns with dedicated packages

## Project Structure

```
src/
├── main/java/com/calculator/
│   ├── CalculatorApp.java          # Main application entry point
│   ├── core/
│   │   └── ExpressionEvaluator.java # Mathematical expression parser and evaluator
│   └── ui/
│       ├── CalculatorFrame.java    # Main application window
│       ├── SimplePanel.java         # Simple calculator interface
│       ├── ScientificPanel.java     # Scientific calculator interface
│       └── GraphPanel.java          # Function graphing interface
└── test/java/com/calculator/
    ├── AppTest.java                 # Core functionality tests
    └── ui/                          # UI component tests
```

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Quick Start (PowerShell Scripts)
The easiest way to run the calculator is using the provided PowerShell scripts:

```powershell
# Run the calculator (default)
.\run-calculator.ps1

# Run tests
.\run-calculator.ps1 -Action test

# Build the project
.\run-calculator.ps1 -Action build

# Clean the project
.\run-calculator.ps1 -Action clean

# Create executable JAR
.\run-calculator.ps1 -Action package
```

### Alternative Scripts
For different operating systems:

**Windows Batch Script:**
```cmd
# Run the calculator
run-calculator.bat
```

### Manual Maven Commands
If you prefer to use Maven directly:

```bash
# Build the project
mvn clean compile

# Run tests
mvn test

# Run the application
mvn exec:java

# Create executable JAR
mvn clean package
java -jar target/calculator-app-1.0.0.jar
```

## Usage

### Simple Calculator
1. Use number buttons (0-9) to input numbers
2. Use operation buttons (+, -, *, /) for arithmetic
3. Use parentheses ( and ) for grouping
4. Press = to calculate the result
5. Use C to clear and ← to backspace

### Scientific Calculator
1. All simple calculator features
2. Use function buttons for mathematical operations:
   - `sin`, `cos`, `tan` for trigonometric functions
   - `ln`, `log` for logarithmic functions
   - `sqrt` for square root
   - `^` for power operations
   - `abs` for absolute value
   - `pi` for π constant
3. Functions can be combined: `sin(pi/2)`, `sqrt(16)`, etc.

### Graph Calculator
1. Enter a function in the "f(x) = " field (e.g., `sin(x)`, `x^2`, `cos(x)`)
2. Set the x-axis range (xMin and xMax)
3. Adjust the number of samples for graph smoothness
4. Click "Plot" to generate the graph
5. The graph will display the function over the specified range

## Supported Functions

### Mathematical Functions
- `sin(x)`, `cos(x)`, `tan(x)` - Trigonometric functions
- `asin(x)`, `acos(x)`, `atan(x)` - Inverse trigonometric functions
- `ln(x)` - Natural logarithm
- `log(x)` - Base-10 logarithm
- `sqrt(x)` - Square root
- `exp(x)` - Exponential function
- `abs(x)` - Absolute value

### Constants
- `pi` - π (3.14159...)
- `e` - Euler's number (2.71828...)

### Operations
- `+`, `-`, `*`, `/` - Basic arithmetic
- `^` - Power operation
- `()` - Parentheses for grouping

## Testing

The project includes comprehensive unit tests covering:
- Basic arithmetic operations
- Complex expressions with parentheses
- Mathematical functions
- Error handling
- UI component functionality

Run tests with:
```bash
mvn test
```

## Error Handling

The calculator gracefully handles:
- Invalid expressions
- Division by zero (returns Infinity)
- Mismatched parentheses
- Unknown functions
- Syntax errors

## Future Enhancements

Potential improvements could include:
- Memory functions (M+, M-, MR, MC)
- History of calculations
- More mathematical functions
- Export graphs as images
- Keyboard shortcuts
- Themes and customization

## License

This project is open source.
