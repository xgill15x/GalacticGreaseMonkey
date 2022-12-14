package Entity;

import Game.GamePanel;

import java.util.Random;

/**
 * This subclass inherits the attributes and methods from the Entity class
 * is for implementing Aliens who tend to attack player.
 * @author Ryan
 */
public class Alien extends Entity {
    boolean aggressive = false;

    /**
     * This method is constructor of Alien class.
     * @param gp GamePanel object
     */
    public Alien(GamePanel gp) {
        super(gp);
        score = 1;
        speed = 2;
        type = 1;

        hitBox.x = 3;
        hitBox.y = 18;
        hitBox.width = 42;
        hitBox.height = 30;
        solidAreaDefaultX = hitBox.x;
        solidAreaDefaultY = hitBox.y;
        getImage();

    }

    /**
     * This method is for retrieving Alien image that can then be painted on the screen.
     */
    public void getImage() {
        if (!onPath) {
            up1 = setup("/monster/greenslime_down_1");
            up2 = setup("/monster/greenslime_down_2");
            down1 = setup("/monster/greenslime_down_1");
            down2 = setup("/monster/greenslime_down_2");
            right1 = setup("/monster/greenslime_down_1");
            right2 = setup("/monster/greenslime_down_2");
            left1 = setup("/monster/greenslime_down_1");
            left2 = setup("/monster/greenslime_down_2");
        } else {
            up1 = setup("/monster/redslime_down_1");
            up2 = setup("/monster/redslime_down_2");
            down1 = setup("/monster/redslime_down_1");
            down2 = setup("/monster/redslime_down_2");
            right1 = setup("/monster/redslime_down_1");
            right2 = setup("/monster/redslime_down_2");
            left1 = setup("/monster/redslime_down_1");
            left2 = setup("/monster/redslime_down_2");
        }
    }

    /**
     *This method is for updating Aliens.
     */
    public void update() {
        super.update();
        if(invincible){
            speed = 1;
        }
        else{
            speed = 2;
        }

        boolean collidedPlayer = gp.collisionChecker.checkPlayer(this);
        if (collidedPlayer) {
            if (!gp.player.invincible) {
                gp.player.score = -1;
                gp.playSE(2);
            }
        }

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (!onPath && tileDistance < 5) {
            onPath = true;
        }

        checkInvincibility(this);
    }

    /**
     * This method is for setting action of Aliens.
     */
    public void setAction() {

        if (onPath) {
            if (!aggressive) {
                aggressive = true;
                getImage();
            }
            int goalCol = (gp.player.worldX + gp.player.hitBox.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.hitBox.y) / gp.tileSize;

            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // pick a number from 1-100

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75) {
                    direction = "right";
                }

                actionLockCounter = 0;
            }
        }
    }
}
