package Objects;

import Game.GamePanel;
import Game.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is for setting object of game.
 * @author Jason
 */
public class GameObject {

    GamePanel gp;

    /**
     * Object name
     */
    public String name;

    /**
     * Collision status
     */
    public boolean collision = false;

    /**
     * Object location on map
     */
    public int objectX, objectY;

    /**
     * Actual hit box for object
     */
    public Rectangle hitBox = new Rectangle(0,0, 48, 48);

    /**
     * Default hit box for object X and Y
     */
    public int hitBoxDefaultX = 0;
    public int hitBoxDefaultY = 0;

    public int timeSinceCreated = 0;

    /**
     * Default image for all object.
     * Since object are not moving, just need 1 image to represent
     */
    public BufferedImage objectImage;

    /**
     * This method is constructor of GameObject class.
     * @param gp GamePanel object.
     */
    public GameObject (GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Set up image for object
     * @param imagePath path to images resource
     * @return  return object image
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
     * This method is for drawing image.
     * @param g2 Graphics2D object.
     */
    public void draw(Graphics2D g2) {

        BufferedImage image = objectImage;

        g2.drawImage(image, objectX, objectY, gp.tileSize, gp.tileSize, null);
    }
}