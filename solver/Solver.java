/**
 * Solver.java
 *
 * @author Alvin Tsang
 *
 * Generate a solution of a slide puzzle
 */
package solver;

import java.awt.event.KeyEvent;
import java.util.*;

import solver.astar.AStarSearch;
import solver.astar.graph.AdjacentNodes;
import solver.astar.graph.Graph;


public class Solver {

    private final int ROW_COMPLETE_WEIGHT = 10000;
    private final int COLUMN_COMPLETE_WEIGHT = 10000;
    private final int PIECE_BEING_SOLVED_WEIGHT = 1000;
    private final int PIECE_COMPLETE_WEIGHT = 1000;
    private final int BONUS_WEIGHT = 1000;
    private final int PIECE_INCOMPLETE_WEIGHT = 1;
    private final ArrayList<Integer> solveOrder;
    private final int dimension;
    private final AStarSearch search;
    private static final int MAX_ITERATIONS = 1000;

    public Solver(int dimension) {
        this.search = new AStarSearch();
        this.dimension = dimension;
        this.solveOrder = generateSolveOrder();
    }

    /**
     * Generate a solution for the given puzzle state
     * @param puzzleState current game board state
     * @return list of moves. If -1 is the last element in the list, no solution was found (too many computations)
     */
    public ArrayList<Integer> solve(ArrayList<Integer> puzzleState) {
        Graph graph = new Graph(dimension, puzzleState);
        graph.printGraph();

        HashMap<Integer, Integer> pieceCompleteWeights = new HashMap<>();
        int piece = goalTest(graph, pieceCompleteWeights);
        if (piece == 0) { // puzzle is solved
            return new ArrayList<>();
        }

        // puzzle is not complete
        ArrayList<Integer> solution = new ArrayList<>();

        int iteration = 0;
        while (iteration < MAX_ITERATIONS) {
            if (piece > (dimension * dimension) - (dimension * 2)) {
                System.out.println("Solve for the last two rows");
                boolean endCondition = true;

                // check that all other pieces except the penultimate piece in the penultimate row is solved
                 for (int i = (dimension * (dimension - 2)) + 1; i < (dimension * (dimension - 1)) - 1; i++) {
                     if (!graph.pieceInPosition(i) || !graph.pieceInPosition(i + dimension)) {
                         piece = i;
                         endCondition = false;
                         break;
                     }
                 }
                if (endCondition) {
                    System.out.println("Solving for the final three pieces");
                    solution.addAll(solveFinalThreePieces(graph, pieceCompleteWeights));
                } else {
                    solution.addAll(solveEndOfColumn(piece, piece + dimension, graph, pieceCompleteWeights));
                }
            } else {
                if (piece % dimension == dimension - 1) {piece = piece + 1;} // this is the second to last piece
                if (piece % dimension == 0) { // this is the last piece
                    solution.addAll(solveEndOfRow(piece - 1, piece, graph, pieceCompleteWeights));
                } else {
                    solution.addAll(movePieceToDestination(piece, piece - 1, graph, pieceCompleteWeights));
                }
            }

            piece = goalTest(graph, pieceCompleteWeights);
            if (piece == 0) { // puzzle is solved
                System.out.println("Final solution: " + solution);
                return solution;
            }
            iteration++;
        }
        solution.add(-1); // indicate the puzzle was not solved
        return solution;
    } // end of solve()

