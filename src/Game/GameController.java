package Game;

import Models.Ships.Ship;
import Models.Threads.DataReceptionThread;
import Models.Threads.PlayerTurnThread;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.*;
import java.util.List;

import static Login.Home.LoginController.*;

public class GameController implements Initializable
{
    @FXML
    private ComboBox shipsComboBox;
    @FXML
    private Label selectPositionLabel;
    @FXML
    private GridPane playerShipsGridpane;
    @FXML
    private GridPane ennemyShipsGridpane;
    @FXML
    private RadioButton verticalPositionRadioButton;
    @FXML
    private RadioButton horizontalPositionRadioButton;
    @FXML
    private Label totalPlayerShipsLifeLabel;
    @FXML
    private Label totalEnnemyShipsLifeLabel;

    private List<Ship> playerGameShips;
    private List<Ship> AIGameShips;

    private String[][] playerShipsButtonsArray = new String[10][10];
    private String[][] ennemyShipsButtonsArray = new String[10][10];

    private DataReceptionThread dataReceptionThread;
    private PlayerTurnThread playerTurnThread;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerGameShips = gameBoard.GetPlayerGameShips();
        AIGameShips = gameBoard.GetEnnemyGameShips();

        shipsComboBox.setItems(FXCollections.observableArrayList("Aircraft Carrier", "Counter Torpedo", "Cruiser", "Submarine", "Torpedo"));
        shipsComboBox.getSelectionModel().selectFirst();

        ToggleGroup toggleGroup = new ToggleGroup();
        verticalPositionRadioButton.setToggleGroup(toggleGroup);
        horizontalPositionRadioButton.setToggleGroup(toggleGroup);

        totalPlayerShipsLifeLabel.setText(gameBoard.GetPlayerShipsTotalLife() + "/" + gameBoard.GetPlayerShipsTotalLife());
        totalEnnemyShipsLifeLabel.setText(gameBoard.GetEnnemyShipsTotalLife() + "/" + gameBoard.GetEnnemyShipsTotalLife());
        totalPlayerShipsLifeLabel.setTextFill(Color.SPRINGGREEN);
        totalEnnemyShipsLifeLabel.setTextFill(Color.SPRINGGREEN);

        for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
                Button playerButton = new Button();
                playerButton.setMaxSize(40, 40);

                playerButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int column = GridPane.getColumnIndex(playerButton);
                        int row = GridPane.getRowIndex(playerButton);
                        selectPositionLabel.setVisible(false);

                        int indexNumber = 0;
                        String buttonText = "";

                        if (!shipsComboBox.getItems().isEmpty()) {
                            switch (shipsComboBox.getSelectionModel().getSelectedItem().toString()) {
                                case "Aircraft Carrier":
                                    indexNumber = 0;
                                    buttonText = "AC";
                                    break;
                                case "Counter Torpedo":
                                    indexNumber = 1;
                                    buttonText = "CT";
                                    break;
                                case "Cruiser":
                                    indexNumber = 2;
                                    buttonText = "C";
                                    break;
                                case "Submarine":
                                    indexNumber = 3;
                                    buttonText = "S";
                                    break;
                                case "Torpedo":
                                    indexNumber = 4;
                                    buttonText = "T";
                                    break;
                                default:
                                    break;
                            }

                            try {
                                boolean isOccupiedVertical = true;
                                boolean isOccupiedHorizontal = true;

                                if (verticalPositionRadioButton.isSelected() || horizontalPositionRadioButton.isSelected()) {
                                    if (verticalPositionRadioButton.isSelected()) {
                                        isOccupiedVertical = isOccupiedByOtherShip(playerShipsGridpane, GridPane.getRowIndex(playerButton), ((GridPane.getRowIndex(playerButton)+1)-(playerGameShips.get(indexNumber).GetWidth())), column, GridPane.getRowIndex(playerButton)>=(playerGameShips.get(indexNumber).GetWidth()-1), false,"vertical");
                                        if (!isOccupiedVertical) {
                                            for (int k=GridPane.getRowIndex(playerButton); k>=((GridPane.getRowIndex(playerButton)+1)-(playerGameShips.get(indexNumber).GetWidth())); k--) {
                                                Button currentButton = (Button)getNodeFromGridPane(playerShipsGridpane, column, k);
                                                currentButton.setId(buttonText);
                                                currentButton.setText(buttonText);
                                                playerShipsButtonsArray[k][column] = buttonText;
                                            }
                                        }
                                    }
                                    else if (horizontalPositionRadioButton.isSelected()) {
                                        isOccupiedHorizontal = isOccupiedByOtherShip(playerShipsGridpane, GridPane.getColumnIndex(playerButton), ((GridPane.getColumnIndex(playerButton))+(playerGameShips.get(indexNumber).GetWidth()-1)), row, false,GridPane.getColumnIndex(playerButton)<=(10-playerGameShips.get(indexNumber).GetWidth()),"horizontal");
                                        if (!isOccupiedHorizontal) {
                                            for (int k=GridPane.getColumnIndex(playerButton); k<=(GridPane.getColumnIndex(playerButton)+(playerGameShips.get(indexNumber).GetWidth()-1)); k++) {
                                                Button currentButton = (Button)getNodeFromGridPane(playerShipsGridpane, k, row);
                                                currentButton.setId(buttonText);
                                                currentButton.setText(buttonText);
                                                playerShipsButtonsArray[row][k] = buttonText;
                                            }
                                        }
                                    }

                                    if (!isOccupiedVertical || !isOccupiedHorizontal) {
                                        if (!isOccupiedVertical)
                                            playerGameShips.get(indexNumber).SetDirection("Vertical");
                                        else
                                            playerGameShips.get(indexNumber).SetDirection("Horizontal");

                                        playerGameShips.get(indexNumber).SetYPosition(GridPane.getRowIndex(playerButton));
                                        playerGameShips.get(indexNumber).SetXPosition(GridPane.getColumnIndex(playerButton));

                                        shipsComboBox.getItems().remove(shipsComboBox.getSelectionModel().getSelectedItem().toString());
                                        shipsComboBox.getSelectionModel().selectFirst();

                                        if (shipsComboBox.getItems().isEmpty()) {
                                            shipsComboBox.setDisable(true);

                                            if (IsAIMode)
                                                initializeEnnemyShips();
                                            else if (IsLANMode) {
                                                gameBoard.SetPlayerShipsButtons(playerShipsButtonsArray);

                                                try {
                                                    outputStream = server.GetOutputStream();
                                                    outputStream.writeObject(gameBoard.GetPlayerShipsButtons());
                                                    outputStream.flush();
                                                    System.out.println("Buttons sent !");

                                                    dataReceptionThread = new DataReceptionThread("Data reception thread", GameController.this);
                                                    dataReceptionThread.start();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        selectPositionLabel.setText("Plus de place à partir de la position sélectionnée !");
                                        selectPositionLabel.setVisible(true);
                                    }
                                }
                            }
                            catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                });

                playerShipsGridpane.add(playerButton, i, j);
                playerButton.toBack();
                playerShipsButtonsArray[j][i] = playerButton.getText();
            }
        }

