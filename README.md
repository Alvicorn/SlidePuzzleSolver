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

### Basic Algorithm
1. Get the current puzzle state.
2. Determine the path needed to set the target block to be in the correct column.
   1. For each move, determine the path that the empty block must take in order to be in the target block's path, without messing up the pieces that are already in place.
   2. Move the target block.
3. Determine the path needed to set the target block to be in the correct row.
   1. For each move, determine the path that the empty block must take in order to be in the target block's path, without messing up the pieces that are already in place.
   2. Move the target block.
4. Address any special conditions.
5. Mark the target block as in-place.

### Swapping Algorithm
1. Starting condition: `n`th piece is in the `n-1`th piece's position and vice versa.
2. Move the `n-1`th piece out two positions in the same direction (column-wise or row-wise).
3. Move the piece below/to the right of the `n-1`th position over 1.
4. Move the `n-1`th piece to the in the correct row.
5. Move the `n`th piece to be in `n-1`th position.
6. Return to the Basic Algorithm.

### End Condition Swapping

### Special Conditions
1. When solving for the first `n-2` rows, the `n-1`th piece is already in the correct position but the `n`th piece is not in the right position. --> Swapping Algorithm.
2. End condition swapping. 