    /**
     * Move the targetPiece to the destination position.
     *
     * @param targetPiece          piece that will be put into the destination position
     * @param destinationPosition  position in the graph where the targetPiece is desired
     * @param graph                representation of the game board
     * @param pieceCompleteWeights list of pieces that are already solved and should not be moved
     * @return ArrayList of actions that allowed the targetPiece to move to the destinationPosition
     */
    private ArrayList<Integer> movePieceToDestination(
            int targetPiece, int destinationPosition, Graph graph,
            HashMap<Integer, Integer> pieceCompleteWeights) {
        ArrayList<Integer> solution = new ArrayList<>();
        int oldTargetPieceWeight = pieceCompleteWeights.get(targetPiece);
        pieceCompleteWeights.put(targetPiece, PIECE_BEING_SOLVED_WEIGHT + BONUS_WEIGHT);
        System.out.println("Target piece: " + targetPiece + " needs to switch with " +
                            graph.getPuzzleState().get(destinationPosition));
        do {
            int destination = graph.getPuzzleState().get(destinationPosition);
            // find the path from the target piece to the destination
            ArrayList<Integer> path = search.shortestPath(targetPiece, destination, graph,
                    pieceCompleteWeights, true);
            System.out.println("Path for piece " + targetPiece + " is " + path);
            path.remove(0); // remove the src node from the path
            if (path.size() == 0) { // targetPiece is already in place
                break;
            }
            // find the path from the empty space to the first position in path
            ArrayList<Integer> emptySpacePath = search.shortestPath(0, path.get(0), graph,
                    pieceCompleteWeights, true);
            System.out.println("Path to move 0 to the space occupied by " + path.get(0) + ": " + emptySpacePath);
            emptySpacePath.remove(0); // remove 0 from the path
            emptySpacePath.add(targetPiece); // add targetPiece so that it can be swapped with the empty space
            moveEmptySpaceAlongPath(emptySpacePath, solution, graph);
        } while (graph.getPuzzleState().get(destinationPosition) != targetPiece);
        pieceCompleteWeights.put(targetPiece, oldTargetPieceWeight);
        return solution;
    } // end of movePieceToDestination()

    /**
     * Solve the last two pieces in a row
     * @param penultimatePiece penultimate piece in the column
     * @param lastPiece last piece in the column
     * @param graph representation of the game board
     * @param pieceCompleteWeights list of weights for each piece based on if it is solved or not
     * @return list of instructions to solve the end of row
     */
    private ArrayList<Integer> solveEndOfRow(
            int penultimatePiece, int lastPiece, Graph graph, HashMap<Integer, Integer> pieceCompleteWeights) {
        System.out.println("Inserting " + penultimatePiece + " and " + lastPiece + " into position");

        if (rowEndPiecesAreFlipped(penultimatePiece, lastPiece, graph)) {
            return flipRowEndPieces(penultimatePiece, lastPiece, graph, pieceCompleteWeights);
        }

        // check if the penultimate piece is already in place
        if (graph.getPuzzleState().get(penultimatePiece - 1) == penultimatePiece &&
                graph.getPuzzleState().get(lastPiece - 1) == 0 &&
                Arrays.stream(graph.getNodeVertices(lastPiece).adjacentNodeList()).anyMatch(num -> num == 0)) {
            return movePieceToDestination(lastPiece, lastPiece - 1, graph, pieceCompleteWeights);
        }

        pieceCompleteWeights.put(penultimatePiece, PIECE_BEING_SOLVED_WEIGHT);
        pieceCompleteWeights.put(lastPiece, PIECE_BEING_SOLVED_WEIGHT);

        // move the lastPiece into the penultimatePiece's correct position
        ArrayList<Integer> solution = new ArrayList<>(movePieceToDestination(
                lastPiece, penultimatePiece - 1, graph, pieceCompleteWeights));

        if (rowEndPiecesAreFlipped(penultimatePiece, lastPiece, graph)) {
            solution.addAll(flipRowEndPieces(penultimatePiece, lastPiece, graph, pieceCompleteWeights));
            return solution;
        }

        pieceCompleteWeights.put(penultimatePiece, PIECE_BEING_SOLVED_WEIGHT);

        // move the penultimatePiece below of the penultimatePiece's correct position
        solution.addAll(movePieceToDestination(
                penultimatePiece, penultimatePiece + dimension - 1, graph, pieceCompleteWeights));

        int destination = graph.getPuzzleState().get(lastPiece - 1);

        ArrayList<Integer> emptySpacePath = search.shortestPath(0, destination, graph,
                pieceCompleteWeights, true);
        System.out.println("Path to move 0 to the space occupied by " + destination + ": " + emptySpacePath);
        emptySpacePath.remove(0); // remove 0 from the path
        emptySpacePath.add(lastPiece); // add the last piece to the path
        emptySpacePath.add(penultimatePiece); // add the penultimate piece to the path
        moveEmptySpaceAlongPath(emptySpacePath, solution, graph);
        return solution;
    } // end of solveEndOfColumn()