        for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
                Button ennemyButton = new Button();
                ennemyButton.setMaxSize(40, 40);

                ennemyButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int column = GridPane.getColumnIndex(ennemyButton);
                        int row = GridPane.getRowIndex(ennemyButton);

                        if ((!ennemyButton.getText().isEmpty()) && (gameBoard.GetEnnemyShipsTotalLife()>=1)) {
                            gameBoard.ShotEnnemyShip(ennemyButton.getId(), row, column);

                            ennemyButton.setText(ennemyButton.getId());
                            ennemyButton.setDisable(true);

                            int currentLife = gameBoard.GetEnnemyShipsTotalLife();
                            totalEnnemyShipsLifeLabel.setText(String.valueOf(currentLife) + "/17");

                            switch (currentLife) {
                                case 12:
                                    totalEnnemyShipsLifeLabel.setTextFill(Color.YELLOWGREEN);
                                    break;
                                case 8:
                                    totalEnnemyShipsLifeLabel.setTextFill(Color.ORANGE);
                                    break;
                                case 3:
                                    totalEnnemyShipsLifeLabel.setTextFill(Color.RED);
                                    break;
                                default:
                                    break;
                            }

                            if (gameBoard.GetEnnemyShipsTotalLife() == 0)
                                ennemyShipsGridpane.setDisable(true);
                        }
                        else if (ennemyButton.getText().isEmpty())
                            ennemyButton.setDisable(true);


                        if (IsAIMode)
                            tryToShotPlayerShipsForAI();
                        else if (IsLANMode) {
                            if (gameBoard.GetIsYourTurn()) {
                                try {
                                    outputStream = server.GetOutputStream();

                                    if ((gameBoard.GetPlayerShipsTotalLife() == 0) || (gameBoard.GetEnnemyShipsTotalLife() == 0)) {
                                        outputStream.writeUTF("The game is finished.");
                                        outputStream.flush();
                                    }
                                    else {
                                        outputStream.writeUTF("You can play now.");
                                        outputStream.flush();

                                        gameBoard.SetIsYourTurn(false);
                                        ennemyShipsGridpane.setDisable(true);
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if (!gameBoard.GetIsYourTurn()) {
                                gameBoard.SetIsYourTurn(true);
                                ennemyShipsGridpane.setDisable(false);
                            }
                        }
                    }
                });

