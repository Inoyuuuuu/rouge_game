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

        //TODO: implement a position check
        //so if we try to go out of bounds, our program doesnt crash

        //main loop
        while (!inputHandler.wasEscapePressed()) {

            int nextPosX = inputHandler.getPlayerPositionX();
            int nextPosY = inputHandler.getPlayerPositionY();

            //wall detection
            if (ui.getCharCharOfPosition(nextPosX, nextPosY).character != 'X') {

                playerPositionX = nextPosX;
                playerPositionY = nextPosY;
                playerPreviousPositionX = inputHandler.getPreviousPlayerPositionX();
                playerPreviousPositionY = inputHandler.getPreviousPlayerPositionY();
            }
            
            inputHandler.setPlayerPositionX(playerPositionX);
            inputHandler.setPlayerPositionY(playerPositionY);

            //print player + surroundings in console
            print3by3field(playerPositionX, playerPositionY, ui);

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
    private static void print3by3field(int posX, int posY, UserInterface ui) {

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
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
            System.out.println();
        }
        System.out.println();
    }
}