package Game;

import Entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {

    GamePanel gp;
    KeyHandler keyHandler;
    Player player;

    @BeforeEach
    public void setUpUserInterface() {
        gp = new GamePanel();
        gp.setupGame();

        keyHandler = new KeyHandler(gp);
        player = new Player(gp, keyHandler);
    }

    @Test
    void showPauseScreenWhenPauseIsPressed() {
        //simulating 'enter' key press to start game
        KeyEvent key = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_ENTER,'\n');
        gp.getKeyListeners()[0].keyPressed(key);

        //simulating pause key press
        KeyEvent key2 = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_P,'P');
        gp.getKeyListeners()[0].keyPressed(key2);

        // 2 indicates paused state
        assertEquals(2, gp.currentGameState);
    }

    @Test
    void showLostScreenWhenPlayerScoreIsNegative() {
        //player moves while score is less than 0 makes them lose
        player.score = -1;
        keyHandler.upPressed = true;
        player.update();

        // 3 indicates losing state
        assertEquals(3, gp.currentGameState);
    }
    @Test
    void continueInPlayStateWhenPlayerScoreIsNotNegative() {
        //player moves while score is not less than 0 should let player continue playing
        player.score = 0;
        keyHandler.upPressed = true;
        player.update();

        assertEquals(0, gp.currentGameState);
    }

    @Test
    void showWinScreenWhenPlayerHasAllPartsAndCollidesWithDoor() {
        //player has all spaceship parts and collides with door
        player.partsCollected = 2;
        player.collideOpenedDoor(1);

        // 4 indicates win state
        assertEquals(4, gp.currentGameState);
    }
    @Test
    void continueInPlayStateWhenPlayerDoesntHaveAllPartsAndCollidesWithDoor() {
        //player does not have all spaceship parts and collides with door
        player.partsCollected = 1;
        player.collideOpenedDoor(1);

        //0 indicates start screen and player hasn't changed it to victory state (4)
        assertEquals(0, gp.currentGameState);
    }

    @Test
    void startButtonInTitleScreenIsWorking() {
        KeyEvent key = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, '\n');
        gp.getKeyListeners()[0].keyPressed(key);
        assertEquals(1, gp.currentGameState);
    }
    @Test
    void changingGameLevelSuccessfully() {
        KeyEvent key = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_1, '\n');
        gp.getKeyListeners()[0].keyPressed(key);
        assertEquals(0, gp.userInterface.commandLevel);
        key = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_2, '\n');
        gp.getKeyListeners()[0].keyPressed(key);
        assertEquals(1, gp.userInterface.commandLevel);
        key = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_3, '\n');
        gp.getKeyListeners()[0].keyPressed(key);
        assertEquals(2, gp.userInterface.commandLevel);
    }
}