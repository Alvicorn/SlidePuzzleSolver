package solver;

import solver.astar.AStarSearch;
import solver.astar.graph.AdjacentNodes;
import solver.astar.graph.Graph;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static java.awt.event.KeyEvent.VK_UP;

public class Solver {

    private Graph graph;
    private ArrayList<Integer> puzzleState;
    private int[] correctPositon;
    private int[] endState;
    private int dimension;

    public Solver(int dimension, ArrayList<Integer> puzzleState) {
        this.puzzleState = puzzleState;
        this.graph = new Graph(dimension, this.puzzleState);
        this.correctPositon = new int[dimension * dimension];
        this.endState = new int[dimension * dimension];
        this.dimension = dimension;


        // set all positions to unsolved
        for (int i = 0; i < dimension * dimension; i++) {
            this.correctPositon[i] = 0;
            this.endState[i] = i + 1;
        }
        this.endState[this.endState.length - 1] = 0; // empty cell will be at the end
    }

    public void solve() throws AWTException, InterruptedException {
        this.graph.printGraph();
        int piece = this.puzzleComplete();
        System.out.println("\n" + piece);
        if (piece == 0) { // check the initial state of the puzzle
            return;
        }

        // puzzle is not complete
        Robot robot = new Robot();
        AStarSearch search = new AStarSearch();

        // set the starting row and column
        int row = piece / this.dimension;
        int rowOffset = piece % this.dimension;
        if (rowOffset == 0) {
            row--;
            rowOffset = 4;
        }
        rowOffset--; // make the rowOffset index from 0

        while (row < this.dimension) { // setting each row
            int correctPosition = piece - 1;
            while (rowOffset < this.dimension) {
                boolean inPlace = false;

                while(!inPlace) {
                    if (rowOffset == this.dimension - 1) { // this is the last piece
                        correctPosition = correctPosition - this.dimension;
                        piece = piece -1;
                    } else if (rowOffset == this.dimension - 2) { // this is the second to last piece
                        piece = piece + 1;
                    }
                    




                    if (this.graph.pieceInPosition(piece)) {
                        inPlace = true;
                    }
                }
            }
        }
//        int row = 0;
//        while (row < this.dimension) {
//            for (int position = (row * this.dimension) + 1; position < this.dimension; position++) {
//
//            }
//            row++;
//        }

//        ArrayList<Integer> finished = new ArrayList<>();
//        for (int block : this.endState) {
//            if (!graph.nodeInPosition(block)) { // node is not solved
//                ArrayList<Integer> path = search.shortestPath(block, 0, this.graph, finished, true);
//                System.out.println(path);
//                Collections.reverse(path);
//                path.remove(0);
//
//                for (int move : path) {
//                    AdjacentNodes vertices = this.graph.getNodeVertices(move);
//                    if (vertices.getTop() == 0) {
//                        showMove(robot, VK_UP);
//                    } else if (vertices.getRight() == 0) {
//                        showMove(robot, KeyEvent.VK_RIGHT);
//                    } else if (vertices.getBottom() == 0) {
//                        showMove(robot, KeyEvent.VK_DOWN);
//                    } else if (vertices.getLeft() == 0) {
//                        showMove(robot, KeyEvent.VK_LEFT);
//                    }
//                }
//            }

            this.graph.printGraph();
//        }
    }

    private void showMove(Robot robot, int keyEvent) throws InterruptedException {
        robot.keyPress(keyEvent);
        robot.keyRelease(keyEvent);
        TimeUnit.SECONDS.sleep(1);

    }
    private void updatePuzzleState(int blankPosition, int keyEvent) {
        switch(keyEvent) {
            case(KeyEvent.VK_UP) -> Collections.swap(this.puzzleState, blankPosition, blankPosition - 4);
            case(KeyEvent.VK_RIGHT) -> Collections.swap(this.puzzleState, blankPosition, blankPosition + 1);
            case(KeyEvent.VK_DOWN) -> Collections.swap(this.puzzleState, blankPosition, blankPosition + 4);
            case(KeyEvent.VK_LEFT) -> Collections.swap(this.puzzleState, blankPosition, blankPosition - 1);
        }
        this.graph.setPuzzleState(this.puzzleState); // update the graph
    }

    /**
     * Check if the puzzle state is a finished puzzle. If complete, return 0,
     * otherwise, return the piece that is out of place
     * @return
     */
    private int puzzleComplete() {
        int piece = 1;
        while(piece < dimension * dimension) {
            if (!this.graph.pieceInPosition(piece)) {
//            if (this.puzzleState.get(piece - 1) != piece) {
                return piece;
            } else {
                piece++;
            }
        }
        return 0;
    }
}
