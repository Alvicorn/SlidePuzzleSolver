package solver.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import solver.astar.AStarSearch;
import solver.astar.graph.Graph;


class AStarSearchTest {

    @Test
    void TwoByTwoAStarSearch() {
        AStarSearch search = new AStarSearch();

        ArrayList<Integer> puzzleState = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        Graph g = new Graph(2, puzzleState);
        g.printGraph();
        System.out.println();

        ArrayList<Integer> p1 = search.shortestPath(0, 3, g, new ArrayList<>(), true);
        ArrayList<Integer> p2 = search.shortestPath(0, 3, g, new ArrayList<>(), false);
        ArrayList<Integer> p3 = search.shortestPath(0, 3, g, new ArrayList<>(List.of(1)), true);
        ArrayList<Integer> p4 = search.shortestPath(0, 3, g, new ArrayList<>(List.of(2)), false);

        System.out.println("3 to 0 (prioritize row):\t\t\t" + p1);
        System.out.println("3 to 0 (prioritize col):\t\t\t" + p2);
        System.out.println("3 to 0 without [1] (prioritize row):\t" + p3);
        System.out.println("3 to 0 without [2] (prioritize col):\t" + p4);

        assertAll(
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(3, 1, 0)).toArray(), p1.toArray()),
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(3, 2, 0)).toArray(), p2.toArray()),
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(3, 2, 0)).toArray(), p3.toArray()),
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(3, 1, 0)).toArray(), p4.toArray())
        );
    } // end of TwoByTwoAStarSearch()

    @Test
    void ThreeByThreeAStarSearch() {
        AStarSearch search = new AStarSearch();

        ArrayList<Integer> puzzleState = new ArrayList<>(Arrays.asList(0, 1, 3,
                                                                       8, 4, 5,
                                                                       6, 7, 2));
        Graph g = new Graph(3, puzzleState);
        g.printGraph();
        System.out.println();

        ArrayList<Integer> p1 = search.shortestPath(0, 2, g, new ArrayList<>(), true);
        ArrayList<Integer> p2 = search.shortestPath(0, 2, g, new ArrayList<>(), false);

        System.out.println("2 to 0 (prioritize row):\t\t\t" + p1);
        System.out.println("2 to 0 (prioritize col):\t\t\t" + p2);

        assertAll(
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(2, 5, 3, 1, 0)).toArray(), p1.toArray()),
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(2 , 7, 6, 8, 0)).toArray(), p2.toArray())
        );
    } // end of TwoByTwoAStarSearch()

    @Test
    void ThreeByThreeWithBlocksAStarSearch() {
        AStarSearch search = new AStarSearch();

        ArrayList<Integer> puzzleState = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        Graph g = new Graph(3, puzzleState);
        g.printGraph();
        System.out.println();

        ArrayList<Integer> p3 = search.shortestPath(0, 8, g, new ArrayList<>(List.of(1)), true);
        ArrayList<Integer> p4 = search.shortestPath(0, 8, g, new ArrayList<>(List.of(7)), false);
        ArrayList<Integer> p5 = search.shortestPath(0, 8, g, new ArrayList<>(List.of(4, 5)), true);

        System.out.println("8 to 0 without [1] (prioritize row):\t" + p3);
        System.out.println("8 to 0 without [2] (prioritize col):\t" + p4);
        System.out.println("8 to 0 without [4,5] (prioritize col):\t" + p5);

        assertAll(
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(8, 5, 4, 3, 0)).toArray(), p3.toArray()),
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(8, 5, 4, 3, 0)).toArray(), p4.toArray()),
                () -> assertArrayEquals(new ArrayList<>(Arrays.asList(8, 7, 6, 3, 0)).toArray(), p5.toArray())
        );
    } // end of TwoByTwoAStarSearch()


}