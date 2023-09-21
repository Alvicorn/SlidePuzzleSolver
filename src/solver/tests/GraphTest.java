package solver.tests;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import solver.astar.graph.Graph;


class GraphTest {

    @Test
    @DisplayName("Check Graph: Full 2 by 2")
    public void TwoByTwoGraph() {
        ArrayList<Integer> puzzleState = new ArrayList<>(Arrays.asList(1, 0,
                                                                       3, 2));
        Graph g = new Graph(2, puzzleState);
        g.printGraph();

        assertAll(
                () -> assertArrayEquals(new int[] {-1, 0, 3, -1}, g.getNodeVertices(1).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {-1, -1, 2, 1}, g.getNodeVertices(0).adjacentNodeList()),

                () -> assertArrayEquals(new int[] {1, 2, -1, -1}, g.getNodeVertices(3).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {0, -1, -1, 3}, g.getNodeVertices(2).adjacentNodeList())
                );
    } // end of TwoByTwoGraph()

    @Test
    @DisplayName("Check Graph: Full 3 by 3")
    public void ThreeByThreeGraph() {
        ArrayList<Integer> puzzleState = new ArrayList<>(Arrays.asList(1, 3, 2,
                                                                       0 ,7, 4,
                                                                       6, 5, 8));
        Graph g = new Graph(3, puzzleState);
        g.printGraph();

        assertAll(
                () -> assertArrayEquals(new int[] {-1, 3, 0, -1}, g.getNodeVertices(1).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {-1, 2, 7, 1}, g.getNodeVertices(3).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {-1, -1, 4, 3}, g.getNodeVertices(2).adjacentNodeList()),
//
                () -> assertArrayEquals(new int[] {1, 7, 6, -1}, g.getNodeVertices(0).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {3, 4, 5, 0}, g.getNodeVertices(7).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {2, -1, 8, 7}, g.getNodeVertices(4).adjacentNodeList()),
//
                () -> assertArrayEquals(new int[] {0, 5, -1, -1}, g.getNodeVertices(6).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {7, 8, -1, 6}, g.getNodeVertices(5).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {4, -1, -1, 5}, g.getNodeVertices(8).adjacentNodeList())
        );
    } // end of ThreeByThreeGraph()

    @Test
    @DisplayName("Check Graph: Full 4 by 4")
    public void FourByFourGraph() {
        ArrayList<Integer> puzzleState = new ArrayList<>(Arrays.asList(0 , 1 , 2 , 3 ,
                                                                       4 , 5 , 6 , 7 ,
                                                                       8 , 9 , 10, 11,
                                                                       12, 13, 14, 15));
        Graph g = new Graph(4, puzzleState);
        g.printGraph();

        assertAll(
                () -> assertArrayEquals(new int[] {-1, 1, 4, -1}, g.getNodeVertices(0).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {-1, 2, 5, 0}, g.getNodeVertices(1).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {-1, 3, 6, 1}, g.getNodeVertices(2).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {-1, -1, 7, 2}, g.getNodeVertices(3).adjacentNodeList()),

                () -> assertArrayEquals(new int[] {0, 5, 8, -1}, g.getNodeVertices(4).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {1, 6, 9, 4},  g.getNodeVertices(5).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {2, 7, 10, 5}, g.getNodeVertices(6).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {3, -1, 11, 6},g.getNodeVertices(7).adjacentNodeList()),

                () -> assertArrayEquals(new int[] {4, 9, 12, -1}, g.getNodeVertices(8).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {5, 10, 13, 8}, g.getNodeVertices(9).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {6, 11, 14, 9}, g.getNodeVertices(10).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {7, -1, 15, 10}, g.getNodeVertices(11).adjacentNodeList()),

                () -> assertArrayEquals(new int[] {8, 13, -1, -1}, g.getNodeVertices(12).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {9, 14, -1, 12}, g.getNodeVertices(13).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {10, 15, -1, 13}, g.getNodeVertices(14).adjacentNodeList()),
                () -> assertArrayEquals(new int[] {11, -1, -1, 14}, g.getNodeVertices(15).adjacentNodeList())
        );
    } // end of FourByFourGraph()

} // end of GraphTest()