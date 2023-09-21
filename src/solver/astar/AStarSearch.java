/**
 * AStarSearch.java
 *
 * Perform the A* search on a graph.
 */

package solver.astar;

import java.util.ArrayList;
import java.util.Arrays;

import solver.astar.minheap.Node;
import solver.astar.graph.Graph;


public class AStarSearch {

    private final PriorityQueue queue;

    public AStarSearch() {
        this.queue = new PriorityQueue();
    }

    /**
     * Perform an A* search from src to distance.
     *
     * @param src Source position.
     * @param dest Destination position.
     * @param graph Model of the target graph.
     * @param immovable List of positions that can not be included in the shortest path.
     * @param prioritizeRow Prioritize row traveling first if True.
     * @return The shortest path from src to dest.
     * */
    public ArrayList<Integer> shortestPath(int src, int dest, Graph graph,
                                           ArrayList<Integer> immovable, boolean prioritizeRow) {
        ArrayList<Integer> visited = new ArrayList<>();

        // start at the src node
        visited.add(src);
        this.queue.enqueue(new Node(0, manhattanDistance(src, dest, graph), src, -1));

        ArrayList<Node> finished = new ArrayList<>();
        while (!this.queue.isEmpty()) {
            Node currentNode = this.queue.dequeue();
            int parentWeight = currentNode.getWeight();
            int parentValue = currentNode.getValue();

            if (parentValue == dest) { // arrive at destination
                finished.add(currentNode);
                visited.add(parentValue);
                break;
            }

            int[] vertices = graph.getNodeVertices(parentValue).adjacentNodeList();
            for (int i = 0; i < 4; i++) {
                if (vertices[i] != -1) {
                    int childValue = vertices[i];

                    int childWeight = (immovable.contains(childValue) ? 100 : 1) + parentWeight;
                    if (prioritizeRow) { // prioritize aligning the node with the row first
                        if (i % 2 == 0) { // vertex is left or right of the current Node
                            childWeight += 5;
                        }
                    } else { // prioritize aligning the node with the column first
                        if (i % 2 == 1) { // vertex is above or below the current Node
                            childWeight += 5;
                        }
                    }

                    // check if vertex has been visited previously
                    if (visited.contains(childValue)) { // compare its current registered weight with the vertex weight
                        if ((this.queue.getWeight(childValue) > childWeight) &&
                                Arrays.asList(graph.getNodeVertices(childValue).adjacentNodeList()).contains(parentValue)) {
                            this.queue.updateWeight(childValue, childWeight);
                            this.queue.updateParent(childValue, parentValue);
                        }
                    } else { // not visited this vertex yet
                        // prevent moving vertices in immovable by giving a larger weight
                        int childDistance = manhattanDistance(childValue, dest, graph);
                        this.queue.enqueue(new Node(childWeight, childWeight + childDistance, childValue, parentValue));
                        visited.add(childValue);
                    }
                }
            }
            finished.add(currentNode);
        }

        // retrace the steps in finished to get the final path
        ArrayList<Integer> path = new ArrayList<>();
        Node start = finished.get(finished.size() - 1);
        while (start.getValue() != src) {
            path.add(start.getValue());
            int parent = start.getParent();
            for (Node node : finished) {
                if (node.getValue() == parent ) {
                    start = node;
                }
            }
        }
        path.add(start.getValue());
        return path;
    } // end of shortestPath()

    /**
     * Calculate the approximate distance from the current node
     * the destination node using Manhattan distance
     *
     * @param successorNode index of the successor node.
     * @return Manhattan distance from the current node the destination.
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