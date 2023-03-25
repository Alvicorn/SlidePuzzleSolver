package solver;

import java.util.ArrayList;

public class Solver {

    private Graph graph;
    private ArrayList<Integer> puzzleState;

    public Solver(int dimension, ArrayList<Integer> puzzleState) {
        this.puzzleState = puzzleState;
        this.graph = new Graph(dimension, this.puzzleState);
    }

    public void solve() {
        this.graph.printGraph();
    }
}
