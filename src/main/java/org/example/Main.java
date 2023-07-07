package org.example;

import org.example.model.Rouge;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        char playerSymbol = '@';
        int playerPositionX = 10;
        int playerPositionY = 10;


        UserInterface ui = new UserInterface(110, 40);
        InputHandler inputHandler = new InputHandler(ui, playerPositionX, playerPositionY);

        while (true) {

            inputHandler.getGatherKeystrokes().take();

            int nextPosX = inputHandler.getPlayerPositionX();
            int nextPosY = inputHandler.getPlayerPositionY();

            if (ui.getCharCharOfPosition(nextPosX, nextPosY).character != 'X') {
                playerPositionX = nextPosX;
                playerPositionY = nextPosY;
            }
            
            inputHandler.setPlayerPositionX(playerPositionX);
            inputHandler.setPlayerPositionY(playerPositionY);

            ui.draw(playerSymbol, playerPositionX, playerPositionY,
                    inputHandler.getPreviousPlayerPositionX(), inputHandler.getPreviousPlayerPositionY());
        }
    }
}