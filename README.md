# SlidePuzzleSolver

## Logic

## Insertion Order
* For a board `n x n` game board, the top `n-2` rows will be solved first. 
  * For each row in the top `n-2` columns, the `n`th piece will be placed in the `n-1`th piece first, before inserting the `n-1`.
  * By doing the above point, both the `n-1`th piece and the `n`th piece will be inserted at the same time.
* Solve for the `n-3` columns.
  * For each column, insert the `n`th piece into the `n-1` piece's position. 
  * Move the `n-1` piece so that it is to the right of the `n`th piece.
  * Slide the `n`th piece down one position and slide the `n-1`th piece to the right to solve.
* Solve for the final five pieces.

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
* Insert the last piece into the the second to last position in the line.
* If the insertion is row-wise:
  * Move the second-to-last piece directly below the the last piece.
    * If the second-to-last piece is in the last position of the line, use the Swapping Algorithm two swap with the piece directly below.
* If the insertion is column-wise:
  * Move the second-to-last piece directly below the the last piece.
  * If the second-to-last piece is in the last position of the line, use the Swapping Algorithm two swap with the piece directly right.
* Move the 0 piece to be in the last position of the line.
* Swap the 0 piece with the last piece.
* Swap the 0 piece with the second-to-last piece.

### Swapping Algorithm
1. Starting condition: `n`th piece is in the `n-1`th piece's position and vice versa.
2. Move the `n-1`th piece out two positions in the same direction (column-wise or row-wise).
3. Move the piece below/to the right of the `n-1`th position over 1.
4. Move the `n-1`th piece to the in the correct row.
5. Move the `n`th piece to be in `n-1`th position.
6. Return to the Basic Algorithm.


### Move 0 Function
This function will take 2 inputs.
1. The destination value
2. List of immovable pieces. This list should include all the values that are in the right position, the destination value.

Logic:
* Get the coordinates of the destination value.
* Determine the path needed to get 0 to the destination.
* Following the path instruction, swap zero with the next un-swapped value in the path