    /**
     * Solve the last two pieces in a column
     * @param penultimatePiece penultimate piece in the column
     * @param lastPiece last piece in the column
     * @param graph representation of the game board
     * @param pieceCompleteWeights list of weights for each piece based on if it is solved or not
     * @return list of instructions to solve the end of row
     */
    private ArrayList<Integer> solveEndOfColumn(
            int penultimatePiece, int lastPiece, Graph graph, HashMap<Integer, Integer> pieceCompleteWeights) {
        System.out.println("Inserting " + penultimatePiece + " and " + lastPiece + " into position");

        if (columnEndPiecesAreFlipped(penultimatePiece, lastPiece, graph)) {
            return flipColumnEndPieces(penultimatePiece, lastPiece, graph, pieceCompleteWeights);
        }

        // check if the penultimate piece is already in place
        if (graph.getPuzzleState().get(penultimatePiece - 1) == penultimatePiece &&
                graph.getPuzzleState().get(lastPiece - 1) == 0 &&
                Arrays.stream(graph.getNodeVertices(lastPiece).adjacentNodeList()).anyMatch(num -> num == 0)) {
            return movePieceToDestination(lastPiece, lastPiece - 1, graph, pieceCompleteWeights);
        }

        pieceCompleteWeights.put(penultimatePiece, PIECE_INCOMPLETE_WEIGHT);
        pieceCompleteWeights.put(lastPiece, PIECE_BEING_SOLVED_WEIGHT);

        // move the lastPiece into the penultimatePiece's correct position
        ArrayList<Integer> solution = new ArrayList<>(movePieceToDestination(
                lastPiece, penultimatePiece - 1, graph, pieceCompleteWeights));

        if (columnEndPiecesAreFlipped(penultimatePiece, lastPiece, graph)) {
            solution.addAll(flipColumnEndPieces(penultimatePiece, lastPiece, graph, pieceCompleteWeights));
            return solution;
        }

        pieceCompleteWeights.put(penultimatePiece, PIECE_BEING_SOLVED_WEIGHT);

        // move the penultimatePiece to the left of the penultimatePiece's correct position
        solution.addAll(movePieceToDestination(penultimatePiece, penultimatePiece, graph, pieceCompleteWeights));

        int destination = graph.getPuzzleState().get(lastPiece - 1);

        ArrayList<Integer> emptySpacePath = search.shortestPath(0, destination, graph,
                pieceCompleteWeights, true);
        System.out.println("Path to move 0 to the space occupied by " + destination + ": " + emptySpacePath);
        emptySpacePath.remove(0); // remove 0 from the path
        emptySpacePath.add(lastPiece); // add the last piece to the path
        emptySpacePath.add(penultimatePiece); // add the penultimate piece to the path
        moveEmptySpaceAlongPath(emptySpacePath, solution, graph);

        return solution;
    } // end of solveEndOfColumn()

