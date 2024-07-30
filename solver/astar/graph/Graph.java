/**
 * Graph.java
 *
 * @author Alvin Tsang
 *
 * Graph representation of the slide puzzle game board.
 */
package solver.astar.graph;

import java.util.ArrayList;


public class Graph {

    private ArrayList<GraphNode> graphNodeList;
    private ArrayList<Integer> puzzleState;
    private final int dimension;

    public Graph(int dimension, ArrayList<Integer> puzzleState) {
        // create a list of vertices from puzzle state for the undirected graph
        this.dimension = dimension;
        this.puzzleState = puzzleState;
        this.puzzleStateToGraph(this.puzzleState);
    } // end of Graph()

    public int getDimension() {return this.dimension;}

    public boolean pieceInPosition(int piece) {return this.graphNodeList.get(piece-1).isInPosition();}

    public AdjacentNodes getNodeVertices (int node) {return this.graphNodeList.get(node).getVertices();}

    public ArrayList<Integer> getPuzzleState() { return this.puzzleState;}

    /**
     * Change the puzzle representation from an arraylist to a graph
     * @param puzzleState arraylist of ints
     */
    private void puzzleStateToGraph(ArrayList<Integer> puzzleState) {
        int topLeftIndex = 0;
        int topRightIndex = dimension - 1;
        int bottomLeftIndex = (dimension * dimension) - dimension;
        int bottomRightIndex = (dimension * dimension) -1;

        ArrayList<Edge> vertices = new ArrayList<>();
        for (int i = 0; i < puzzleState.size(); i++) {
            int src = puzzleState.get(i);
            if (i == topLeftIndex) { // top left corner
                vertices.add(new Edge(src, puzzleState.get(i + 1), 2)); // i --- rightNode
                vertices.add(new Edge(src, puzzleState.get(i + dimension), 3)); // i --- bottomNode

            } else if (i == topRightIndex) { // top right corner
                vertices.add(new Edge(src, puzzleState.get(i + dimension), 3)); // i --- bottomNode
                vertices.add(new Edge(src, puzzleState.get(i - 1), 4)); // i --- leftNode

            } else if (i == bottomLeftIndex) { // bottom left corner
                vertices.add(new Edge(src, puzzleState.get(i - dimension), 1)); // i --- topNode
                vertices.add(new Edge(src, puzzleState.get(i + 1), 2)); // i --- rightNode

            }else if (i == bottomRightIndex) { // // bottom right corner
                vertices.add(new Edge(src, puzzleState.get(i - dimension), 1)); // i --- topNode
                vertices.add(new Edge(src, puzzleState.get(i - 1), 4)); // i --- leftNode

            } else if (i % dimension == 0) { // start of a new row
                vertices.add(new Edge(src, puzzleState.get(i - dimension), 1)); // i --- topNode
                vertices.add(new Edge(src, puzzleState.get(i + 1), 2)); // i --- rightNode
                vertices.add(new Edge(src, puzzleState.get(i + dimension), 3)); // i --- bottomNode

            } else if (i % dimension == dimension - 1) { // end of a row
                vertices.add(new Edge(src, puzzleState.get(i - dimension), 1)); // i --- topNode
                vertices.add(new Edge(src, puzzleState.get(i + dimension), 3)); // i --- bottomNode
                vertices.add(new Edge(src, puzzleState.get(i - 1), 4)); // i --- leftNode

            } else if (i < topRightIndex) { // middle of first row
                vertices.add(new Edge(src, puzzleState.get(i + 1), 2)); // i --- rightNode
                vertices.add(new Edge(src, puzzleState.get(i + dimension), 3)); // i --- bottomNode
                vertices.add(new Edge(src, puzzleState.get(i - 1), 4)); // i --- leftNode

            } else if (bottomLeftIndex < i && i < bottomRightIndex) { // middle of the last row
                vertices.add(new Edge(src, puzzleState.get(i - dimension), 1)); // i --- topNode
                vertices.add(new Edge(src, puzzleState.get(i + 1), 2)); // i --- rightNode
                vertices.add(new Edge(src, puzzleState.get(i - 1), 4)); // i --- leftNode

            } else { // all remaining nodes
                vertices.add(new Edge(src, puzzleState.get(i - dimension), 1)); // i --- topNode
                vertices.add(new Edge(src, puzzleState.get(i + 1), 2)); // i --- rightNode
                vertices.add(new Edge(src, puzzleState.get(i + dimension), 3)); // i --- bottomNode
                vertices.add(new Edge(src, puzzleState.get(i - 1), 4)); // i --- leftNode
            }
        }

        // each node in the graph has a list of adjacent nodes, allocate memory for that here
        this.graphNodeList = new ArrayList<>();
        int size = dimension * dimension; // size of the board
        for (int i = 0; i < size; i++) {
            int puzzleValue = this.puzzleState.get(i);
            int rowOffset = i % this.dimension;
            if (rowOffset == this.dimension - 1) {
                rowOffset = 4;
            }
            rowOffset--;
            this.graphNodeList.add(i, new GraphNode(puzzleValue));
            /*
            The board index starts in the top left corner with index 0, but the valid puzzle
            starts at 1 and ascends to ((dimension * dimension) - 1). In a solved puzzle, the bottom
            right value should be zero, but should only happen when the rest of the puzzle is in the
            correct position
             */
            if (i + 1 == puzzleValue) { // check if non-zero puzzle value is in the correct puzzle position
                this.graphNodeList.get(i).setInPosition(true);
            }
        }

        // add the edges to the nodeList
        for (Edge v : vertices) {
            this.graphNodeList.get(v.getSrc()).addVertex(v.getDest(), v.getSrcPosition()); // node src is adjacent to node dest
        }
    } // end of puzzleStateToGraph()

    /**
     * Swap the Vertices with the values u and v.
     * @param u vertex value
     * @param v vertex value
     */
    public void swapVertices(int u, int v) {
        for (int i = 0; i < puzzleState.size(); i++) {
            if (puzzleState.get(i) == u) {
                puzzleState.set(i, v);
            } else if (puzzleState.get(i) == v) {
                puzzleState.set(i, u);
            }
        }
        puzzleStateToGraph(puzzleState);
    } // end of swapVertices()

    /**
     * Print the puzzle to the console
     */
    public void printGraph() {
        System.out.println("\nPuzzle State:\t" + this.puzzleState.toString());
        for (int i = 0; i < this.puzzleState.size(); i++) {
            if (i % this.dimension == 0) {
                System.out.println();
            }
            System.out.print("\t" + this.puzzleState.get(i) + "\t");
        }
        System.out.println("\n");
    } // end of printPuzzle()

} // end of Graph.java
