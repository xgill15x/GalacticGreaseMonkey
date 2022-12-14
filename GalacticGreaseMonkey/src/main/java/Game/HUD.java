package Game;

import java.awt.*;

/**
 * This class is for setting HUD of game.
 * @author Zihao
 * @author Ryan
 */
public class HUD {
    GamePanel gp;
    Font monospaced_40;

    /**
     * This method is constructor of HUD class.
     * @param gp GamePanel object.
     */
    public HUD(GamePanel gp){
        this.gp = gp;

        monospaced_40 = new Font("Monospaced", Font.PLAIN, 40);
    }

    /**
     * This class is for setting HUD properties.
     * @param g2 Graphics2D objects.
     */
    public void draw(Graphics2D g2){
        g2.setFont(monospaced_40);

        // Display Score
        g2.setColor(Color.BLACK);
        g2.drawString("Score: " + gp.player.score, 23, 38);
        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + gp.player.score, 20, 35);

        // Parts collected
        g2.setColor(Color.BLACK);
        g2.drawString("Parts: " + gp.player.partsCollected, 300, 38);
        g2.setColor(Color.WHITE);
        g2.drawString("Parts: " + gp.player.partsCollected, 300, 35);

    }
}
