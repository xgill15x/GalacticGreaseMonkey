package Entity;

import Game.GamePanel;


/**
 * This subclass inherits the attributes and methods from the Entity class
 * is for initializing player properties.
 * @author Ryan
 */
public class Projectile extends Entity{

    Entity user;

    /**
     * This method is constructor of Projectile class.
     * @param gp GamePanel object.
     */
    public Projectile(GamePanel gp) { super(gp); }

    /**
     * This method is for setting properties of projectile.
     * @param worldX x-axis of game.
     * @param worldY y-axis of game.
     * @param direction projectile direction.
     * @param alive available of shooting projectile.
     * @param user user of projectile.
     */
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.score = this.maxLife;

    }

    /**
     * This method is for setting projectile shooting and damage.
     */
    public void update() {

        if (user == gp.player) {
            int alienIndex = gp.collisionChecker.checkEntity(this, gp.alien);
            //gp.collisionChecker.checkTile(this);
            if (alienIndex != 999) {
                gp.player.damageAlien(alienIndex);
                alive = false;
            }
        }

        // TODO: Fix wall collision
//        gp.collisionChecker.checkTile(this);
//        if (collisionDetected){
//            alive = false;
//            collisionDetected = false;
//        }
        switch (direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }

        score--;
        if (score <= 0) {
            alive = false;
        }

        spriteCounter++;
        if(spriteCounter > 12) {
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
}
