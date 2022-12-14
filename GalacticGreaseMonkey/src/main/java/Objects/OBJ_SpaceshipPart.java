package Objects;

import Game.GamePanel;

/**
 * This subclass inherits the attributes and methods from the GameObject class.
 * Is for Fireball Key properties.
 * @author Luan
 */
public class OBJ_SpaceshipPart extends GameObject {

    /**
     * This method is constructor of Diamond class.
     * @param gp GamePanel object.
     */
    public OBJ_SpaceshipPart(GamePanel gp) {
        super(gp);
        name = "Key";
        objectImage = setup("/object/gear");
        collision = false;
    }
}
