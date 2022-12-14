package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class is for setting game keys.
 * @author Jason
 * @author Ryan
 */
public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;

    /**
     * This method is constructor of KeyHandler class.
     * @param gp GamePanel object
     */
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        /*
            Game always starts on the title screen when first loaded
         */
        if(gp.currentGameState == gp.titleState) {
            /*
                First two if blocks following this comment are there for option selection
             */

            /*
                If the "Exit" option is selected and player presses up,
                then put the cursor on the "New game" option
             */
            if (code == KeyEvent.VK_W && gp.userInterface.commandNumTitleScreen == 1) {
                gp.userInterface.commandNumTitleScreen--;
                gp.playSE(0);
            }

            /*
                If the "New Game" option is selected and player pressed down,
                then put the cursor on the "Exit" option
             */
            if (code == KeyEvent.VK_S && gp.userInterface.commandNumTitleScreen == 0) {
                gp.userInterface.commandNumTitleScreen++;
                gp.playSE(0);
            }

            /*
                The next 3 if blocks are there to set the difficulty level via the title screen

             */
            if (code == KeyEvent.VK_1) {
                /*
                    If user selects "1" then set the game to the easiest mode
                 */
                gp.userInterface.commandLevel = 0;
            }
            if (code == KeyEvent.VK_2) {
                // If user selects "2" then set the game to the medium-difficulty mode
                gp.userInterface.commandLevel = 1;
            }
            if (code == KeyEvent.VK_3) {
                // If user selects "3" then set the game to the hardest mode
                gp.userInterface.commandLevel = 2;
            }

            /*
                If the user presses enter on the title screen, then they are content with their option choices
             */
            if (code == KeyEvent.VK_ENTER) {
                if (gp.userInterface.commandNumTitleScreen == 0) {
                    gp.playSE(9);
                    gp.setupGame();
                    gp.currentGameState = gp.playingState;
                } else {
                    gp.playSE(10);
                    System.exit(0);

                }
            }
        }


        //retry when in lost state
        /*
            No matter the state, if the game loop ends, you can press enter to start it up again
         */
        if(gp.currentGameState == gp.loseState || gp.currentGameState == gp.winState) {
            if (code == KeyEvent.VK_ENTER) {
                if (gp.currentGameState == gp.loseState) {
                    gp.setupGame();
                    gp.currentGameState = gp.playingState;
                    gp.userInterface.commandNumTitleScreen = 0;
                    gp.userInterface.commandNumPauseScreen = 0;
                } else {
                    gp.setupGame();

                }
            }
        }

        /*
            General key bindings
         */
        switch (code) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_SPACE -> spacePressed = true;
        }

        //Pause State
        if(code == KeyEvent.VK_P || code == KeyEvent.VK_ESCAPE) {
            if (gp.currentGameState == gp.playingState) {
                gp.currentGameState = gp.pausedState;
            }
            else if (gp.currentGameState == gp.pausedState){
                gp.currentGameState = gp.playingState;
            }
        }

        if (gp.currentGameState == gp.pausedState) {
            if (code == KeyEvent.VK_W && gp.userInterface.commandNumPauseScreen > 0) {
                gp.userInterface.commandNumPauseScreen--;
            }
            if (code == KeyEvent.VK_S && gp.userInterface.commandNumPauseScreen < 2) {
                gp.userInterface.commandNumPauseScreen++;
            }

            if (code == KeyEvent.VK_ENTER) {
                switch (gp.userInterface.commandNumPauseScreen) {
                    case 0 -> {
                        gp.currentGameState = gp.playingState;
                    }
                    case 1 -> {
                        gp.setupGame();
                        gp.currentGameState = gp.playingState;
                    }
                    case 2 -> System.exit(0);
                }
                gp.userInterface.commandNumTitleScreen = 0;
                gp.userInterface.commandNumPauseScreen = 0;
            }
        }
    }

    /*
        General bindings for keys (when they are released)
     */
    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_SPACE -> spacePressed = false;
        }
    }
}
