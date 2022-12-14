package Entity;

import Game.GamePanel;
import Game.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class AlienTest {
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
    public void alienConstructorTest() { // Test constructor
        Alien alien = new Alien(gp);

        assertEquals(1,alien.score);
        assertEquals(2,alien.speed);
        assertEquals(1,alien.type);
        assertEquals(3,alien.hitBox.x);
        assertEquals(18,alien.hitBox.y);
        assertEquals(42,alien.hitBox.width);
        assertEquals(30,alien.hitBox.height);
        assertEquals(alien.hitBox.x,alien.solidAreaDefaultX);
        assertEquals(alien.hitBox.y,alien.solidAreaDefaultY);
        assertFalse(alien.aggressive);
    }

    @Test
    void playerOutOfRangeTest(){
        player.worldX = 48;
        player.worldY = 48;

        Alien alien = new Alien(gp);
        alien.worldX = 20 * gp.tileSize;
        alien.worldY = 14 * gp.tileSize;

        alien.update();
        assertFalse(alien.onPath);
    }

    @Test
    void playerInRangeTest(){
        player.worldX = 48;
        player.worldY = 48;

        Alien alien = new Alien(gp);
        alien.worldX = 48;
        alien.worldY = 48 + 2 * gp.tileSize;

        alien.update();
        assertTrue(alien.onPath);
    }

    @Test
    void actionLockOnTest() {
        Alien alien = new Alien(gp);
        alien.actionLockCounter = 0;
        String initialDirection = alien.direction;

        alien.setAction();
        assertEquals(initialDirection, alien.direction); // Shouldn't change due to action lock
    }

    @Test
    void actionLockOffTest() {
        Alien alien = new Alien(gp);
        alien.actionLockCounter = 119;

        alien.setAction();
        assertEquals(0, alien.actionLockCounter); // Counter should reset to 0 after switching direction
    }

    @Test
    void alienInvincibilityTest(){
        gp.alien[0] = new Alien(gp);

        // Initial damage makes alien invincible
        gp.player.damageAlien(0);
        gp.alien[0].update();
        assertTrue(gp.alien[0].invincible);

        // After counter surpasses 40, invincibility should turn off
        gp.alien[0].invincibleCounter = 41;
        gp.alien[0].update();
        assertFalse(gp.alien[0].invincible);
    }

    @Test
    void alienTakesDamageTest(){
        gp.alien[0] = new Alien(gp);

        int initialAlienScore = gp.alien[0].score;

        // Damaging alien should reduce score by 1
        gp.player.damageAlien(0);
        gp.alien[0].update();
        assertEquals(initialAlienScore-1, gp.alien[0].score);
    }
}
