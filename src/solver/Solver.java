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
    private ArrayList<Integer> endState;
    private int dimension;

    public Solver(int dimension, ArrayList<Integer> puzzleState) {
        this.puzzleState = puzzleState;
        this.graph = new Graph(dimension, this.puzzleState);
        this.endState = new ArrayList<>();
        this.dimension = dimension;


        // set all positions to unsolved
        for (int i = 0; i < dimension * dimension; i++) {
            this.endState.add(i, i + 1);
        }
        this.endState.set(this.endState.size() - 1, 0); // empty cell will be at the end
        System.out.println("end state=" + this.endState);
    }

    public void solve() throws AWTException, InterruptedException {
        this.graph.printGraph();
        Robot robot = new Robot();
        AStarSearch search = new AStarSearch();
        int piece = this.puzzleComplete();

        while (piece != 0) { // while puzzle is not complete
            System.out.println("\nSolving at piece: " + piece);

            // find the piece the that is the target piece's position
            int impedingPiece = this.puzzleState.get(piece - 1);
            System.out.println("Impeding piece: " + impedingPiece);

            // find the full path for the target piece to get to its correct position
            ArrayList<Integer> immovablePieces = new ArrayList<>();
            System.out.println("immovable pieces: " + immovablePieces);
            for (int i = 1; i < piece; i++) {
                immovablePieces.add(i);
            }
            ArrayList<Integer> path = search.shortestPath(
                    piece, impedingPiece,
                    this.graph, immovablePieces, false);
            System.out.println("Full path: " + path);
            path.remove(0);

            // find the current row and column of the piece
            int[] currentPosition = this.findPiecePosition(this.puzzleState, piece);
            System.out.println("Current position: [" + currentPosition[0] + ", " + currentPosition[1] + "]");

            // find the correct row and column of the piece
            int[] correctPosition = this.findPiecePosition(this.endState, piece);
            System.out.println("Correct position: [" + correctPosition[0] + ", " + correctPosition[1] + "]");

            // align column first
            while (currentPosition[1] != correctPosition[1]) {



                currentPosition = this.findPiecePosition(this.puzzleState, piece);
                correctPosition = this.findPiecePosition(this.endState, piece);
            }

            piece = this.puzzleComplete();
            break;
        }

//        // set the starting row and column
//        int row = piece / this.dimension;
//        int rowOffset = piece % this.dimension;
//        if (rowOffset == 0) {
//            row--;
//            rowOffset = this.dimension;
//        }
//        rowOffset--; // make the rowOffset index from 0
//
//        while (row < this.dimension) { // setting each row
//            int correctPosition = piece - 1;
//            while (rowOffset < this.dimension) {
//                boolean inPlace = false;
//
//                while(!inPlace) {
//                    if (rowOffset == this.dimension - 1) { // this is the last piece
//                        correctPosition = correctPosition - this.dimension;
//                        piece = piece - 1;
//                    } else if (rowOffset == this.dimension - 2) { // this is the second to last piece
//                        piece = piece + 1;
//                    }
//                    System.out.println("\nTarget Piece: " + piece);
//                    if (this.graph.pieceInPosition(piece)) {
//                        inPlace = true;
//                    }
//                }
//            }
//        }
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

    private int[] findPiecePosition(ArrayList<Integer> puzzleState, int target_piece) {
        int pieceIndex = 0;
        for (int index = 0; index < puzzleState.size(); index++){
            if (puzzleState.get(index) == target_piece) {
                pieceIndex = index;
            }
        }
        int[] position = new int[2];
        position[0] = pieceIndex / this.dimension; // row
        position[1] = pieceIndex % this.dimension; // column
        return position;
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
     * otherwise, return the piece that is out of place.
     *
     * @return The first piece that is out of place. Zero if puzzle is solved.
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