    /**
     * Check if the last two pieces in a column meets
     * the flipped condition or blocked condition
     *
     * Examples: 3 X 3 game board; look at 4 & 7
     *
     * Flipped Condition:
     *      7 is in 4's valid position and vice versa
     *	1		2		3
     * 	7		6		5
     * 	4		0		8
     *
     * Blocked Condition:
     *      7 is in 4's valid position and 0 is in 7's valid position with 4
     *      to the left of 0. When 0 is swapped with 4. Swapped Condition will occur.
     * 	1		2		3
     * 	7		6		5
     * 	0		4		8
     * @param penultimatePiece penultimate piece in the column
     * @param lastPiece last piece in the column
     * @param graph representation of the game board
     * @return True if either the swapped condition of blocked condition is satisfied
     */
    private boolean columnEndPiecesAreFlipped(
            int penultimatePiece, int lastPiece, Graph graph) {
        ArrayList<Integer> puzzleState = graph.getPuzzleState();
        System.out.println("Checking if columns are flipped...");

        boolean flippedCondition = puzzleState.get(penultimatePiece - 1) == lastPiece &&
                                   puzzleState.get(lastPiece - 1) == penultimatePiece;

        boolean blockedCondition = puzzleState.get(penultimatePiece - 1) == lastPiece &&
                                   puzzleState.get(lastPiece - 1) == 0 &&
                                   puzzleState.get(lastPiece) == penultimatePiece;

        System.out.println("Swapped Condition: " + flippedCondition);
        System.out.println("Blocked Condition: " + blockedCondition);
        return flippedCondition || blockedCondition;
    } // end of columnEndPiecesAreFlipped

    /**
     * Check if the last two pieces in a row meets
     * the flipped condition or blocked condition
     *
     * Examples: 3 X 3 game board; look at 2 & 3
     *
     * Flipped Condition:
     *      3 is in 2's valid position and vice versa
     *	1		3		2
     * 	7		6		5
     * 	4		0		8
     *
     * Blocked Condition:
     *      3 is in 2's valid position and 0 is in 3's valid position with 2
     *      under of 0. When 0 is swapped with 2. Swapped Condition will occur.
     * 	1		3		0
     * 	7		6		2
     * 	5		4		8
     * @param penultimatePiece penultimate piece in the column
     * @param lastPiece last piece in the column
     * @param graph representation of the game board
     * @return True if either the swapped condition of blocked condition is satisfied
     */
    private boolean rowEndPiecesAreFlipped(
            int penultimatePiece, int lastPiece, Graph graph) {
        ArrayList<Integer> puzzleState = graph.getPuzzleState();
        System.out.println("Checking if rows are flipped...");

        boolean flippedCondition = puzzleState.get(penultimatePiece - 1) == lastPiece &&
                                   puzzleState.get(lastPiece - 1) == penultimatePiece;

        boolean blockedCondition = puzzleState.get(penultimatePiece - 1) == lastPiece &&
                                   puzzleState.get(lastPiece - 1) == 0 &&
                                   puzzleState.get(lastPiece + dimension - 1) == penultimatePiece;

        System.out.println("Swapped Condition: " + flippedCondition);
        System.out.println("Blocked Condition: " + blockedCondition);
        return flippedCondition || blockedCondition;
    } // end of rowEndPiecesAreFlipped()

    /**
     * Fix the flipped or blocked condition for the last two pieces in a column
     * @param penultimatePiece penultimate piece in the column
     * @param lastPiece last piece in the column
     * @param graph representation of the game board
     * @param pieceCompleteWeights list of pieces that are already solved and should not be moved
     * @return ArrayList of actions that allowed the targetPiece to move to the destinationPosition
     */
    private ArrayList<Integer> flipColumnEndPieces(
            int penultimatePiece, int lastPiece, Graph graph, HashMap<Integer, Integer> pieceCompleteWeights) {
        System.out.println("Flipping " + penultimatePiece + " and " + lastPiece + " into position");
        ArrayList<Integer> solution = new ArrayList<>();

        pieceCompleteWeights.put(penultimatePiece, PIECE_INCOMPLETE_WEIGHT);
        pieceCompleteWeights.put(lastPiece, PIECE_BEING_SOLVED_WEIGHT);

        // move the lastPiece to its valid position + 2
        solution.addAll(movePieceToDestination(
                lastPiece, lastPiece + 1, graph, pieceCompleteWeights));

        // move penultimatePiece to its valid position + 1
        solution.addAll(movePieceToDestination(
                penultimatePiece, penultimatePiece, graph, pieceCompleteWeights));

        pieceCompleteWeights.put(penultimatePiece, PIECE_BEING_SOLVED_WEIGHT);

        // move the lastPiece to its valid position + 1
        solution.addAll(movePieceToDestination(
                lastPiece, lastPiece, graph, pieceCompleteWeights));

        // move penultimatePiece to its valid position + 2
        solution.addAll(movePieceToDestination(
                penultimatePiece, penultimatePiece + 1, graph, pieceCompleteWeights));

        solution.addAll(solveEndOfColumn(penultimatePiece, lastPiece, graph, pieceCompleteWeights));
        return solution;
    } // end of flipColumnEndPieces()

