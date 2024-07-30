/**
 * AdjacentNodes.java
 *
 * @author Alvin Tsang
 *
 * Class recording adjacent nodes and its direction relative to the source node.
 */

package solver.astar.graph;


public class AdjacentNodes {

    private int top;
    private int right;
    private int bottom;
    private int left;

    public AdjacentNodes() {
        this.top = -1;
        this.right = -1;
        this.bottom = -1;
        this.left = -1;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int[] adjacentNodeList() {
        return new int[] { this.top, this.right, this.bottom, this.left};
    }
} // end of AdjacentNodes.java
