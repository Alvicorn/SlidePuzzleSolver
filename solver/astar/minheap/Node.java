/**
 * PQNode.java
 *
 * Node for a priority queue
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

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }
}
