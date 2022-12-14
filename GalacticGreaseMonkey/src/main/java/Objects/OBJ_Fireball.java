package Objects;

import Entity.Projectile;
import Game.GamePanel;

import java.awt.*;

/**
 * This subclass inherits the attributes and methods from the GameObject class.
 * Is for setting Fireball properties.
 * @author Ryan
 */
public class OBJ_Fireball extends Projectile {

    /**
     * This method is constructor of Fireball class.
     * @param gp GamePanel object.
     */
    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Fireball";
        speed = 5;
        maxLife = 80;
        score = maxLife;
        attack = 1;
        alive = false;
        hitBox = new Rectangle(12,12, 30, 30);
        getImage();

    }

    /**
     * This method is for retrieving image of projectile.
     */
    public void getImage() {
        up1 = setup("/projectile/fireball_up_1");
        up2 = setup("/projectile/fireball_up_2");
        down1 = setup("/projectile/fireball_down_1");
        down2 = setup("/projectile/fireball_down_2");
        right1 = setup("/projectile/fireball_right_1");
        right2 = setup("/projectile/fireball_right_2");
        left1 = setup("/projectile/fireball_left_1");
        left2 = setup("/projectile/fireball_left_2");
    }
}