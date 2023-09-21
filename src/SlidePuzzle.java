import java.awt.*;
import java.util.ArrayList;

import solver.Solver;
import ui.Puzzle;


public class SlidePuzzle {

    public static void main(String[] args) throws AWTException, InterruptedException {
        int dimension = 5;
        Puzzle game = new Puzzle(dimension);
        ArrayList<Integer> puzzleState = game.initializeBoard();
        Solver solver = new Solver(dimension, puzzleState);
        solver.solve();
    }
} // end of SlidePuzzle.java
