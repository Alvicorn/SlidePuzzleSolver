# SlidePuzzleSolver

## Description
[15-Slide Puzzle](https://en.wikipedia.org/wiki/15_puzzle) is a game with 15 tiles in a box that
can hold 16 tiles. The goal for the game is to slide the tiles vertically or horizontally one
at a time such that it returns to its sorted order. The sorted order reads left to right, top 
to bottom in increasing order with the 1 tile in the top left corner.

This slide puzzle implementation extends the 15-Slide Puzzle by allowing the game board to
have the dimensions `N x N` for n > 1. Attached to this implementation is an algorithm that 
will solve a scrambled puzzle for the user. See below for the implementation.

[4X4 Slide Puzzle Solver Demo](https://youtu.be/vBfNDLpQZMc)

This project was built using IntelliJ IDEA and Open JDK Version 18.0.1.

## Puzzle Validity
To validate that the puzzle has a solution, an algorithm from 
[GeeksForGeeks](https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/) was used and 
extended for puzzle of size `N x N`.

## Solver Algorithm
The solver will complete the puzzle in the following repeating order:
* Highest unsolved top row
* Most left unsolved column

When solving the puzzle, the following solving states may appear:
1. Solving the last two pieces in a row
2. Solving the last two pieces in a column
3. Last two pieces in a row and flipped
4. Last two pieces in a column are flipped
5. General insertion

### Case 1: Solving the last two pieces in a row
1. Move the last piece in the correct position of the penultimate piece
2. Move the penultimate piece one row under its correct position
3. Move the empty space to the right of the last piece
4. Swap the empty space with the last piece
5. Swap the empty space with the penultimate piece

<details>
  <summary>Visual Example</summary>

|   |   |   |
|---|---|---|
| 1 |   | 8 |
| 4 | 3 | 5 |
| 7 | 2 | 6 |

|   |   |   |
|---|---|---|
| 1 | 3 | 8 |
| 4 |   | 5 |
| 7 | 2 | 6 |

|   |   |   |
|---|---|---|
| 1 | 3 | 8 |
| 4 | 2 | 5 |
| 7 |   | 6 |

|   |   |   |
|---|---|---|
| 1 | 3 |   |
| 4 | 2 | 8 |
| 7 | 6 | 5 |

|   |   |   |
|---|---|---|
| 1 |   | 3 |
| 4 | 2 | 8 |
| 7 | 6 | 5 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 4 |   | 8 |
| 7 | 6 | 5 |

</details>

### Case 2: Solving the last two pieces in a column
1. Move the last piece in the correct position of the penultimate piece
2. Move the penultimate piece one column to the right of its correct position
3. Move the empty space below of the last piece
4. Swap the empty space with the last piece
5. Swap the empty space with the penultimate piece

<details>
  <summary>Visual Example</summary>

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
|   | 7 | 4 |
| 8 | 5 | 6 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 7 |   | 4 |
| 8 | 5 | 6 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 7 | 4 |   |
| 8 | 5 | 6 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 7 | 4 | 6 |
|   | 8 | 5 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
|   | 4 | 6 |
| 7 | 8 | 5 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 4 |   | 6 |
| 7 | 8 | 5 |

</details>

### Case 3: Solving the last two pieces in a row that are flipped:
1. Move the last piece two rows below its valid position
2. Move the penultimate piece one row below its valid position
3. Move the last piece one row below its valid position
4. Move the penultimate piece two rows below its valid position
5. Apply Case 1

<details>
  <summary>Flipped Condition</summary>

|   |   |   |
|---|---|---|
| 1 | 3 | 2 |
| 4 |   | 5 |
| 7 | 8 | 6 |

|   |   |   |
|---|---|---|
| 1 | 2 |   |
| 4 | 3 | 5 |
| 7 | 8 | 6 |

|   |   |   |
|---|---|---|
| 1 | 2 | 5 |
| 4 | 3 |   |
| 7 | 8 | 6 |

|   |   |   |
|---|---|---|
| 1 | 2 | 5 |
| 4 |   | 3 |
| 7 | 8 | 6 |

|   |   |   |
|---|---|---|
| 1 | 2 | 5 |
| 4 | 8 | 3 |
| 7 | 6 |   |

|   |   |   |
|---|---|---|
| 1 | 2 | 5 |
| 4 | 8 |   |
| 7 | 6 | 3 |

|   |   |   |
|---|---|---|
| 1 |   | 5 |
| 4 | 2 | 8 |
| 7 | 6 | 3 |

|   |   |   |
|---|---|---|
| 1 | 5 | 8 |
| 4 | 2 |   |
| 7 | 6 | 3 |

|   |   |   |
|---|---|---|
| 1 | 5 | 8 |
| 4 | 2 | 3 |
| 7 | 6 |   |

|   |   |   |
|---|---|---|
| 1 | 5 | 8 |
| 4 | 2 | 3 |
| 7 |   | 6 |

|   |   |   |
|---|---|---|
| 1 | 5 | 8 |
| 4 |   | 3 |
| 7 | 2 | 6 |
</details>

<details>
  <summary>Blocked Condition</summary>

|   |   |   |
|---|---|---|
| 1 | 3 |   |
| 4 | 5 | 2 |
| 7 | 8 | 6 |

|   |   |   |
|---|---|---|
| 1 |   | 2 |
| 4 | 3 | 5 |
| 7 | 8 | 6 |

|   |   |   |
|---|---|---|
| 1 | 2 | 5 |
| 4 | 3 |   |
| 7 | 8 | 6 |

|   |   |   |
|---|---|---|
| 1 | 2 | 5 |
| 4 |   | 3 |
| 7 | 8 | 6 |

|   |   |   |
|---|---|---|
| 1 | 2 | 5 |
| 4 | 8 | 3 |
| 7 | 6 |   |

|   |   |   |
|---|---|---|
| 1 | 2 | 5 |
| 4 | 8 |   |
| 7 | 6 | 3 |

|   |   |   |
|---|---|---|
| 1 |   | 5 |
| 4 | 2 | 8 |
| 7 | 6 | 3 |

|   |   |   |
|---|---|---|
| 1 | 5 | 8 |
| 4 | 2 |   |
| 7 | 6 | 3 |

|   |   |   |
|---|---|---|
| 1 | 5 | 8 |
| 4 | 2 | 3 |
| 7 | 6 |   |

|   |   |   |
|---|---|---|
| 1 | 5 | 8 |
| 4 | 2 | 3 |
| 7 |   | 6 |

|   |   |   |
|---|---|---|
| 1 | 5 | 8 |
| 4 |   | 3 |
| 7 | 2 | 6 |
</details>

### Case 4: Solving the last two pieces in a row that are flipped:
1. Move the last piece two columns to the right of its valid position
2. Move the penultimate piece one column to the right of its valid position
3. Move the last piece one column to the right of its valid position
4. Move the penultimate piece two columns to the right of its valid position
5. Apply Case 2

<details>
  <summary>Flipped Condition</summary>

|   |   |    |
|---|---|----|
| 1 | 2 | 3  |
| 7 |   | 8  |
| 4 | 5 | 6  |

|   |   |    |
|---|---|----|
| 1 | 2 | 3  |
|   | 7 | 8  |
| 4 | 5 | 6  |

|   |   |    |
|---|---|----|
| 1 | 2 | 3  |
| 4 | 7 | 8  |
| 5 |   | 6  |

|   |   |    |
|---|---|----|
| 1 | 2 | 3  |
| 4 |   | 8  |
| 5 | 7 | 6  |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 4 | 8 | 6 |
| 5 | 7 |   |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 4 | 8 | 6 |
| 5 |   | 7 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 4 |   | 6 |
| 5 | 8 | 7 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
|   | 4 | 6 |
| 5 | 8 | 7 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 5 | 4 | 6 |
| 8 |   | 7 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 5 | 4 | 6 |
| 8 | 7 |   |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 5 | 4 |   |
| 8 | 7 | 6 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 5 |   | 4 |
| 8 | 7 | 6 |
</details>

<details>
  <summary>Blocked Condition</summary>

|   |   |    |
|---|---|----|
| 1 | 2 | 3  |
| 7 | 5 | 8  |
|   | 4 | 6  |

|   |   |    |
|---|---|----|
| 1 | 2 | 3  |
| 7 |   | 8  |
| 4 | 5 | 6  |

|   |   |    |
|---|---|----|
| 1 | 2 | 3  |
|   | 7 | 8  |
| 4 | 5 | 6  |

|   |   |    |
|---|---|----|
| 1 | 2 | 3  |
| 4 | 7 | 8  |
| 5 |   | 6  |

|   |   |    |
|---|---|----|
| 1 | 2 | 3  |
| 4 |   | 8  |
| 5 | 7 | 6  |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 4 | 8 | 6 |
| 5 | 7 |   |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 4 | 8 | 6 |
| 5 |   | 7 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 4 |   | 6 |
| 5 | 8 | 7 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
|   | 4 | 6 |
| 5 | 8 | 7 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 5 | 4 | 6 |
| 8 |   | 7 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 5 | 4 | 6 |
| 8 | 7 |   |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 5 | 4 |   |
| 8 | 7 | 6 |

|   |   |   |
|---|---|---|
| 1 | 2 | 3 |
| 5 |   | 4 |
| 8 | 7 | 6 |
</details>

### Case 5: General insertion
1. Get the shortest path from the target piece to its correct position without disturbing pieces that are already in the correct position
2. Move the empty space to be in the position of the first piece in path
3. Swap the empty space and the target piece
4. Repeat until the target piece is in the correct position

## Solver Validity
Unit tests were added to test all valid `2 X 2` and `3 X 3` game states. With `2 X 2` game states being
the base case and `3 X 3` game states being our induction hypothesis, by induction, the solver can solve 
any puzzle > 2 (given infinite time and computing power) since no other solving cases will appear.