    /**
     * Fix the flipped or blocked condition for the last two pieces in a row
     * @param penultimatePiece penultimate piece in the column
     * @param lastPiece last piece in the column
     * @param graph representation of the game board
     * @param pieceCompleteWeights list of pieces that are already solved and should not be moved
     * @return ArrayList of actions that allowed the targetPiece to move to the destinationPosition
     */
    private ArrayList<Integer> flipRowEndPieces(
            int penultimatePiece, int lastPiece, Graph graph, HashMap<Integer, Integer> pieceCompleteWeights) {
        System.out.println("Flipping " + penultimatePiece + " and " + lastPiece + " into position");
        ArrayList<Integer> solution = new ArrayList<>();

        pieceCompleteWeights.put(penultimatePiece, PIECE_INCOMPLETE_WEIGHT);
        pieceCompleteWeights.put(lastPiece, PIECE_BEING_SOLVED_WEIGHT);

        // move the lastPiece to its valid position + (dimension * 2)
        solution.addAll(movePieceToDestination(
                lastPiece, lastPiece + (dimension * 2) - 1, graph, pieceCompleteWeights));

        // move penultimatePiece to its valid position + dimension
        solution.addAll(movePieceToDestination(
                penultimatePiece, penultimatePiece + dimension - 1, graph, pieceCompleteWeights));

        pieceCompleteWeights.put(penultimatePiece, PIECE_BEING_SOLVED_WEIGHT);

        // move the lastPiece to its valid position + dimension
        solution.addAll(movePieceToDestination(
                lastPiece, lastPiece + dimension - 1, graph, pieceCompleteWeights));

        // move penultimatePiece to its valid position + (dimension * 2)
        solution.addAll(movePieceToDestination(
                penultimatePiece, penultimatePiece + (dimension * 2) - 1, graph, pieceCompleteWeights));

        solution.addAll(solveEndOfRow(penultimatePiece, lastPiece, graph, pieceCompleteWeights));
        return solution;
    } // end of flipRowEndPieces()

    /**
     * Solve the final three pieces of the puzzle
     * @param graph representation of the game board
     * @param pieceCompleteWeights list of pieces that are already solved and should not be moved
     * @return ArrayList of actions that allowed the targetPiece to move to the destinationPosition     */
    private ArrayList<Integer> solveFinalThreePieces(Graph graph, HashMap<Integer, Integer> pieceCompleteWeights) {
        ArrayList<Integer> solution = new ArrayList<>();
        while (true) {
            int piece = goalTest(graph, pieceCompleteWeights);
            if (piece == 0) {return solution;}
            solution.addAll(movePieceToDestination(piece, piece - 1, graph, pieceCompleteWeights));
        }
    } // end of solveFinalThreePieces()

    /**
     * Move the empty space along a given path in the graph
     * @param path path to take in the graph
     * @param currentSolution list of moves that will become the solution
     * @param graph representation of the game board
     */
    private void moveEmptySpaceAlongPath(
            ArrayList<Integer> path, ArrayList<Integer> currentSolution, Graph graph) {
        for (int pathPiece : path) {
            currentSolution.add(determineAction(graph, pathPiece));
            graph.swapVertices(0, pathPiece);
            graph.printGraph();
        }
    } // end of moveEmptySpaceALongPath()

