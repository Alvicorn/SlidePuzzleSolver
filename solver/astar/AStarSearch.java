/**
 * AStarSearch.java
 *
 * @author Alvin Tsang
 *
 * A star search algorithm on a graph using a minimum priority queue.
 */
package solver.astar;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

import solver.astar.minheap.PQNode;
import solver.astar.graph.Graph;


public class AStarSearch {

    private final PriorityQueue queue = new PriorityQueue();

    public AStarSearch() {}

    /**
     * Find the shortest path from src to dest in the graph
     * @param src starting vertex
     * @param dest destination vertex
     * @param graph graph to perform the search on
     * @param vertexWeights map of vertices in the graph with weights
     * @param prioritizeRow if true, find the shortest path to dest by aligning the src with dest's row first
     * @return the shortest path from src to dest
     */
    public ArrayList<Integer> shortestPath(int src, int dest, Graph graph,
                                           HashMap<Integer, Integer> vertexWeights, boolean prioritizeRow) {
        queue.clear();
        ArrayList<Integer> visited = new ArrayList<>();

        // start at the src node
        visited.add(src);
        this.queue.enqueue(new PQNode(0, manhattanDistance(src, dest, graph), src, -1));

        ArrayList<PQNode> finished = new ArrayList<>();
        while (!this.queue.isEmpty()) {
            PQNode currentNode = this.queue.dequeue();
            int weight = currentNode.getWeight();
            int value = currentNode.getValue();

            if (value == dest) { // arrive at destination
                finished.add(currentNode);
                visited.add(value);
                break;
            }

            int[] vertices = graph.getNodeVertices(value).adjacentNodeList();
            for (int i = 0; i < 4; i++) {
                int vertexValue = vertices[i];
                if (vertices[i] != -1 && !visited.contains(vertexValue)) {
                    int vertexWeight = vertexWeights.getOrDefault(vertexValue, 1) + weight;
//                    int vertexWeight = (immovable.contains(vertexValue) ? 1000 : 1) + weight;

                    if (prioritizeRow) { // prioritize aligning the node with the row first
                        if (i % 2 == 0) { // vertex is above or below of the current Node
                            vertexWeight += 100;
                        }
                    } else { // prioritize aligning the node with the column first
                        if (i % 2 == 1) { // vertex is left or right the current Node
                            vertexWeight += 100;
                        }
                    }

                    int vertexDistance = manhattanDistance(vertexValue, dest, graph);
                    queue.enqueue(new PQNode(vertexWeight, vertexWeight + vertexDistance, vertexValue, value));
                    visited.add(vertexValue); // Mark the vertex as visited after enqueueing
                }
            }
            finished.add(currentNode);
        }

        // retrace the steps in finished to get the final path
        ArrayList<Integer> path = new ArrayList<>();

        PQNode currentNode = finished.get(finished.size() - 1);
        while (currentNode.getValue() != src) {
            path.add(currentNode.getValue());
            int parent = currentNode.getParent();
            for (PQNode node : finished) {
                if (node.getValue() == parent) {
                    currentNode = node;
                    break;
                }
            }
        }
        path.add(currentNode.getValue());
        Collections.reverse(path);
        return path;
    } // end of shortestPath()

    /**
     * Calculate the approximate distance from the current node
     * the destination node using Manhattan distance
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
