package solver.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import solver.astar.minheap.MinHeap;
import solver.astar.minheap.PQNode;


class TestMinHeap {

    @Test
    @DisplayName("GetMin Method: Empty Heap")
    void testEmptyHeap() {
        MinHeap heap = new MinHeap();
        assertAll(
                () -> assertTrue(heap.isEmpty()),
                () -> assertNull(heap.getMin()),
                () -> assertNull(heap.extractMin())
        );
    } // end of testEmptyHeap()

    @Test
    @DisplayName("Insert Method: 1 Element")
    void testInsert1Element() {
        MinHeap heap = new MinHeap();
        heap.insert(new PQNode(1, 1, 1, -1));
        assertAll(
                () -> assertEquals(1, heap.getMin().getValue()),
                () -> assertEquals(1, heap.extractMin().getValue())
        );
    } // end of testInsert1Element()

    @Test
    @DisplayName("Insert Method: 1000 Element")
    void testInsert1000Element() {

        ArrayList<Integer> expected = new ArrayList<>();
        ArrayList<Integer> shuffled = new ArrayList<>();

        for (int i = 1; i <= 1000; i++) {
            expected.add(i);
            shuffled.add(i);
        }
        Collections.shuffle(shuffled);

        MinHeap heap = new MinHeap();
        for (int number : shuffled) {
            heap.insert(new PQNode(number, number, number, 0));
        }

        ArrayList<Integer> results = new ArrayList<>();
        while(!heap.isEmpty()) {
            results.add(heap.extractMin().getValue());
        }
        assertArrayEquals(expected.toArray(), results.toArray());
    } // end of testInsert1000Element()

} // end of MinHeapTest.java