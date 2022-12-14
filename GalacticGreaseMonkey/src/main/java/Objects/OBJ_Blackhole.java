package Objects;

import Game.GamePanel;
/**
 * This subclass inherits the attributes and methods from the GameObject class.
 * Is for setting Blackhole properties.
 * @author Ryan
 */
public class OBJ_Blackhole extends GameObject {

    /**
     * This method is constructor of Blackhole class.
     * @param gp GamePanel object.
     */
    public OBJ_Blackhole(GamePanel gp) {
        super(gp);

        name = "Blackhole";
        objectImage = setup("/object/blackhole");
        collision = false;
    }
}
