package solver.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import solver.astar.minheap.MinHeap;
import solver.astar.minheap.Node;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {

    @Test
    @DisplayName("GetMin Method: Empty Heap")
    void EmptyHeap() {
        MinHeap heap = new MinHeap();
        assertAll(
                () -> assertTrue(heap.isEmpty()),
                () -> assertNull(heap.getMin()),
                () -> assertNull(heap.extractMin())
        );
    } // end of insert1Element()

    @Test
    @DisplayName("Insert Method: 1 Element")
    void insert1Element() {
        MinHeap heap = new MinHeap();
        heap.insert(new Node(1, 1, 1, -1));
        assertAll(
                () -> assertEquals(1, heap.getMin().getValue()),
                () -> assertEquals(1, heap.extractMin().getValue())
        );
    } // end of insert1Element()

    @Test
    @DisplayName("Insert Method: 1000 Element")
    void insert1000Element() {

        ArrayList<Integer> expected = new ArrayList<>();
        ArrayList<Integer> shuffled = new ArrayList<>();

        for (int i = 1; i <= 1000; i++) {
            expected.add(i);
            shuffled.add(i);
        }
        Collections.shuffle(shuffled);

        MinHeap heap = new MinHeap();
        for (int number : shuffled) {
            heap.insert(new Node(number, number, number, 0));
        }

        ArrayList<Integer> results = new ArrayList<>();
        while(!heap.isEmpty()) {
            results.add(heap.extractMin().getValue());
        }
        assertArrayEquals(expected.toArray(), results.toArray());
    } // end of insert1000Element()



} // end of MinHeapTest.java