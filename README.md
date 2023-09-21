# SlidePuzzleSolver

## Logic

## Insertion Order
* For a `n x n` game board, the top `n-2` rows will be solved first. 
  * Solve from left to right.
* Solve the last two rows, one column at a time.

### Main Algorithm
While the puzzle is not solved:
* Get the current puzzle state and find the first piece (piece with the smallest value) in the incorrect position. 
  * If the correct position of the target piece is one of last two rows:
    * Execute the End-Of-Line Insertion Algorithm (column-wise).
  * If the correct position of the target piece is the second to last piece for its row:
    * Execute the End-of-Line Insertion Algorithm (row-wise).
  * If the target piece is not of the above:
    * Execute the Insertion Algorithm.

### Insertion Algorithm
Condition: Inserting a piece that is not the last two pieces of a row or not one of the final three pieces of the game.

Logic:
* Determine the path needed to set the target block to the correct position.
* If the piece adjacent to the target block is 0, swap positions and continue to the next iteration.
* If the piece adjacent to the target block is not 0, use the Move 0 Function to make it so.
* Swap the target piece with 0.

### End-Of-Line Insertion Algorithm
Condition: When there is only two pieces left to insert in a line. A line can be a row or column.

Logic:
* Insert the last piece into the second to last position in the line.
* If the insertion is row-wise:
  * Move the second-to-last piece directly below the last piece.
    * If the second-to-last piece is in the last position of the line, use the Swapping Algorithm two swap with the piece directly below.
* If the insertion is column-wise:
  * Move the second-to-last piece directly below the last piece.
  * If the second-to-last piece is in the last position of the line, use the Swapping Algorithm two swap with the piece directly right.
* Move the 0 piece to be in the last position of the line.
* Swap the 0 piece with the last piece.
* Swap the 0 piece with the second-to-last piece.

### Swapping Algorithm
Condition: `n`th piece is in the `n-1`th piece's position and vice versa.

Logic:
* Move the `n-1`th piece out two positions in the same direction (column-wise or row-wise). 
* Move the piece below/to the right of the `n-1`th position over 1. 
* Move the `n-1`th piece to the in the correct row. 
* Move the `n`th piece to be in `n-1`th position. 

### Move 0 Function
This function will take 2 inputs.
1. The destination value
2. List of immovable pieces. This list should include all the values that are in the right position, the destination value.

Logic:
* Get the coordinates of the destination value.
* Determine the path needed to get 0 to the destination.
* Following the path instruction, swap zero with the next un-swapped value in the path.
