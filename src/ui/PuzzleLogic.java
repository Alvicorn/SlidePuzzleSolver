/**
 * PuzzleLogic.java
 *
 * Handle the logic behind the puzzle
 */

package ui;

import java.util.ArrayList;
import java.util.Collections;


public class PuzzleLogic {
    private final PuzzleValidator validator;
    private final int dimension;
    private final int size;
    private final ArrayList<Integer> puzzleSolution;
    private ArrayList<Integer> puzzleState;
    private int emptyCell;

    /**
     * For the user's game.
     *
     * @param dimension size of the board.
     */
    public PuzzleLogic(int dimension) {
        this.validator = new PuzzleValidator();
        this.dimension = dimension;
        this.size = dimension * dimension;
        this.emptyCell = this.size;

        this.puzzleSolution = new ArrayList<>(this.size);
        this.puzzleState = null;

        // Generate the puzzle end state
        for (int i = 1; i < this.size; i++) {
            this.puzzleSolution.add(i);
        }
        this.puzzleSolution.add(0);
        System.out.println("Final Puzzle State:" + this.puzzleSolution);
    }

    public int getSize() {
        return this.size;
    }

    public void setEmptyCell(int emptyCell) {
        this.emptyCell = emptyCell;
    }

    public int getEmptyCell() {
        return this.emptyCell;
    }

    public ArrayList<Integer> getPuzzleState() {
        return this.puzzleState;
    }

    /**
     * Generate a solvable in the form of a 1D arraylist.
     *
     * @return Solvable puzzle.
     */
    public ArrayList<Integer> generatePuzzle() {
        for (boolean isSolvable = false; !isSolvable;) {
            // create ordered list
            this.puzzleState = new ArrayList<>(this.size);
            for (int i = 0; i < this.size; i++) {
                this.puzzleState.add(i);
            }
            Collections.shuffle(this.puzzleState);
            isSolvable = this.validator.isSolvable(this.puzzleState, this.dimension);
        }
//        System.out.println("Initial Board state:");
//        this.printPuzzle();
        return this.puzzleState;
    } // end of generatePuzzle()

    /**
     * Validate that moving the piece and pieceIndex is valid. A valid move
     * exists when the piece is exactly 1 block away (excluding diagonal).
     * @param pieceIndex Index of the piece that is being moved.
     * @return True is the move is valid and update the puzzle state false otherwise.
     */
    public boolean validMove (int pieceIndex) {
        int pieceRow = pieceIndex / this.dimension;
        int pieceCol = pieceIndex % this.dimension;

        int blankRow = this.emptyCell / this.dimension;
        int blankCol = this.emptyCell % this.dimension;

        boolean validMove = false;

        if (pieceRow - blankRow == 1 && // piece is one row above the blank
                pieceCol - blankCol == 0 ) { // piece is in the same column as blank
            validMove = true; // move piece down

        } else if (pieceRow - blankRow == -1 && // piece is one row below the blank
                pieceCol - blankCol == 0 ) { // piece is in the same column as blank
            validMove = true; // move piece up

        } else if (pieceRow - blankRow == 0 && // piece is in the same row as blank
                pieceCol - blankCol == 1 ) { // piece is to the right of blank
            validMove = true;

        } else if (pieceRow - blankRow == 0 && // piece is in the same row as blank
                pieceCol - blankCol == -1 ) { // piece is to the left of blank
            validMove = true;
        }

        if (validMove) { // update the puzzle state and emptyCell
            Collections.swap(this.puzzleState, pieceIndex, this.emptyCell);
            this.emptyCell = pieceIndex;
//            this.printPuzzle();
        }
        return validMove;
    } // end of validMove()

    /**
     * Checks where game is finished or not.
     *
     * @return true, if the board is in final state
     *         false, if the board is not in final state.
     */
    public boolean isSolved() {
        return this.puzzleSolution.equals(this.puzzleState);
    } // end of isSolved()

    /**
     * Print the puzzle to the console.
     */
    private void printPuzzle() {
        System.out.println("\nPuzzle State:\t" + this.puzzleState.toString() + "\n");

        for (int i = 0; i < this.puzzleState.size(); i++) {
            if (i % this.dimension == 0) {
                System.out.println();
            }
            System.out.print("\t" + this.puzzleState.get(i) + "\t");
        }
    } // end of printPuzzle()

} // end of PuzzleLogic.java