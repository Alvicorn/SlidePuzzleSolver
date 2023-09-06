# SlidePuzzleSolver

## Algorithm
* Get the current puzzle state
* Check the state of the puzzle and identify the first piece that is out of position
* For each row:
  * For each position in the row:
    * While not in place:
      * If this is the last piece:
        * Set the valid position to be the position directly below its valid position
        * Set the current piece to be the second to last piece
      * If this is the second to last piece:
        * Set the current piece to be the last piece
        
      * Move the blank spot to be adjacent to the piece that is out of place
        * Prioritize the piece to be in the correct column first
      * Check if the piece is in place
    * Move the blank spot to be in the last position in the row
    * Move the blank spot to the left and then move the blank spot down
  * For each position the in column:
    * Identify the position of the piece that is out of place
    * While not in place:
    * If this is the last piece:
        * Set the valid position to be the position directly right its valid position
    * If this is the second to last piece:
        * Set the valid position of the last piece to the position of the second to last piece
        * Set the current piece to be the last piece
    * Move the blank spot to be adjacent to the piece that is out of place
        * Prioritize the piece to be in the correct row first
    * Check if the piece is in place
    * Move the blank spot to be in the last position in the column
    * Move the blank spot to the down and then move the blank spot right