/**
 * PriorityQueue.java
 *
 * Minimum Priority queue.
 */

package solver.astar;

import solver.astar.minheap.MinHeap;
import solver.astar.minheap.Node;


public class PriorityQueue {

    private final MinHeap heap;

    public PriorityQueue() {
        this.heap = new MinHeap();
    }

    public void enqueue(Node node) {
        this.heap.insert(node);
    }

    public Node dequeue() {
        return this.heap.extractMin();
    }

    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    public void updateParent(int currentNode, int updatedValue) {
        this.heap.setParent(currentNode, updatedValue);
    }

    public void updateWeight(int currentNode, int updatedValue) {
        this.heap.setWeight(currentNode, updatedValue);
    }

    public int getWeight(int node) {
        return this.heap.getWeight(node);
    }

} // end of PriorityQueue.java