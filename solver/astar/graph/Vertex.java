package solver.astar.graph;

public class Vertex {

    private int src;
    private int dest;
    private int position;
    public Vertex(int src, int dest, int position) {
        this.src = src;
        this.dest = dest;
        this.position = position;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public int getDest() {
        return dest;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(char position) {
        this.position = position;
    }
}
