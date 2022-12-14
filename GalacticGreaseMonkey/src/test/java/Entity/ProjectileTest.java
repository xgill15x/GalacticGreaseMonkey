package Entity;

import Game.CollisionChecker;
import Game.GamePanel;
import Game.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileTest {

    Entity user;
    GamePanel gp;

    @BeforeEach
    public void set_up_projectile_collision(){
        gp = new GamePanel();
        gp.setupGame();

        user = new Entity(gp);
    }

    @Test
    void projectile_collision_test(){
        user = gp.player;
        int alienIndex = 1;

        // Damage reaction cause alien healthbar minus 1
        gp.player.damageAlien(alienIndex);
        assertEquals(0, gp.alien[alienIndex].score);
    }

}