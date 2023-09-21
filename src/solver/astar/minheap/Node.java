/**
 * Node.java
 *
 * Node for a minheap.
 */

package solver.astar.minheap;


public class Node {

    int weight;
    int totalCost;
    int value;
    int parent;

    public Node(int weight, int totalCost, int value, int parent) {
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

} // end of Node.java