package org.example;

/**
 * Main class of the program
 * @author ukuny
 *
 */
public class Main {

    /**
     * the entry point to the rouge program
     *
     * @param args not used
     * @throws InterruptedException throw because of BlockingQueue.take()
     */
    public static void main(String[] args) throws InterruptedException {

        char playerSymbol = '@';
        int playerPositionX = 10;
        int playerPositionY = 10;
        int playerPreviousPositionX = 10;
        int playerPreviousPositionY = 10;

        UserInterface ui = new UserInterface(110, 40);
        InputHandler inputHandler = new InputHandler(ui, playerPositionX, playerPositionY);

        //TODO: implement draw a rectangle
        //drawRectangle();

        ui.drawBorder('O');
        ui.drawRectangle('X', 20,20, 10, 10, false);

        //main loop
        while (!inputHandler.wasEscapePressed()) {

            int nextPosX = inputHandler.getPlayerPositionX();
            int nextPosY = inputHandler.getPlayerPositionY();

            //wall detection
            if (!ui.isOutOfBounds(nextPosX, nextPosY) && ui.getCharCharOfPosition(nextPosX, nextPosY).character != '#') {

                playerPositionX = nextPosX;
                playerPositionY = nextPosY;
                playerPreviousPositionX = inputHandler.getPreviousPlayerPositionX();
                playerPreviousPositionY = inputHandler.getPreviousPlayerPositionY();
            }
            
            inputHandler.setPlayerPositionX(playerPositionX);
            inputHandler.setPlayerPositionY(playerPositionY);

            //print player + surroundings in console
            print3x3field(playerPositionX, playerPositionY, ui);

            //draw player
            ui.drawPlayer(playerSymbol, playerPositionX, playerPositionY,
                    playerPreviousPositionX, playerPreviousPositionY);

            inputHandler.getGatherKeystrokes().take();
        }

        //after while loop, close window
        ui.setVisible(false);
        ui.dispose();
    }

    /**
     * this method gets a position and prints all the chars next to it
     *
     * @param posX position X
     * @param posY position Y
     * @param ui the UserInterface with the ascii-panel the characters are on
     */
    private static void print3x3field(int posX, int posY, UserInterface ui) {

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {

                if (ui.isOutOfBounds(posX + i - 1, posY + j - 1)) {
                    System.out.println("meh");
                } else {
                    char currentChar = ui.getCharCharOfPosition(posX + i - 1, posY + j - 1).character;

                    if (i == 1 && j == 1) {
                        System.out.print("@ ");
                    } else {
                        if (Character.isWhitespace(currentChar) || currentChar == '@') {
                            System.out.print(". ");
                        } else {
                            System.out.print(ui.getCharCharOfPosition(posX + i - 1, posY + j - 1).character
                                    + " ");
                        }
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}