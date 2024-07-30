/**
 * MinHeap.java
 *
 * @author Alvin Tsang
 *
 * Minimum heap using an arraylist as the concrete data type
 */
package solver.astar.minheap;

import java.util.ArrayList;


public class MinHeap {
    private final ArrayList<PQNode> heap = new ArrayList<>();

    public MinHeap() {}

    public boolean isEmpty() {
        return this.heap.size() == 0;
    }

    public void setParent(int currentNode, int updatedParent) {
        int index = findIndex(currentNode);
        if (index < this.heap.size()) { // index was found
            this.heap.get(index).parent = updatedParent;
        }
    } // end of updateParent()

    public void setWeight(int currentNode, int updatedWeight) {
        int index = findIndex(currentNode);
        if (index < this.heap.size()) { // index was found
            this.heap.get(index).weight = updatedWeight;
        }
    } // end of updateParent()

    public int getWeight(int node) {
        int index = findIndex(node);
        return index < this.heap.size() ? this.heap.get(index).weight : -1;
    } // end of getWeight()

    private int findIndex(int src) {
        int index = 0;
        boolean found = false;
        while (index < this.heap.size() && !found) {
            if (this.heap.get(index).parent == src) {
                found = true;
            } else {
                index++;
            }
        }
        return index;
    } // end of findIndex()


    /**
     * Get the smallest value in the min heap without modifying the heap
     * @return the smallest value in the min heap, -1 if empty
     */
    public PQNode getMin() {
        return this.heap.size() > 0 ? this.heap.get(0) : null;
    } // end of getMin()

    /**
     * Remove the smallest value in the min heap
     * @return the smallest value in the min heap, -1 if empty
     */
    public PQNode extractMin() {
        if (isEmpty()) {
            return null;
        }

        PQNode extract = this.heap.get(0);

        // replace the root with the last node, remove the last node
        // then heapify
        if (this.heap.size() == 1) { // 1 element in the heap, no need to reheap
            this.heap.remove(0);
        } else {
            this.heap.set(0, this.heap.get(this.heap.size() - 1));
            this.heap.remove(this.heap.size() - 1);
            heapify(0);
        }
        return extract;
    } // end of extractMin()

    /**
     * Insert a value into the min heap
     * @param node node containing the weight, total cost, value and parent value
     */
    public void insert(PQNode node) {
        this.heap.add(node);

        int current = this.heap.size() - 1;
        while (this.heap.get(current).totalCost < this.heap.get(getParent(current)).totalCost) {
            swap(getParent(current), current);
            current = getParent(current);
        }
    } // end of insert()

    /**
     * Restore the heap after modifications
     * @param index position to start the heapify process
     */
    private void heapify(int index) {
        if (!isLeaf(index)) {
            int swapIndex;

            int leftChild = getLeftChild(index);
            int rightChild = getRightChild(index);

            if (rightChild < this.heap.size()) {
                if (this.heap.get(leftChild).totalCost< this.heap.get(rightChild).totalCost) {
                    swapIndex = leftChild;
                } else {
                     swapIndex = rightChild;
                }

                if (this.heap.get(index).totalCost > this.heap.get(leftChild).totalCost ||
                        this.heap.get(index).totalCost > this.heap.get(rightChild).totalCost) {
                    swap(index, swapIndex);
                    heapify(swapIndex);
                }
            }
        }
    } // end of heapify()

    /**
     * Swap two the positions of two nodes
     * @param parent    index of the parent node
     * @param child     index of the child node
     */
    private void swap(int parent, int child) {
        PQNode temp = this.heap.get(parent);
        this.heap.set(parent, this.heap.get(child));
        this.heap.set(child, temp);
    } // end of swap()

    /**
     * Get the index of the parent node
     * @param index index of the child node
     * @return      index of the parent node
     */
    private int getParent(int index) {
        return index / 2;
    }

    /**
     * Get the index of the left child
     * @param index index of the parent node
     * @return      index of the left child
     */
    private int getLeftChild(int index) {
        return index * 2;
    }

    /**
     * Get the index of the right child
     * @param index index of the parent node
     * @return      index of the right child
     */
    private int getRightChild(int index) {
        return index * 2 + 1;
    }

    /**
     * Check if the index is a leaf node in the min heap
     * @param index node in question
     * @return  true if the node is a leaf, false otherwise
     */
    private boolean isLeaf(int index) {
        return index > this.heap.size() / 2;
    }

} // end of MinHeap.java