package Objects;

import Game.GamePanel;

/**
 * This subclass inherits the attributes and methods from the GameObject class.
 * is for setting Diamond properties.
 * @author Jason
 */
public class OBJ_Diamond extends GameObject {

    /**
     * This method is constructor of Diamond class.
     * @param gp GamePanel object.
     */
    public OBJ_Diamond(GamePanel gp) {
        super(gp);
        name = "Diamond";
        objectImage = setup("/object/diamond");
        collision = false;
    }
}
