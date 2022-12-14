package Game;

import Entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class KeyHandlerTest {

    GamePanel gp;
    KeyHandler keyHandler;
    Player player;

    @BeforeEach
    public void setUpKeyHandler() {
        gp = new GamePanel();
        gp.setupGame();

        keyHandler = new KeyHandler(gp);
        player = new Player(gp, keyHandler);
    }

    @Test
    void playerMovesDuringKeyPressIfInPlayState() {
        int playerYPosBeforeKeyPress = player.worldY;
        int playerXPosBeforeKeyPress = player.worldX;

        //simulating down key press
        KeyEvent downKey = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_S,'S');
        gp.getKeyListeners()[0].keyPressed(downKey);

        KeyEvent rightKey = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_D,'D');
        gp.getKeyListeners()[0].keyPressed(rightKey);

        //test downKeyPress direction
        keyHandler.keyPressed(downKey);
        player.update();
        keyHandler.keyReleased(downKey);
        assertEquals("down", player.direction);

        int playerYPosAfterKeyPress = player.worldY;

        //test rightKeyPress direction
        keyHandler.keyPressed(rightKey);
        player.update();
        keyHandler.keyReleased(rightKey);
        assertEquals("right", player.direction);

        int playerXPosAfterKeyPress = player.worldX;

        //players Y position after 1 frame should be its old position plus the players speed
        assertEquals(playerYPosBeforeKeyPress+player.speed, playerYPosAfterKeyPress);

        //players X position after 1 frame should be its old position plus the players speed
        assertEquals(playerXPosBeforeKeyPress+player.speed, playerXPosAfterKeyPress);
    }
    @Test
    void playerDoesNotMoveDuringKeyPressIfNotInPlayState() {

        gp.currentGameState = 3; //some arbitrary game state that isn't the playing state
        gp.update();

        int playerYPosBeforeKeyPress = player.worldY;
        int playerXPosBeforeKeyPress = player.worldX;

        //simulating down key press
        KeyEvent downKey = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_S,'S');
        gp.getKeyListeners()[0].keyPressed(downKey);

        KeyEvent rightKey = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_D,'D');
        gp.getKeyListeners()[0].keyPressed(rightKey);

        keyHandler.keyPressed(downKey);
        gp.update();
        keyHandler.keyReleased(downKey);
        assertEquals("down", player.direction);

        //players position should remain the same after key press
        int playerYPosAfterKeyPress = player.worldY;

        //test rightKeyPress direction
        keyHandler.keyPressed(rightKey);
        gp.update();
        keyHandler.keyReleased(rightKey);
        assertEquals("down", player.direction);

        int playerXPosAfterKeyPress = player.worldX;

        //position should stay the same since game is not in play state
        assertEquals(playerYPosBeforeKeyPress, playerYPosAfterKeyPress);
        assertEquals(playerXPosBeforeKeyPress, playerXPosAfterKeyPress);
    }

    @Test
    void testForReleaseKey() {
        //simulating down key press
        KeyEvent downKey = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_S,'S');
        gp.getKeyListeners()[0].keyPressed(downKey);

        KeyEvent rightKey = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_D,'D');
        gp.getKeyListeners()[0].keyPressed(rightKey);

        //test downKeyPress direction
        keyHandler.keyPressed(downKey);
        player.update();
        keyHandler.keyReleased(downKey);
        player.update();

        //test downKeyPress direction
        keyHandler.keyPressed(rightKey);
        player.update();

        assertEquals(false, keyHandler.downPressed);
        assertEquals(true, keyHandler.rightPressed); //key was not released so should remain true
    }
}