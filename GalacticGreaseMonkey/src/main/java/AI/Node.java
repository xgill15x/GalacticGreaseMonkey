package AI;

/**
 * This class is for taking each tile in the map and turning it into a node
 * @author Ryan
 */
public class Node {
    Node parent;
    public int col;
    public int row;
    int gCost;
    int hCost;
    int fCost;
    boolean solid;
    boolean open;
    boolean checked;

    /**
     * This method is constructor of Node class.
     * @param col value of colum
     * @param row value of row
     */
    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
