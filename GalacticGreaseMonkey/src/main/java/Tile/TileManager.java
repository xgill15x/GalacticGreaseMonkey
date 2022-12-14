package Tile;

import Game.GamePanel;
import Game.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * This class is for managing tile of game.
 * @author Jason
 * @author Ryan
 */
public class TileManager {
    /**
     * Varaibale name
     */
    GamePanel gp;
    /**
     * Tile array
     */
    public Tile [] tile;
    /**
     * Map tile array
     */
    public int[][] mapTileNum;

    /**
     * This method is constructor of TileManager class.
     * @param gp GamePanel object.
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap("maps/map1.txt");
    }

    /**
     * This method is for retrieving image of Tile.
     */
    public void getTileImage() {
        // Collision handling for tiles
        setup(0,"space",false);
        setup(1,"spaceRock",true);
        setup(2,"satellite",true);
        setup(3,"space2",false);
    }

    /**
     * This method is for add tile to array.
     * @param index array position.
     * @param imageName path of tile.
     * @param collision whether collided.
     */
    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTOol = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].image = uTOol.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This class is for setting map of game.
     * @param filePath tile path.
     */
    public void loadMap(String filePath) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int col = 0;
            int row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String getLine = bufferedReader.readLine();

                while (col < gp.maxScreenCol ) {
                    String[] numbers = getLine.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is for drawing map of game.
     * @param g2 Graphics2D object.
     */
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        int x = 0;
        int y = 0;

        while (worldCol < gp.maxScreenCol && worldRow < gp.maxScreenRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            g2.drawImage(tile[tileNum].image, x, y, null);
            worldCol++;
            x += gp.tileSize;

            if (worldCol == gp.maxScreenCol) {
                worldCol = 0;
                x = 0;
                worldRow ++;
                y += gp.tileSize;
            }
        }
    }
}