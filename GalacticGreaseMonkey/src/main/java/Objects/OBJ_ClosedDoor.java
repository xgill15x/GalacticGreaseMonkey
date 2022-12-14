package Objects;

import Game.GamePanel;

/**
 * This subclass inherits the attributes and methods from the GameObject class
 * is for setting ClosedDoor properties
 * @author Luan
 */
public class OBJ_ClosedDoor extends GameObject {

    /**
     * This method is constructor of ClosedDoor class.
     * @param gp GamePanel object.
     */
    public OBJ_ClosedDoor(GamePanel gp) {
        super(gp);
        name = "Closed Door";
        objectImage = setup("/WinChest/door");
        collision = false;
    }
}
