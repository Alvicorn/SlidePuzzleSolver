/**
 * PQNode.java
 *
 * @ author Alvin Tsang
 *
 * Node for a priority queue
 */
package solver.astar.minheap;

public class PQNode {

    int weight;
    int totalCost;
    int value;
    int parent;

    public PQNode(int weight, int totalCost, int value, int parent) {
        this.weight = weight;
        this.totalCost = totalCost;
        this.value = value;
        this.parent = parent;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public int getParent() {
        return parent;
    }

} // end of PQNode.java
