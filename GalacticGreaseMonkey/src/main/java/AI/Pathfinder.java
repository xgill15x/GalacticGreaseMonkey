package AI;

import Game.GamePanel;

import java.util.ArrayList;

/**
 * This class is for using AI to find the shortest path.
 * @author Ryan
 */
public class Pathfinder {

    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    /**
     * This method is constructor of Pathfinder class.
     * @param gp GamePanel object
     */
    public Pathfinder(GamePanel gp) {
        this.gp = gp;
        instantiateNodes();
    }


    /**
     * This method is for instantiating nodes.
     */
    public void instantiateNodes() {
        node = new Node[gp.maxScreenCol][gp.maxScreenRow];

        int col = 0;
        int row = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            node[col][row] = new Node(col,row);
            col++;
            if(col == gp.maxScreenCol) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * This class is for reset nodes.
     */
    public void resetNodes() {

        int col = 0;
        int row = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){

            // Reset open, checked and solid state
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if(col == gp.maxScreenCol) {
                col = 0;
                row++;
            }
        }

        // Reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    /**
     * This method is for setting nodes value.
     * @param startCol start colum of path.
     * @param startRow start row of path.
     * @param goalCol goal colum of path.
     * @param goalRow goal row of path.
     */
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        // Set start and goal nodes
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow){
            // SET SOLID NODE
            // CHECK TILES
            int tileNum = gp.tileManager.mapTileNum[col][row];
            if(gp.tileManager.tile[tileNum].collision) {
                node[col][row].solid = true;
            }

            // SET COST
            getCost(node[col][row]);

            col++;
            if(col == gp.maxScreenCol){
                col = 0;
                row++;
            }
        }
    }

    /**
     * This method is for calculating cost of path.
     * @param node Node object.
     */
    public void getCost(Node node){

        // G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F cost
        node.fCost = node.gCost + node.hCost;
    }

    /**
     * This class is for determining whether the path is available.
     * @return whether available of path.
     */
    public boolean search() {
        while (!goalReached && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;

            // Check current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // Open the Up node
            if (row - 1 >= 0) {
                openNode(node[col][row-1]);
            }

            // Open the Down node
            if (row + 1 < gp.maxScreenRow) {
                openNode(node[col][row+1]);
            }

            // Open the Left node
            if (col - 1 >= 0) {
                openNode(node[col-1][row]);
            }

            // Open the Right node
            if (col + 1 < gp.maxScreenCol) {
                openNode(node[col+1][row]);
            }

            // Find the best node
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {

                // Check if this node's F cost is better
                if(openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }

                else if(openList.get(i).fCost == bestNodefCost) {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }

            }
            // If no node in openList, end loop
            if(openList.size() == 0){
                break;
            }

            // After the loop, openList[bestNodeIndex] is the next step
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }

        return goalReached;
    }

    /**
     * This method is for adding node to list.
     * @param node Node object
     */
    public void openNode (Node node){
        if (!node.open && !node.checked && !node.solid){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    /**
     * This method is for Tracking path of AI.
     */
    public void trackThePath() {
        Node current = goalNode;

        while(current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }

}