                ennemyShipsGridpane.add(ennemyButton, i, j);
                ennemyButton.toBack();
            }
        }
    }

    public void SetEnnemyShipsGridPaneEnabled(boolean value) {
        if (value)
            ennemyShipsGridpane.setDisable(false);
        else
            ennemyShipsGridpane.setDisable(true);
    }

    public GridPane GetEnnemyShipsGridPane() {
        return ennemyShipsGridpane;
    }

    private void initializeEnnemyShips() {
        List<String> directions = Arrays.asList("Vertical", "Horizontal");
        List<String> buttonsText = Arrays.asList("AC", "CT", "C", "S", "T");

        Random randomDirection = new Random();
        Random randomRow = new Random();
        Random randomColumn = new Random();

        for (int i=0; i<AIGameShips.size(); i++) {
            boolean isOccupiedVertical = true;
            boolean isOccupiedHorizontal = true;
            boolean retry = true;

            while (retry) {
                String direction = getRandomItem(directions, randomDirection);
                int row = randomRow.nextInt(10);
                int column = randomColumn.nextInt(10);

                if (direction == "Vertical") {
                    isOccupiedVertical = isOccupiedByOtherShip(ennemyShipsGridpane, row, ((row+1)-(AIGameShips.get(i).GetWidth())), column, row>=(AIGameShips.get(i).GetWidth()-1), false,"vertical");
                    if (!isOccupiedVertical) {
                        for (int k=row; k>=((row+1)-AIGameShips.get(i).GetWidth()); k--) {
                            Button currentButton = (Button)getNodeFromGridPane(ennemyShipsGridpane, column, k);
                            currentButton.setId(buttonsText.get(i));
                            currentButton.setText(buttonsText.get(i));
                        }
                    }
                }
                else if (direction == "Horizontal") {
                    isOccupiedHorizontal = isOccupiedByOtherShip(ennemyShipsGridpane, column, ((column)+(AIGameShips.get(i).GetWidth()-1)), row, false,column<=(10-AIGameShips.get(i).GetWidth()),"horizontal");
                    if (!isOccupiedHorizontal) {
                        for (int k=column; k<=(column+(AIGameShips.get(i).GetWidth()-1)); k++) {
                            Button currentButton = (Button)getNodeFromGridPane(ennemyShipsGridpane, k, row);
                            currentButton.setId(buttonsText.get(i));
                            currentButton.setText(buttonsText.get(i));
                        }
                    }
                }

                if (!isOccupiedVertical || !isOccupiedHorizontal) {
                    retry = false;

                    if (!isOccupiedVertical)
                        playerGameShips.get(i).SetDirection("Vertical");
                    else
                        playerGameShips.get(i).SetDirection("Horizontal");

                    AIGameShips.get(i).SetYPosition(row);
                    AIGameShips.get(i).SetXPosition(column);
                }
            }
        }

        ennemyShipsGridpane.setDisable(false);
    }

    private void tryToShotPlayerShipsForAI() {
        Random randomRow = new Random();
        Random randomColumn = new Random();
        boolean retry = true;

        while (retry) {
            int row = randomRow.nextInt(10);
            int column = randomColumn.nextInt(10);

            Button currentButton = (Button)getNodeFromGridPane(playerShipsGridpane, column, row);

            if (!currentButton.isDisabled()) {
                if ((!currentButton.getText().isEmpty()) && (gameBoard.GetPlayerShipsTotalLife()>=1)) {
                    gameBoard.ShotPlayerShip(currentButton.getId(), row, column);
                    currentButton.setDisable(true);

                    int currentLife = gameBoard.GetPlayerShipsTotalLife();
                    totalPlayerShipsLifeLabel.setText(String.valueOf(currentLife) + "/17");

                    switch (currentLife) {
                        case 12:
                            totalPlayerShipsLifeLabel.setTextFill(Color.YELLOWGREEN);
                            break;
                        case 8:
                            totalPlayerShipsLifeLabel.setTextFill(Color.ORANGE);
                            break;
                        case 3:
                            totalPlayerShipsLifeLabel.setTextFill(Color.RED);
                            break;
                        default:
                            break;
                    }

                    retry = false;

                    if (gameBoard.GetPlayerShipsTotalLife() == 0)
                        ennemyShipsGridpane.setDisable(true);
                }
                else if (gameBoard.GetPlayerShipsTotalLife()>=1) {
                    currentButton.setDisable(true);
                    retry = false;
                }
            }
            else
                retry = true;
        }
    }

    public Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
                return node;
        }
        return null;
    }

    private <T> T getRandomItem(List<T> list, Random rand) {
        return list.get(rand.nextInt(list.size()));
    }

    private boolean isOccupiedByOtherShip(GridPane gridPane, int startPosition, int endPosition, int location, boolean minRow, boolean maxColumn, String direction) {
        boolean result = true;

        if (direction.equals("vertical") && minRow) {
            for (int i=startPosition; i>=endPosition; i--) {
                Button currentButton = (Button)getNodeFromGridPane(gridPane, location, i);
                if (currentButton.getText().isEmpty())
                    result = false;
                else {
                    result = true;
                    break;
                }
            }
        }
        else if (direction.equals("horizontal") && maxColumn) {
            for (int i=startPosition; i<=endPosition; i++) {
                Button currentButton = (Button)getNodeFromGridPane(gridPane, i, location);
                if (currentButton.getText().isEmpty())
                    result = false;
                else {
                    result = true;
                    break;
                }
            }
        }
        else
            result = true;

        return result;
    }
}
