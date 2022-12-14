package Entity;

import Game.GamePanel;
import Game.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

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
    void testGetPlayerImage() {
        player.getPlayerImage();
        assertNotNull(player.up1);
        assertNotNull(player.up2);
        assertNotNull(player.down1);
        assertNotNull(player.down2);
        assertNotNull(player.left1);
        assertNotNull(player.left2);
        assertNotNull(player.right1);
        assertNotNull(player.right2);
    }

    @Test
    void testUpdate() {

        //simulating down key press
        KeyEvent downKey = new KeyEvent(gp, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_S,'S');
        gp.getKeyListeners()[0].keyPressed(downKey);

        //test player update
        keyHandler.keyPressed(downKey);
        player.update();
        keyHandler.keyReleased(downKey);
        assertEquals("down", player.direction);     //testing direction

        player.spriteCounter = 11;
        keyHandler.keyPressed(downKey);
        player.update();
        assertEquals(2, player.spriteNum);  //testing sprite num of main char
    }

    @Test
    void testPickUpSpaceshipPart() {
        int currentScore = player.score;
        player.pickUpSpaceshipPart(0);
        int newScore = player.score;

        assertEquals(1, player.partsCollected);
        assertEquals(currentScore+300, newScore);
    }

    @Test
    void testPickUpDiamond() {
        int currentScore = player.score;
        player.pickUpDiamond(0);
        int newScore = player.score;

        assertEquals(currentScore+500, newScore);
    }

    @Test
    void collideBlackhole() {
        int currentScore = player.score;
        player.collideBlackhole(0);
        int newScore = player.score;

        assertEquals(currentScore-300, newScore);
    }

    @Test
    void testCollideOpenedDoor() {
        gp.currentGameState = 1; //put into play state
        player.partsCollected = 2;
        player.collideOpenedDoor(0);
        assertEquals(gp.winState, gp.currentGameState); //should win bc all parts are collected
    }
    @Test
    void testCollideClosedDoor() {
        gp.currentGameState = 1; //put into play state
        player.partsCollected = 1;
        player.collideOpenedDoor(0);
        assertEquals(gp.playingState, gp.currentGameState); //should remain in playstate bc all 2 parts are not collected
    }

    @Test
    void damageAlien() {
        int alienHealth = gp.alien[0].score;
        player.damageAlien(0);
        int newAlienHealth = gp.alien[0].score;
        assertEquals(alienHealth-1, newAlienHealth);
    }

    @Test
    void testPlayerReset() {
        player.playerReset();
        assertEquals(48, player.worldX);
        assertEquals(48, player.worldY);
        assertEquals("down", player.direction);
        assertEquals(0, player.partsCollected);
        assertEquals(0, player.score);
    }
}