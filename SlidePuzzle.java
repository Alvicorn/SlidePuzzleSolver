import solver.Solver;
import ui.Puzzle;
import ui.PuzzleLogic;

import java.util.ArrayList;


public class SlidePuzzle {

    public static void main(String[] args) {
        int dimension = 4;
        Puzzle game = new Puzzle(dimension);
        ArrayList<Integer> puzzleState = game.initializeBoard();
        Solver solver = new Solver(dimension, puzzleState);
        solver.solve();

    }

}