/**
 * PriorityQueue.java
 *
 * @author Alvin Tsang
 *
 * Minimum Priority queue using a minheap
 */
package solver.astar;

import solver.astar.minheap.MinHeap;
import solver.astar.minheap.PQNode;

public class PriorityQueue {

    private final MinHeap heap = new MinHeap();

    public PriorityQueue() {}

    public void enqueue(PQNode node) {
        heap.insert(node);
    }

    public PQNode dequeue() {
        return heap.extractMin();
    }

    public void clear() {
        while (!heap.isEmpty()) {heap.extractMin();}
    } // end of clear()

    public boolean isEmpty() {
        return heap.isEmpty();
    }

} // end of PriorityQueue.java
