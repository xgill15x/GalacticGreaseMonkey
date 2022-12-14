package Entity;

import Game.GamePanel;
import Game.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {

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

    // Constructor tests
    @Test
    void hitboxValuesTest(){ // hitbox
        Entity entity = new Entity(gp);

        assertEquals(0,entity.hitBox.x);
        assertEquals(0,entity.hitBox.y);
        assertEquals(48,entity.hitBox.width);
        assertEquals(48,entity.hitBox.height);
        assertEquals(0,entity.solidAreaDefaultX);
        assertEquals(0,entity.solidAreaDefaultY);
    }

    @Test
    void entityStateTest(){ // state
        Entity entity = new Entity(gp);

        assertFalse(entity.invincible);
        assertFalse(entity.onPath);
        assertTrue(entity.alive);
        assertFalse(entity.dying);
        assertEquals(0, entity.dyingCounter);
    }

    @Test
    void entityAttributesTest() {
        Entity entity = new Entity(gp);

        assertEquals(0, entity.spriteCounter);
        assertEquals(1, entity.spriteNum);
        assertEquals(2, entity.attack);
        assertFalse(entity.hpBarOn);
        assertEquals(0, entity.hpBarCounter);

        assertEquals(0, entity.score);
        assertEquals(2, entity.maxLife);
    }
}
