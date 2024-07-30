/**
 * Edge.java
 *
 * @author Alvin Tsang
 *
 * Edges on the graph.
 */

package solver.astar.graph;

public class Edge {

    private final int src;
    private final int dest;
    private final int position;
    public Edge(int src, int dest, int position) {
        this.src = src;
        this.dest = dest;
        this.position = position;
    }

    public int getSrc() {
        return src;
    }

    public int getDest() {
        return dest;
    }

    public int getSrcPosition() {
        return position;
    }

} // Edge.java
