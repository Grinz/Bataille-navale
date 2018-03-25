package Models.Threads;

import Game.GameController;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.io.ObjectInputStream;

import static Login.Home.LoginController.gameBoard;
import static Login.Home.LoginController.server;

public class DataReceptionThread extends Thread
{
    private volatile boolean isRunning = true;
    private GameController controller;

    private PlayerTurnThread playerTurnThread;
    private String[][] ennemyShipsButtonsArray = new String[10][10];
    private GridPane ennemyShipsGridpane;

    private ObjectInputStream inputStream = null;

    public DataReceptionThread(String name, GameController controller) {
        super(name);
        this.controller = controller;
    }

    public void run() {
        while (isRunning) {
            if (inputStream == null) {
                System.out.println("Data reception thread started.");
                try {
                    inputStream = server.GetInputStream();
                    ennemyShipsButtonsArray = (String[][]) inputStream.readObject();
                    gameBoard.SetEnnemyShipsButtons(ennemyShipsButtonsArray);
                    ennemyShipsGridpane = controller.GetEnnemyShipsGridPane();
                    System.out.println("Buttons received !");

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i=0; i<10; i++) {
                                    for (int j=0; j<10; j++) {
                                        if (ennemyShipsButtonsArray[i][j] != null) {
                                            Button currentButton = (Button)controller.getNodeFromGridPane(ennemyShipsGridpane, j, i);
                                            currentButton.setText(ennemyShipsButtonsArray[i][j]);
                                            currentButton.setId(ennemyShipsButtonsArray[i][j]);
                                        }
                                    }
                                }

                                if (gameBoard.GetIsYourTurn())
                                    ennemyShipsGridpane.setDisable(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                finally {
                    inputStream = null;
                    isRunning = false;

                    playerTurnThread = new PlayerTurnThread("Player turn thread", controller);
                    playerTurnThread.start();
                }
            }
        }
    }
}
