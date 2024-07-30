/**
 * TestSolver.java
 *
 * @author Alvin Tsang
 *
 * Unit tests for Solver.java
 */
package solver.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import solver.Solver;
import ui.PuzzleValidator;


class TestSolver {

    @Test
    public void testTwoDimensionGameBoard() {
        Solver solver = new Solver(2);
        PuzzleValidator validator = new PuzzleValidator();

        ArrayList<ArrayList<Integer>> puzzleStates = new ArrayList<>();

        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 0));
        generatePermutations(list, 0, puzzleStates);

        List<ArrayList<Integer>> validPuzzleStates = puzzleStates.stream()
                .filter(puzzleState -> validator.isSolvable(puzzleState, 2)).toList();

        for (ArrayList<Integer> puzzleState : validPuzzleStates) {
            System.out.println("----- Testing Puzzle: " + puzzleState + " -----");
            for (int action : solver.solve(new ArrayList<>(puzzleState))) {
                executeAction(puzzleState, action, 2);
            }
            assertArrayEquals(new ArrayList<>(Arrays.asList(1, 2, 3, 0)).toArray(), puzzleState.toArray());
        }
    } // end of testTwoDimensionGameBoard()

    @Test
    public void testThreeDimensionGameBoard() {
        Solver solver = new Solver(3);
        PuzzleValidator validator = new PuzzleValidator();

        ArrayList<ArrayList<Integer>> puzzleStates = new ArrayList<>();

        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 0));
        generatePermutations(list, 0, puzzleStates);

        List<ArrayList<Integer>> validPuzzleStates = puzzleStates.stream()
                .filter(puzzleState -> validator.isSolvable(puzzleState, 3)).toList();

        int testCase = 0;
        HashSet<ArrayList<Integer>> solvedStates = new HashSet<>();

        for (ArrayList<Integer> puzzleState : validPuzzleStates) {
            System.out.println("----- Testing Puzzle " + testCase + " : " + puzzleState + " -----");
            ArrayList<ArrayList<Integer>> solvedTransitionStates = new ArrayList<>();
            solvedTransitionStates.add(new ArrayList<>(puzzleState));
            for (int action : solver.solve(new ArrayList<>(puzzleState))) {
                executeAction(puzzleState, action, 3);
                solvedTransitionStates.add(new ArrayList<>(puzzleState));
                if (solvedStates.contains(puzzleState)) {
                    puzzleState = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 0));
                    break;
                }
            }
            assertArrayEquals(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 0)).toArray(), puzzleState.toArray());
            solvedStates.addAll(solvedTransitionStates);
            testCase++;
        }
    } // end of testThreeDimensionGameBoard()

    /**
     * Execute an action on the puzzle.
     * @param puzzleState current game board state represented as an array
     * @param action direction of the action (left, up, right, down)
     * @param dimension size of the game board
     */
    public static void executeAction(
            ArrayList<Integer> puzzleState, int action, int dimension) {
        int zeroPosition = puzzleState.indexOf(0);
        int targetPosition;

        if (action == 37) { // move empty space to the left
            if (zeroPosition % dimension == 0) {
                throw new IndexOutOfBoundsException("Can not move the empty space because it is the first item in the row");
            }
            targetPosition = zeroPosition - 1;
        } else if (action == 38) { // move empty space up
            if (zeroPosition < dimension) {
                throw new IndexOutOfBoundsException("Can not move the empty space because it is in the first row");
            }
            targetPosition = zeroPosition - dimension;
        } else if (action == 39) { // move empty space to the right
            if (zeroPosition % dimension == dimension - 1) {
                throw new IndexOutOfBoundsException("Can not move the empty space because it is the last item in the row");
            }
            targetPosition = zeroPosition + 1;
        } else if (action == 40) { // move empty space down
            if (zeroPosition > dimension * (dimension - 1)) {
                throw new IndexOutOfBoundsException("Can not move the empty space because it is in the last row");
            }
            targetPosition = zeroPosition + dimension;

        } else {
            throw new IllegalArgumentException("Invalid action: " + action + " Actions must be in [37, 38, 39, 40]");
        }
        puzzleState.set(zeroPosition, puzzleState.get(targetPosition));
        puzzleState.set(targetPosition, 0);
    } // end of executeAction()

    /***
     * Given a list of integers, generate all possible permutations.
     * @param list list of integers to scramble
     * @param start start index
     * @param result array of all permutations
     */
    public static void generatePermutations(
            ArrayList<Integer> list, int start, ArrayList<ArrayList<Integer>> result) {
        if (start == list.size() - 1) {
            result.add(new ArrayList<>(list));
        } else {
            for (int i = start; i < list.size(); i++) {
                swap(list, start, i);
                generatePermutations(list, start + 1, result);
                swap(list, start, i); // Backtrack
            }
        }
    } // end of generatePermutations()

    /***
     * Helper function to generatePermutations(). Swap two values in an arraylist
     * @param list arraylist of integers
     * @param i index in list
     * @param j index in list
     */
    private static void swap(ArrayList<Integer> list, int i, int j) {
        Integer temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    } // end of swap()

} // end of TestSolver.java