    /**
     * Determine where the empty space is relative to the vertex and
     * record the action that needs to be taken.
     *
     * Left: 37
     * Up: 38
     * Right: 39
     * Down: 40
     * @param graph representation of the game board
     * @param vertex check this vertex to see if the empty space is adjacent to it
     * @return KeyEvent indicating a valid action. -1 otherwise.
     */
    private int determineAction(Graph graph, int vertex) {
        AdjacentNodes vertices = graph.getNodeVertices(vertex);
        if (vertices.getTop() == 0) {
            return KeyEvent.VK_DOWN;
        } else if (vertices.getRight() == 0) {
            return KeyEvent.VK_LEFT;
        } else if (vertices.getBottom() == 0) {
            return KeyEvent.VK_UP;
        } else if (vertices.getLeft() == 0) {
            return KeyEvent.VK_RIGHT;
        } else {
            System.out.println("Invalid key event. 0 is not adjacent to vertex " + vertex);
            System.out.println(vertex + " is adjacent to " + Arrays.toString(vertices.adjacentNodeList()));
            System.exit(66);
        }
        return -1;
    } // end of determineAction()

    /**
     * Generate the solving order such that the solver will alternate between
     * solving the highest unsolved row and the most left unsolved column.
     * @return suggested solving order for the puzzle
     */
    private ArrayList<Integer> generateSolveOrder() {
        ArrayList<Integer> solveOrder = new ArrayList<>();

        int rowCounter = 1;
        for (int i = 0; i < dimension; i++) {
            for (int j = (i * dimension) + rowCounter; j <= (i * dimension) + dimension; j++) {
                solveOrder.add(j);
            }
            for (int j = ((i + 1) * dimension) + rowCounter; j < dimension * dimension; j += dimension) {
                solveOrder.add(j);
            }
            rowCounter++;
        }
        solveOrder.remove((dimension * dimension) - 1); // remove the last item
        System.out.println("Solve order: " + solveOrder);
        return solveOrder;
    } // end of generateSolveOrder()

    /**
     * Check if the puzzle state is a finished puzzle. If complete, return 0,
     * otherwise, return the piece that is out of place
     * @param graph representation of the game board
     * @param pieceCompleteWeights list of weights for each piece based on if it is solved or not
     * @return a value > 0 if the puzzle is not solved. 0 otherwise.
     */
    private int goalTest(Graph graph, HashMap<Integer, Integer> pieceCompleteWeights) {
        int piece = 0;
        for (int solvePiece : solveOrder) {
            if (!graph.pieceInPosition(solvePiece)) {
                piece = solvePiece;
                break;
            }
        }

        if (piece == 0) {return 0;} // puzzle is solved

        for (int i = 0; i < solveOrder.size(); i++) {
            final int solvePiece = solveOrder.get(i);
            if (i < solveOrder.indexOf(piece)) { // all solved pieces
                if (solvePiece % dimension == 0) { // the entire row is in the correct position
                    for (int j = i - dimension + 1; j <= i; j++) {
                        pieceCompleteWeights.put(solveOrder.get(j), ROW_COMPLETE_WEIGHT);
                    }
                } else if (solvePiece > dimension * (dimension - 1)) { // the entire column is in the correct position
                    for (int j = solvePiece % dimension - 1; j <= i; j += dimension) {
                        pieceCompleteWeights.put(solveOrder.get(j), COLUMN_COMPLETE_WEIGHT);
                    }
                } else {
                    pieceCompleteWeights.put(solvePiece, PIECE_COMPLETE_WEIGHT);
                }
            } else {
                pieceCompleteWeights.put(solvePiece, PIECE_INCOMPLETE_WEIGHT);
            }
        }
        return piece;
    } // end of goalTest()

} // end of Solver.java
