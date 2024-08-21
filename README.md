# BBM204 - Spring 2024 - PA3: File Processing with HashMaps

This project is part of the BBM204 Software Engineering course. The goal is to implement a file processing system using HashMaps to efficiently handle large datasets. The program reads data from input files, processes the data using HashMaps, and generates the required outputs based on specific commands.

## Topics: 
Graphs - Connected Components, Minimum Spanning Trees

## Features
- **File Reading and Parsing:** Efficiently reads and parses large input files.
- **HashMap Usage:** Implements HashMaps to store and retrieve data quickly, ensuring efficient processing even with large datasets.
- **Command Processing:** Handles various commands to manipulate and query the stored data.
- **Output Generation:** Produces output files with the results of the processed commands.

## How to Run
1. **Compilation:** Compile the Java files using the following command:
   ```bash
   javac *.java
   ```

2. **Execution:** Run the program with the required input files:
   ```bash
   java Main input.txt commands.txt
   ```

3. **Output:** The program will generate output files based on the processed commands.

## Input Files
- **input.txt:** Contains the initial dataset to be processed.
- **commands.txt:** Lists the commands that will be executed on the dataset.

## Output Files
- The program generates output files as specified by the assignment, with results corresponding to each command in `commands.txt`.

## Testing
Test the implementation by modifying the `input.txt` and `commands.txt` files. Consider different edge cases, such as empty files or invalid commands, to ensure the program handles them correctly.

## Known Issues
- The program assumes correctly formatted input files and may not handle errors or unexpected formats gracefully.
- Performance could be affected if the dataset size exceeds typical memory limits, though HashMap usage should mitigate this.

## Future Improvements
- Enhance error handling for invalid inputs and command formats.
- Optimize memory usage for extremely large datasets.



