package Game;

import Entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class GamePanelTest {
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
    void gameOverTest() {
        gp.currentGameState = gp.playingState;
        gp.player.score = -1;
        gp.update();
        assertEquals(gp.loseState, gp.currentGameState);
    }

    @Test
    void pauseStateTest(){
        KeyEvent pauseKey = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_P,'P');
        gp.getKeyListeners()[0].keyPressed(pauseKey);

        gp.currentGameState = 1;
        keyHandler.keyPressed(pauseKey);
        gp.update();
        assertEquals(gp.pausedState, gp.currentGameState);
    }

    @Test
    void spawnDiamondIfFrameIsMultipleOf100() {
        int sizeOfDiamondArrayBefore100Frames = gp.diamond.size();
        gp.diamondSpawnTime = 99;   //starting on 99th frame
        gp.currentGameState = 1;
        gp.update(); //should add 1 diamond item to diamond array because update() invokes 100th frame

        assertEquals(gp.diamond.size(), sizeOfDiamondArrayBefore100Frames+1);   //size of diamond array should be 1 larger than before
        assertEquals(gp.diamondSpawnTime, 0); //diamond spawn counter should be reset
    }
    @Test
    void noDiamondSpawnedIfFrameIsntMulipleOf100() {
        int sizeOfDiamondArrayBefore51Frames = gp.diamond.size();
        gp.diamondSpawnTime = 50;   //starting on 50th frame
        gp.update(); //should not add diamond item to diamond array because update() invokes 51tst frame (which isn't a multiple of 100)

        assertEquals(gp.diamond.size(), sizeOfDiamondArrayBefore51Frames);   //size of diamond array should stay same as before
    }
}