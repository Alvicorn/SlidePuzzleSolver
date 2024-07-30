/**
 * TestAStarSearch.java
 *
 * @author Alvin Tsang
 *
 * Unit tests for AStarSearch.java
 */
package solver.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import solver.astar.AStarSearch;
import solver.astar.graph.Graph;


class TestAStarSearch {

    @Test
    void testTwoByTwo() {
        AStarSearch search = new AStarSearch();

        ArrayList<Integer> puzzleState = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        Graph g = new Graph(2, puzzleState);
        g.printGraph();
        System.out.println();

        ArrayList<Integer> p1 = search.shortestPath(0, 3, g, new HashMap<>(), true);
        ArrayList<Integer> p2 = search.shortestPath(0, 3, g, new HashMap<>(), false);
        ArrayList<Integer> p3 = search.shortestPath(0, 3, g, new HashMap<>() {{put(1, 1000);}}, true);
        ArrayList<Integer> p4 = search.shortestPath(0, 3, g, new HashMap<>() {{put(2, 1000);}}, false);

        System.out.println("3 to 0 (prioritize row):\t\t\t" + p1);
        System.out.println("3 to 0 (prioritize col):\t\t\t" + p2);
        System.out.println("3 to 0 without [1] (prioritize row):\t" + p3);
        System.out.println("3 to 0 without [2] (prioritize col):\t" + p4);

        assertAll(
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(0, 1, 3)).toArray(), p1.toArray()),
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(0, 2, 3)).toArray(), p2.toArray()),
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(0, 2, 3)).toArray(), p3.toArray()),
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(0, 1, 3)).toArray(), p4.toArray())
        );
    } // end of testsTwoByTwo()

    @Test
    void testThreeByThree() {
        AStarSearch search = new AStarSearch();

        ArrayList<Integer> puzzleState = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        Graph g = new Graph(3, puzzleState);
        g.printGraph();
        System.out.println();

        ArrayList<Integer> p1 = search.shortestPath(0, 8, g, new HashMap<>(), true);
        ArrayList<Integer> p2 = search.shortestPath(0, 8, g, new HashMap<>(), false);

        System.out.println("8 to 0 (prioritize row):\t\t\t" + p1);
        System.out.println("8 to 0 (prioritize col):\t\t\t" + p2);

        assertAll(
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 5, 8)).toArray(), p1.toArray()),
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(0, 3, 6, 7, 8)).toArray(), p2.toArray())
        );
    } // end of testThreeByThree()

    @Test
    void testThreeByThreeWithBlocks() {
        AStarSearch search = new AStarSearch();

        ArrayList<Integer> puzzleState = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        Graph g = new Graph(3, puzzleState);
        g.printGraph();
        System.out.println();

        ArrayList<Integer> p3 = search.shortestPath(0, 8, g, new HashMap<>() {{put(1, 1000);}}, true);
        ArrayList<Integer> p4 = search.shortestPath(0, 8, g, new HashMap<>() {{put(2, 1000);}}, false);
        ArrayList<Integer> p5 = search.shortestPath(0, 8, g, new HashMap<>() {{put(4, 1000);put(5, 1000);}}, true);

        System.out.println("8 to 0 without [1] (prioritize row):\t" + p3);
        System.out.println("8 to 0 without [2] (prioritize col):\t" + p4);
        System.out.println("8 to 0 without [4,5] (prioritize col):\t" + p5);

        assertAll(
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(0, 3, 4, 5, 8)).toArray(), p3.toArray()),
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(0, 3, 6, 7, 8)).toArray(), p4.toArray()),
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(0, 3, 6, 7, 8)).toArray(), p5.toArray())
        );
    } // end of threeByThreeWithBlocks()

} // end of TestAStarSearch.java