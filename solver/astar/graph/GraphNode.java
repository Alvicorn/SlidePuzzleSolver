/**
 * GraphNode.java
 *
 * @author Alvin Tsang
 *
 * A vertex in the graph
 */

package solver.astar.graph;

public class GraphNode implements Comparable<GraphNode> {

        private final int nodeValue;
        private boolean inPosition;
        private AdjacentNodes adjacentNodes;

        public GraphNode(int value) {
            this.nodeValue = value;
            this.inPosition = false;
            this.adjacentNodes = new AdjacentNodes();
        }

    public int getValue() {
        return this.nodeValue;
    }

    public boolean isInPosition() {
        return this.inPosition;
    }

    public void setInPosition(boolean inPosition) {
        this.inPosition = inPosition;
    }

    public void addVertex(int vertex, int position) {
        switch (position) {
            case 1 -> this.adjacentNodes.setTop(vertex);
            case 2 -> this.adjacentNodes.setRight(vertex);
            case 3 -> this.adjacentNodes.setBottom(vertex);
            case 4 -> this.adjacentNodes.setLeft(vertex);
        }
    }

    public AdjacentNodes getVertices() {return this.adjacentNodes;}

    @Override
    public int compareTo(GraphNode n) {return this.getValue() - n.getValue();}

} // end of GraphNode.java

