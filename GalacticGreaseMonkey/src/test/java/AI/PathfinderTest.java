package AI;

import Entity.Player;
import Game.GamePanel;
import Game.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PathfinderTest {
    GamePanel gp;
    KeyHandler keyHandler;
    Player player;

    @BeforeEach
    public void setUpGamePanel() {
        gp = new GamePanel();
        gp.setupGame();

        keyHandler = new KeyHandler(gp);
        player = new Player(gp, keyHandler);
    }

    @Test
    void instantiateNodesTest() {
        Pathfinder pf = new Pathfinder(gp);

        for(int col = 0; col < gp.maxScreenCol; col++) {
            for(int row = 0; row < gp.maxScreenRow; row++){
                assertEquals(pf.node[col][row].col, col);
                assertEquals(pf.node[col][row].row, row);
            }
        }
    }

    @Test
    void resetNodesTest() {
        Pathfinder pf = new Pathfinder(gp);
        pf.resetNodes();

        for(int col = 0; col < gp.maxScreenCol; col++) {
            for(int row = 0; row < gp.maxScreenRow; row++){
                assertFalse(pf.node[col][row].open);
                assertFalse(pf.node[col][row].checked);
                assertFalse(pf.node[col][row].solid);

                assertFalse(pf.goalReached);
                assertEquals(0, pf.step);
            }
        }
    }

    @Test
    void setNodesTest() {
        Pathfinder pf = new Pathfinder(gp);
        pf.setNodes(0,0,0,0);

        assertTrue(pf.node[0][0].solid);
    }

    @Test
    void openNodeTest() {
        Pathfinder pf = new Pathfinder(gp);
        pf.openNode(pf.node[0][0]);

        assertTrue(pf.node[0][0].open);
    }

    @Test
    void getCostTest() {
        Pathfinder pf = new Pathfinder(gp);
        pf.startNode = pf.node[1][1];
        pf.goalNode = pf.node[1][3];

        pf.getCost(pf.node[1][1]);
        assertEquals(0, pf.node[1][1].gCost);
        assertEquals(2, pf.node[1][1].hCost);
        assertEquals(2, pf.node[1][1].fCost);
    }
}
