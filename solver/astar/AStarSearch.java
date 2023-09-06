package solver.astar;

import solver.astar.graph.AdjacentNodes;
import solver.astar.minheap.Node;
import solver.astar.graph.Graph;

import java.util.ArrayList;

public class AStarSearch {

    private final PriorityQueue queue;

    public AStarSearch() {
        this.queue = new PriorityQueue();
    }




    public ArrayList<Integer> shortestPath(int src, int dest, Graph graph,
                                           ArrayList<Integer> immovable, boolean prioritizeRow) {
        ArrayList<Integer> visited = new ArrayList<>();

        // start at the src node
        visited.add(src);
        this.queue.enqueue(new Node(0, manhattanDistance(src, dest, graph), src, -1));

        ArrayList<Node> finished = new ArrayList<>();
        while (!this.queue.isEmpty()) {
            Node currentNode = this.queue.dequeue();
            int weight = currentNode.getWeight();
            int value = currentNode.getValue();

            if (value == dest) { // arrive at destination
                finished.add(currentNode);
                visited.add(value);
                break;
            }


            int[] vertices = graph.getNodeVertices(value).adjacentNodeList();
            for (int i = 0; i < 4; i++) {
                if (vertices[i] != -1) {
                    int vertexValue = vertices[i];

                    int vertexWeight = (immovable.contains(vertexValue) ? 100 : 1) + weight;
                    if (prioritizeRow) { // prioritize aligning the node with the row first
                        if (i % 2 == 1) { // vertex is left or right of the current Node
                            vertexWeight += 5;
                        }
                    } else { // prioritize aligning the node with the column first
                        if (i % 2 == 0) { // vertex is above or below the current Node
                            vertexWeight += 5;
                        }
                    }

                    // check if vertex has been visited previously
                    if (visited.contains(vertexValue)) { // compare its current registered weight with the vertex weight
                        if (this.queue.getWeight(vertexValue) > vertexWeight) {
                            this.queue.updateWeight(vertexValue, vertexWeight);
                            this.queue.updateParent(vertexValue, value);
                        }
                    } else { // not visited this vertex yet
                        // prevent moving vertices in immovable by giving a larger weight
                        int vertexDistance = manhattanDistance(vertexValue, dest, graph);
                        this.queue.enqueue(new Node(vertexWeight, vertexWeight + vertexDistance, vertexValue, value));
                        visited.add(vertexValue);
                    }
                }
            }
            finished.add(currentNode);
        }

//        for (Node node : finished) {
//            System.out.println("(" + node.getValue() + ","+ node.getParent() + ")");
//        }

        // retrace the steps in finished to get the final path
        ArrayList<Integer> path = new ArrayList<>();
        Node start = finished.get(finished.size() - 1);
        while (start.getValue() != src) {
            path.add(start.getValue());
            int parent = start.getParent();
            for (Node node : finished) {
                if (node.getValue() == parent) {
                    start = node;
                }
            }
        }
        path.add(start.getValue());
        return path;
    }

    /**
     * Calculate the approximate distance from the current node
     * the the destination node using Manhattan distance
     * @param successorNode index of the successor node
     * @return              Manhattan distance from the current node the destination
     */
    private int manhattanDistance(int successorNode, int dest, Graph graph) {
        int dimension = graph.getDimension();

        int currentX = successorNode / dimension;
        int currentY = successorNode % dimension;

        int destX = dest / dimension;
        int destY = dest % dimension;

        return Math.abs(destX - currentX) + Math.abs(destY - currentY);
    } // end of manhattanDistance()



} // end of AStar.java
