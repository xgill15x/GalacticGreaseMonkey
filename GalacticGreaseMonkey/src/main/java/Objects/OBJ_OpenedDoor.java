package Objects;

import Game.GamePanel;

/**
 * This subclass inherits the attributes and methods from the GameObject class
 * is for setting OpenedDoor properties
 * @author Luan
 */
public class OBJ_OpenedDoor extends GameObject{

    /**
     * This method is constructor of OpenedDoor class.
     * @param gp GamePanel object.
     */
    public OBJ_OpenedDoor(GamePanel gp) {
        super(gp);
        name = "Opened Door";
        objectImage = setup("/WinChest/door_open");
        collision = false;
    }

}
