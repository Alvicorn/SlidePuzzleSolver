package solver;

import ui.PuzzleLogic;

import java.util.ArrayList;

public class Graph {
    private class Vertex {
        public int src;
        public int dest;
        public Vertex(int src, int dest) {
            this.src = src;
            this.dest = dest;
        }
    } // end of Vertex class

    private ArrayList<ArrayList<Integer>> adjacentList;
    private ArrayList<Integer> puzzleState;
    private int dimension;

    public Graph(int dimension, ArrayList<Integer> puzzleState) {
        // create a list of vertices from puzzle state for the undirected graph
        this.dimension = dimension;
        this.puzzleState = puzzleState;
        this.puzzleStateToGraph(this.puzzleState);


    } // end of Graph()

    /**
     * Update the puzzle state
     * @param puzzleState Arraylist of ints representing the puzzle state
     */
    public void setPuzzleState(ArrayList<Integer> puzzleState) {
        this.puzzleState = puzzleState;
        this.puzzleStateToGraph(this.puzzleState);
    }

    /**
     * Change the puzzle representation from an arraylist to a graph
     * @param puzzleState arraylist of ints
     */
    private void puzzleStateToGraph(ArrayList<Integer> puzzleState) {
        int topLeftIndex = 0;
        int topRightIndex = dimension - 1;
        int bottomLeftIndex = (dimension * dimension) - dimension;
        int bottomRightIndex = (dimension * dimension) -1;

        ArrayList<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < puzzleState.size(); i++) {
            if (i == topLeftIndex) { // top left corner
                vertices.add(new Vertex(i, puzzleState.get(i + 1))); // i --- rightNode
                vertices.add(new Vertex(i, puzzleState.get(i + dimension))); // i --- bottomNode

            } else if (i == topRightIndex) { // top right corner
                vertices.add(new Vertex(i, puzzleState.get(dimension + 1))); // i --- bottomNode
                vertices.add(new Vertex(i, puzzleState.get(i - 1))); // i --- leftNode

            } else if (i == bottomLeftIndex) { // bottom left corner
                vertices.add(new Vertex(i, puzzleState.get(i - dimension))); // i --- topNode
                vertices.add(new Vertex(i, puzzleState.get(i + 1))); // i --- rightNode

            }else if (i == bottomRightIndex) { // // bottom right corner
                vertices.add(new Vertex(i, puzzleState.get(i - dimension))); // i --- topNode
                vertices.add(new Vertex(i, puzzleState.get(i - 1))); // i --- leftNode

            } else if (i % dimension == 0) { // start of a new row
                vertices.add(new Vertex(i, puzzleState.get(i - dimension))); // i --- topNode
                vertices.add(new Vertex(i, puzzleState.get(i + 1))); // i --- rightNode
                vertices.add(new Vertex(i, puzzleState.get(i + dimension))); // i --- bottomNode

            } else if (i % dimension == dimension - 1) { // end of a row
                vertices.add(new Vertex(i, puzzleState.get(i - dimension))); // i --- topNode
                vertices.add(new Vertex(i, puzzleState.get(i + dimension))); // i --- bottomNode
                vertices.add(new Vertex(i, puzzleState.get(i - 1))); // i --- leftNode

            } else if (i < topRightIndex) { // middle of first row
                vertices.add(new Vertex(i, puzzleState.get(i + 1))); // i --- rightNode
                vertices.add(new Vertex(i, puzzleState.get(i + dimension))); // i --- bottomNode
                vertices.add(new Vertex(i, puzzleState.get(i - 1))); // i --- leftNode

            } else if (bottomLeftIndex < i && i < bottomRightIndex) { // middle of the last row
                vertices.add(new Vertex(i, puzzleState.get(i - dimension))); // i --- topNode
                vertices.add(new Vertex(i, puzzleState.get(i + 1))); // i --- rightNode
                vertices.add(new Vertex(i, puzzleState.get(i - 1))); // i --- leftNode

            } else { // all remaining nodes
                vertices.add(new Vertex(i, puzzleState.get(i - dimension))); // i --- topNode
                vertices.add(new Vertex(i, puzzleState.get(i + 1))); // i --- rightNode
                vertices.add(new Vertex(i, puzzleState.get(i + dimension))); // i --- bottomNode
                vertices.add(new Vertex(i, puzzleState.get(i - 1))); // i --- leftNode
            }
        }

        // each node in the graph has a list of adjcent nodes, allocate memory for that here
        this.adjacentList = new ArrayList<>();
        int size = dimension * dimension; // size of the board
        for (int i = 0; i < size; i++) {
            this.adjacentList.add(i, new ArrayList<>());
        }

        // add the edges to the adjacentList
        for (Vertex v : vertices) {
            this.adjacentList.get(v.src).add(v.dest); // src is adjacent to the dest

        }
    }

    /**
     * Print the puzzle to the console
     */
    public void printGraph() {
        System.out.println("\nPuzzle State:\t" + this.puzzleState.toString() + "\n");
        for (int i = 0; i < this.puzzleState.size(); i++) {
            if (i % this.dimension == 0) {
                System.out.println();
            }
            System.out.print("\t" + this.puzzleState.get(i) + "\t");
        }
    } // end of printPuzzle()



} // end of Graph.java
