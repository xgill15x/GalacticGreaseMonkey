package Entity;

import Game.GamePanel;
import Game.KeyHandler;
import Game.UtilityTool;

import Objects.OBJ_Fireball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * This subclass inherits the attributes and methods from the Entity class
 * is for initializing player properties.
 * @author Ryan
 * @author Jason
 */
public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    public int partsCollected = 0;
    public int projectileCounter = 120;

    /**
     * This method is constructor of Player class.
     * @param gp GamePanel object.
     * @param keyH KeyHandler object.
     */
    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        speed = 5;
        direction = "down";
        hitBox = new Rectangle(8,12, 26, 26);
        this.gp = gp;
        this.keyH = keyH;
        worldX = 48;    //player starting position
        worldY = 48;
        solidAreaDefaultX = hitBox.x;   //player hitboxes
        solidAreaDefaultY = hitBox.y;

        attackArea.width = 36;
        attackArea.height = 36;
        projectile = new OBJ_Fireball(gp);

        getPlayerImage();
    }

    /**
     * This method is for retrieving player image that can then be painted on the screen.
     */
    public void getPlayerImage() {
        up1 = setup("monkeyUp1");
        up2 = setup("monkeyUp2");
        down1 = setup("monkeyDown1");
        down2 = setup("monkeyDown2");
        right1 = setup("monkeyRight1");
        right2 = setup("monkeyRight2");
        left1 = setup("monkeyLeft1");
        left2 = setup("monkeyLeft2");
    }

    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/" + imageName + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * This method is for updating player position on key press and animation every 10ms.
     */
    public void update() {
        // Handle WASD movement

        /*
            checking to see if player has made a move
         */
        if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
            if(keyH.upPressed) {
                direction = "up";
            }

            else if(keyH.downPressed) {
                direction = "down";
            }

            else if(keyH.leftPressed) {
                direction = "left";
            }

            else {
                direction = "right";
            }

            // Check tile collision
            collisionDetected = false;
            gp.collisionChecker.checkTile(this);

            // Check spaceship part collision
            int spaceshipPartIndex = gp.collisionChecker.checkSpaceshipPart(this, true);
            pickUpSpaceshipPart(spaceshipPartIndex);

            // Check diamond collision
            int diamondIndex = gp.collisionChecker.checkDiamond(this, true);
            pickUpDiamond(diamondIndex);

            // Check blackhole collision
            int blackholeIndex = gp.collisionChecker.checkBlackhole(this, true);
            collideBlackhole(blackholeIndex);

            //Check Door collision
            int doorIndex = gp.collisionChecker.checkWinningDoor(this, true);
            collideOpenedDoor(doorIndex);

            // If false collision, player can move
            if (!collisionDetected) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            /*
                Sprite counter is incremented iterations of this method.
                Once it reaches 10, the players sprite is changed to a another mimicking movement
             */
            spriteCounter++;
            if (spriteCounter > 10) {
                /*
                    Only 2 sprites for each directions for player
                    , which is why it only switches between 1 & 2 as inputs
                 */
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }

                // reseting sprite counter so it can continiously switch sprites after reaching 10
                spriteCounter = 0;
            }
        }

        /*
            The if block states that you can only use projectiles when there is not an active projectile on the map
            , the cooldown of 90 updates has been met, and the player has actually pressed the space button
         */
        if(gp.keyH.spacePressed && !projectile.alive && projectileCounter > 90) {
            projectileCounter = 0;
            // Set default coordinates, direction, and user
            projectile.set(worldX, worldY, direction, true, this);

            // Add it to the list
            gp.projectileList.add(projectile);

            gp.playSE(7);

        }
        projectileCounter++;

        // Cooldown for player getting hit
        checkInvincibility(this);
    }

    /**
     * This method is for collecting Spaceship.
     * @param index If index is 999, character haven't collided with any object.
     */
    public void pickUpSpaceshipPart(int index) {

        // If index is 999, character haven't collided with any object
        if (index != 999) {
            /*
                If the player collides with a spaceship parts (i.e. gear), then add 300 points to their score,
                increment the variable that keeps count of the number of parts collected (partsCollected)
             */
            gp.playSE(4);
            score += 300;
            partsCollected++;
            gp.spaceshipPart[index] = null;

            //If the player has collected all parts present on the map, show an opened door
            if (partsCollected == gp.userInterface.commandLevel * 1 + 2) {
                gp.closedDoor[0] = null;
            }
        }
    }

    /**
     * This method is for collecting Diamond.
     * @param index If index is 999, character haven't collided with any object.
     */
    public void pickUpDiamond(int index) {

        // If index is 999, character haven't collided with any object
        /*
            If players collides with diamond, add 500 points from it and remove the diamond from the map
         */
        if (index != 999) {
            gp.playSE(1);
            score += 500;
            gp.diamond.remove(index);
        }
    }

    /**
     * This method is for updating scores when player collided with Blackhole.
     * @param index If index is 999, character haven't collided with any object.
     */
    public void collideBlackhole(int index) {
        /*
            checking to see if a collision between player and a blackhole has occured
        */
        if (index != 999) {
            /*
                If collision has occured, make player temporarily invincible and remove the blackhole from the map
             */
            if (!invincible){
                invincible = true;
            }
            gp.playSE(6);
            score -= 300;
            gp.blackhole[index] = null;
        }
    }

    /**
     * This method is for setting reaction, when player collided with OpenedDoor.
     * @param index If index is 999, character haven't collided with any object.
     */
    public void collideOpenedDoor(int index) {

        /*
            No collision detected between player and another object
         */
        if (index != 999) {
            if (partsCollected == gp.userInterface.commandLevel * 1 + 2) {
                gp.currentGameState = gp.winState;
                gp.playSE(8);

                int currentTopScore = -1;

                /*
                    Based on the "command" level, chose the correct topScore file to write to
                 */
                String fileToBeWrittenTo = "topScore.txt";
                if (gp.userInterface.commandLevel == 0) {
                    fileToBeWrittenTo = "topScore.txt";
                }
                else if (gp.userInterface.commandLevel == 1) {
                    fileToBeWrittenTo = "topScoreMed.txt";
                }
                else if (gp.userInterface.commandLevel == 2) {
                    fileToBeWrittenTo = "topScoreHard.txt";
                }

                /*
                    Retrieve the current top score
                    Since there are many, you have to specify the "command" level
                    to target the correct file
                 */
                try (BufferedReader buffer = new BufferedReader(new FileReader(fileToBeWrittenTo))) {
                    String temp = buffer.readLine();
                    currentTopScore = Integer.parseInt(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*
                    If the players score is greater than the current top score for that difficulty
                    , overwrite the current top score with the players score
                 */
                if (score > currentTopScore) {
                    currentTopScore = score;
                }


                /*
                    Write the new topScore to the corresponding topScore file (based on command level)
                 */
                try {
                    PrintWriter writer = new PrintWriter(fileToBeWrittenTo, StandardCharsets.UTF_8);
                    writer.println(currentTopScore);
                    writer.close();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This method is for setting damage, when player collided with Alien.
     * @param i If index is 999, character haven't collided with any object.
     */
    public void damageAlien(int i) {
        /*
            If i is 999, no collisiion has occured
        */
        if (i != 999) {
            /*
                checking to see if alien is temporarily invincible (which only happens when the alien has been recently damaged)
             */
            if(!gp.alien[i].invincible) {
                /*
                    if alien is in vulnerable state, tell it to remove half its life and make it temporarily invincible
                 */
                gp.playSE(5);
                gp.alien[i].score -= 1;
                gp.alien[i].invincible = true;

                if(gp.alien[i].score < 0) {
                    gp.alien[i].dying = true;
                }
            }
        }
    }

    public void playerReset() {
        /*
            Reset player attributes and behaviour
            This is called whenever the gameloop terminates or needs to restart
         */
        worldX = 48;
        worldY = 48;
        direction = "down";
        partsCollected = 0;
        score = 0;
    }

    //Display corresponding image based on key press
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

        if(invincible) { // Makes player transparent when they are invincible
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);

        g2.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)));
    }

}
