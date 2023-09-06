import solver.Solver;
import ui.Puzzle;
import ui.PuzzleLogic;

import java.awt.*;
import java.util.ArrayList;


public class SlidePuzzle {

    public static void main(String[] args) throws AWTException, InterruptedException {
        int dimension = 2;
        Puzzle game = new Puzzle(dimension);
        ArrayList<Integer> puzzleState = game.initializeBoard();
        Solver solver = new Solver(dimension, puzzleState);
        solver.solve();

    }

}