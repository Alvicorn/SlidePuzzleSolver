package solver.astar.graph;

public class GraphNode implements Comparable<GraphNode> {

        private int nodeValue;
        private boolean inPosition;
        private AdjacentNodes adjacentNodes;
        private int row;
        private int rowOffset;

        public GraphNode(int value, int row, int rowOffset) {
            this.nodeValue = value;
            this.inPosition = false;
            this.adjacentNodes = new AdjacentNodes();
            this.row = row;
            this.rowOffset = rowOffset;
        }

    public int getValue() {
        return this.nodeValue;
    }

    public void setValue(int value) {
        this.nodeValue = value;
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

    public AdjacentNodes getVertices() {
            return this.adjacentNodes;
    }

    public int getRow() {
        return this.row;
    }

    public int getRowOffset() {
            return this.rowOffset;
    }

    @Override
    public int compareTo(GraphNode n) {
        return this.getValue() - n.getValue();
    }
}

