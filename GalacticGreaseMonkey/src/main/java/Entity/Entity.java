package Entity;

import Game.GamePanel;
import Game.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * This Class is for interacts with the game and responds to player input or other entities
 * @author Ryan
 */
public class Entity {

    protected GamePanel gp;

    // Hitbox and collision
    public int worldX, worldY;
    public Rectangle hitBox = new Rectangle(0,0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public boolean collisionDetected = false;
    public Rectangle attackArea = new Rectangle(0,0,0,0);

    // State
    public boolean invincible = false;
    public int invincibleCounter;
    public boolean onPath = false;
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;

    // Entities
    public int actionLockCounter;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int attack = 2;
    boolean hpBarOn = false;
    int hpBarCounter = 0;
    public Projectile projectile;

    public int speed;
    public int maxLife = 2; // for alien healthbar
    public int score = 0;
    public int type; // 0 = player, 1 = alien

    public void setAction() {}

    /**
     * This method is for checking collision.
     */
    public void checkCollision(){
        collisionDetected = false;
        gp.collisionChecker.checkTile(this);

        if(type == 2){
            boolean playerCollision = gp.collisionChecker.checkPlayer(this);
            if(!playerCollision){
                collisionDetected = false;
            }
        }

        if (!collisionDetected) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }
    }

    public void checkInvincibility(Entity e){
        if (e.invincible){
            e.invincibleCounter++;
            if(e.invincibleCounter > 60) { // Removes invincibility after one second
                e.invincible = false;
                e.invincibleCounter = 0;
            }
        }
    }

    /**
     * This method is for updating action and collision.
     */
    public void update() {
        setAction();

        checkCollision();

        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    /**
     * This method is for setting image.
     * @param imagePath path of image.
     * @return image
     */
    public BufferedImage setup(String imagePath){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * This method is Constructor of Entity class.
     * @param gp GamePanel object.
     */
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    //Sprite animations stored in these variables
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction = "down";

    // Handles objects
    public String name;

    /**
     * This method is for displaying corresponding image based on key press.
     * Makes player transparent when they are invincible.
     * HP Bar.
     * @param g2 Graphics2D object.
     */
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        // HP Bar
        if(type == 1 && hpBarOn) {
            double oneScale = (double)gp.tileSize/maxLife;
            double hpBarValue = oneScale*(score+1);

            g2.setColor(new Color(35,35,35));
            g2.fillRect(worldX-1, worldY - 6, gp.tileSize+2, 12);

            g2.setColor(new Color(255,0,30));
            g2.fillRect(worldX, worldY - 5, (int)hpBarValue, 10);

            hpBarCounter++;

            if(hpBarCounter > 600) {
                hpBarCounter = 0;
                hpBarOn = false;
            }
        }

        if(invincible) { // Makes player transparent when they are invincible
            hpBarOn = true;
            hpBarCounter = 0;
            changeAlpha(g2,0.5f);
        }

        if(dying) {
            dyingAnimation(g2);
        }

        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);

        changeAlpha(g2,1f);
    }

    /**
     * This method is for setting death animation.
     * @param g2 Graphics2D object.
     */
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        if(dyingCounter <= 10) {changeAlpha(g2,0f);}
        if(dyingCounter > 10 && dyingCounter <= 20) {changeAlpha(g2,0f);}
        if(dyingCounter > 20 && dyingCounter <= 30) {changeAlpha(g2,1f);}
        if(dyingCounter > 30 && dyingCounter <= 40) {changeAlpha(g2,0f);}
        if(dyingCounter > 40 && dyingCounter <= 50) {changeAlpha(g2,1f);}
        if(dyingCounter > 50 && dyingCounter <= 60) {changeAlpha(g2,0f);}
        if(dyingCounter > 60 && dyingCounter <= 70) {changeAlpha(g2,1f);}
        if(dyingCounter > 70 && dyingCounter <= 80) {changeAlpha(g2,1f);}
        if(dyingCounter > 80){
            dying = false;
            alive = false;
        }
    }

    /**
     * This method is for changing the amount of transparency in an image.
     * @param g2 Graphics2D object.
     * @param alphaValue specifies the amount of transparency in an image.
     */
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    /**
     * This class is for searching path of AI
     * @param goalCol goal colum.
     * @param goalRow goal row.
     */
    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + hitBox.x)/gp.tileSize;
        int startRow = (worldY + hitBox.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gp.pFinder.search()) {
            // Next worldX & worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // Entity's hitbox position
            int enLeftX  = worldX + hitBox.x;
            int enRightX = worldX + hitBox.x + hitBox.width;
            int enTopY = worldY + hitBox.y;
            int enBottomY = worldY + hitBox.y + hitBox.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            }
            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            }
            else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                // left or right
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "up";
                checkCollision();
                if(collisionDetected) {
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "up";
                checkCollision();
                if(collisionDetected) {
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX) {
                // down or left
                direction = "down";
                checkCollision();
                if(collisionDetected) {
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX) {
                // down or right
                direction = "down";
                checkCollision();
                if(collisionDetected) {
                    direction = "right";
                }
            }

            // If reaches goal, stop
            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow) {
                onPath = false;
            }
        }
    }
}