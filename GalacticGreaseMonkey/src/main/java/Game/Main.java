package Game;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args)  //static method
    {
        //System.out.println("HelloWorld");
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Galactic Grease Monkey");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();              // Set object in game
        gamePanel.startGameThread();
    }
}
