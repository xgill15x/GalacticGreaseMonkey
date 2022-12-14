package Game;
import Objects.*;
import Entity.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is for setting location of object.
 * @author Jason
 * @author Ryan
 */

class Coordinates {
    public int xVal;
    public int yVal;

    public Coordinates(int xVal, int yVal) {
        this.xVal = xVal;
        this.yVal = yVal;
    }
}

public class AssetSetter {

    GamePanel gp;

    /**
     * This method is constructor of AssetSetter.
     * @param gp GamePanel object.
     */
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    private Coordinates getRandomCoords(int origin, int bound) {
        int randomIdx = ThreadLocalRandom.current().nextInt(origin, bound);
        int randomX = Integer.parseInt(gp.listOfValidCoords.toArray()[randomIdx].toString().split("=")[0]);
        int randomY = Integer.parseInt(gp.listOfValidCoords.toArray()[randomIdx].toString().split("=")[1]);
        Coordinates randomCoords = new Coordinates(randomX, randomY);

        return randomCoords;
    }

    /**
     * This method is for making a key object and save into obj array,
     * and Set location for object on map.
     */
    public void setSpaceshipPart() {

        int numShipPart = gp.userInterface.commandLevel * 1 + 2;

        for (int i = 0; i < numShipPart; i++) {
            gp.spaceshipPart[i] = new OBJ_SpaceshipPart(gp);

            Coordinates randomCoords = getRandomCoords(0, 258);
            gp.spaceshipPart[i].objectX = randomCoords.xVal * gp.tileSize;
            gp.spaceshipPart[i].objectY = randomCoords.yVal * gp.tileSize;
        }
    }

    /**
     * This method is for making a Diamond object and save into obj array,
     * and Set location for object on map.
     */
    public void setDiamond() {

        gp.diamond.add(new OBJ_Diamond(gp));
        gp.diamond.get(gp.diamond.size()-1).objectX = 20 * gp.tileSize;
        gp.diamond.get(gp.diamond.size()-1).objectY = 4 * gp.tileSize;

        gp.diamond.add(new OBJ_Diamond(gp));
        gp.diamond.get(gp.diamond.size()-1).objectX = 7 * gp.tileSize;
        gp.diamond.get(gp.diamond.size()-1).objectY = 5 * gp.tileSize;

    }

    /**
     * This method is for making Blackhole object and save into obj array,
     * and Set location for object on map.
     */
    public void setBlackhole() {

        int numBlackholes = ThreadLocalRandom.current().nextInt(2,7);
        if (gp.userInterface.commandLevel == 0) {
            numBlackholes = 3;
        } else if (gp.userInterface.commandLevel == 1) {
            numBlackholes = 5;
        } else {
            numBlackholes = 7;
        }

        for (int i = 0; i < numBlackholes; i++) {
            gp.blackhole[i] = new OBJ_Blackhole(gp);

            Coordinates randomCoords = getRandomCoords(0, 258);
            gp.blackhole[i].objectX = randomCoords.xVal * gp.tileSize;
            gp.blackhole[i].objectY = randomCoords.yVal * gp.tileSize;
        }
    }

    /**
     * This method is for making ClosedDoor object and save into obj array,
     * and Set location for object on map.
     */
    public void setDoor() {
        gp.closedDoor[0] = new OBJ_ClosedDoor(gp);
        gp.openedDoor[0] = new OBJ_OpenedDoor(gp);

        Coordinates randomCoords = getRandomCoords(0, 258);
        gp.openedDoor[0].objectX = randomCoords.xVal * gp.tileSize;
        gp.openedDoor[0].objectY = randomCoords.yVal * gp.tileSize;
        gp.closedDoor[0].objectX = randomCoords.xVal * gp.tileSize;
        gp.closedDoor[0].objectY = randomCoords.yVal * gp.tileSize;
    }

    /**
     * This method is for making Alien object and save into obj array,
     * and Set location for object on map.
     */
    public void setAlien() {
        gp.alien[0] = new Alien(gp); // Make a key object and save into obj array
        gp.alien[0].worldX = 20 * gp.tileSize; // Set location for obj on map
        gp.alien[0].worldY = 14 * gp.tileSize;

        gp.alien[1] = new Alien(gp);
        gp.alien[1].worldX = 12 * gp.tileSize;
        gp.alien[1].worldY = 13 * gp.tileSize;

        gp.alien[2] = new Alien(gp);
        gp.alien[2].worldX = 11 * gp.tileSize;
        gp.alien[2].worldY = 6 * gp.tileSize;

        gp.alien[3] = new Alien(gp);
        gp.alien[3].worldX = 21 * gp.tileSize;
        gp.alien[3].worldY = gp.tileSize;
    }

    /**
     * This method is for making new Alien object and save into obj array,
     * Set location for object on map.
     * @param index array position.
     */
    public void newAlien(int index) {
        gp.alien[index] = new Alien(gp); // Make a key object and save into obj array
        gp.alien[index].worldX = 30 * gp.tileSize; // Set location for obj on map
        gp.alien[index].worldY = 14 * gp.tileSize;
        gp.alien[index].direction = "left";
    }

    /**
     * This method is for making new Alien object and save into obj array,
     * Set location for object on map.
     * @param index array position.
     */
    public void newAlien2(int index) {
        gp.alien[index] = new Alien(gp); // Make a key object and save into obj array
        gp.alien[index].worldX = 20 * gp.tileSize; // Set location for obj on map
        gp.alien[index].worldY = 14 * gp.tileSize;
        gp.alien[index].direction = "left";
    }

    /**
     * This method is for making new Alien object and save into obj array,
     * Set location for object on map.
     * @param index array position.
     */
    public void newAlien3(int index) {
        gp.alien[index] = new Alien(gp); // Make a key object and save into obj array
        gp.alien[index].worldX = gp.tileSize; // Set location for obj on map
        gp.alien[index].worldY = 14 * gp.tileSize;
        gp.alien[index].direction = "left";
    }

    /**
     * This method is for making new Alien object and save into obj array,
     * Set location for object on map.
     * @param index array position.
     */
    public void newAlien4(int index) {
        gp.alien[index] = new Alien(gp); // Make a key object and save into obj array
        gp.alien[index].worldX = 30 * gp.tileSize; // Set location for obj on map
        gp.alien[index].worldY = gp.tileSize;
        gp.alien[index].direction = "left";
    }



}
