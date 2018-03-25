package Models.Threads;

import Game.GameController;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import static Login.Home.LoginController.gameBoard;
import static Login.Home.LoginController.server;

public class PlayerTurnThread extends Thread
{
    private volatile boolean isRunning = true;
    private GameController controller;

    private ObjectInputStream inputStream = null;


    public PlayerTurnThread(String name, GameController controller) {
        super(name);
        this.controller = controller;
    }

    public void run() {
        while (isRunning) {
            if (inputStream == null) {
                try {
                    inputStream = server.GetInputStream();
                    String result = inputStream.readUTF();

                    if (result.equals("You can play now.")) {
                        gameBoard.SetIsYourTurn(true);
                        controller.SetEnnemyShipsGridPaneEnabled(true);
                    }
                    else if (result.equals("The game is finished.")) {
                        controller.SetEnnemyShipsGridPaneEnabled(false);
                        isRunning = false;
                    }
                }
                catch (EOFException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    inputStream = null;
                }
            }
        }
    }
}
