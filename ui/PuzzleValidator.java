/**
 * PuzzleValidator.java
 *
 * Check if the puzzle is solvable.
 *
 * Solution from https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
 * @author Aditya Goel
 *
 * Logic:
 *      Let N be the grid width. We assume that the board will be N * N
 *
 *      An inversion is defined as:
 *          a pair of numbers, (a,b) that are in a 1D array where a < b, but b appears
 *          in the list before a (i.e. (b,a) is the inversion of (a,b)
 *
 *      If N is odd:
 *          the puzzle instance is solvable if:
 *              number of inversions is even in the input state.
 *
 *      If N is even:
 *          the puzzle instance is solvable if:
 *              the blank is on an even row counting from the bottom
 *              (second-last, fourth-last, etc.) and number of inversions is odd.
 *              or
 *              the blank is on an odd row counting from the bottom
 *              (last, third-last, fifth-last, etc.) and number of inversions is even.
 *
 *      For all other cases, the puzzle instance is not solvable.
 */
package ui;

import java.util.ArrayList;


public class PuzzleValidator {

    public PuzzleValidator() {}

    /**
     * Check that the board is a square
     * @param puzzleState ArrayList representation of the game board
     * @param dimension side length of the game board
     * @return true if valid, false otherwise
     */
    private boolean validDimension(ArrayList<Integer> puzzleState, int dimension) {
        return puzzleState.size() / dimension == dimension;
    } // end of validDimension

    /**
     * Find the row of the blank space
     * A blank space is indicated by a zero. We assume the elements in the arraylist are unique
     * @param puzzleState ArrayList representation of the game board
     * @param dimension side length of the game board
     * @return the row of the blank position, -1 otherwise
     */
    private int findBlankRow(ArrayList<Integer> puzzleState, int dimension) {
        int blankIndex = puzzleState.indexOf(0);
        return (blankIndex == -1) ? blankIndex : blankIndex / dimension;
    } // end of findBlankRow()

    /**
     * Count the number of inversions in the puzzle
     * @param puzzleState ArrayList representation of the game board
     * @param dimension side length of the game board
     * @return the number of inversions in the puzzle
     */
    private int getInversionCount(ArrayList<Integer> puzzleState, int dimension) {
        int count = 0;
        int size = dimension * dimension;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                // count pairs(arr[i], arr[j]) such that i < j but arr[i] > arr[j]
                if (puzzleState.get(j) != 0 &&
                        puzzleState.get(i) != 0 &&
                        puzzleState.get(i) > puzzleState.get(j)) {
                    count++;
                }
            }
        }
        return count;
    } // end of get InversionCount()

    /**
     * Check if the puzzle is solvable
     * @param puzzleState ArrayList representation of the game board
     * @param dimension side length of the game board
     * @return true if the puzzle is valid, false otherwise
     */
    public boolean isSolvable(ArrayList<Integer> puzzleState, int dimension) {
        if (!this.validDimension(puzzleState, dimension)) {
            return false;
        }
        int inversionCount = this.getInversionCount(puzzleState, dimension);

        if (dimension % 2 == 1) { // puzzle has an odd dimension
            return inversionCount % 2 == 0;
        } else { // puzzle has an even dimension
            int blankRow = findBlankRow(puzzleState, dimension);
            if (blankRow % 2 == 1) { // blank is in an odd row
                return inversionCount % 2 == 0;
            } else { // blank is in an even row
                return inversionCount % 2 == 1;
            }
        }
    } // end of isSolvable()

} // end of PuzzleValidator.java
