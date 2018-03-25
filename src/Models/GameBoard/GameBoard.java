package Models.GameBoard;

import Models.Ships.*;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class GameBoard implements Serializable {
    private String playerPseudo;

    private boolean isYourTurn;

    private int playerShipsTotalLife = 17;
    private int ennemyShipsTotalLife = 17;

    private List<Ship> playerGameShips = Arrays.asList(new AircraftCarrier(5, 0, 0, "", 5), new CounterTorpedo(3, 0, 0, "", 3),
            new Cruiser(4, 0, 0, "",4), new Submarine(3, 0, 0,"", 3), new Torpedo(2, 0, 0,"", 2));

    private List<Ship> ennemyGameShips = Arrays.asList(new AircraftCarrier(5, 0,0, "",5), new CounterTorpedo(3, 0, 0, "",3),
            new Cruiser(4, 0, 0, "", 4), new Submarine(3, 0, 0, "",3), new Torpedo(2, 0, 0, "",2));

    private String[][] playerShipsButtonsArray = new String[10][10];
    private String[][] ennemyShipsButtonsArray = new String[10][10];

    public GameBoard() {
    }

    public void SetPlayerPseudo(String pseudo) {
        this.playerPseudo = pseudo;
    }

    public String GetPlayerPseudo() {
        return playerPseudo;
    }

    public void SetIsYourTurn(boolean isYourTurn) {
        this.isYourTurn = isYourTurn;
    }

    public boolean GetIsYourTurn() {
        return isYourTurn;
    }

    public void SetPlayerShipsTotalLife(int totalLife) {
        this.playerShipsTotalLife = totalLife;
    }

    public int GetPlayerShipsTotalLife() {
        return playerShipsTotalLife;
    }

    public void SetEnnemyShipsTotalLife(int totalLife) {
        this.ennemyShipsTotalLife = totalLife;
    }

    public int GetEnnemyShipsTotalLife() {
        return ennemyShipsTotalLife;
    }

    public List<Ship> GetPlayerGameShips() {
        return playerGameShips;
    }

    public List<Ship> GetEnnemyGameShips() {
        return ennemyGameShips;
    }

    public void SetPlayerShipsButtons(String[][] playerShipsButtonsArray) {
        this.playerShipsButtonsArray = playerShipsButtonsArray;
    }

    public String[][] GetPlayerShipsButtons() {
        return playerShipsButtonsArray;
    }

    public void SetEnnemyShipsButtons(String[][] ennemyShipsButtonsArray) {
        this.ennemyShipsButtonsArray = ennemyShipsButtonsArray;
    }

    public String[][] GetEnnemyShipsButtons() {
        return ennemyShipsButtonsArray;
    }

    public void ShotEnnemyShip(String shipType, int row, int column) {
        switch (shipType) {
            case "AC":
                ennemyGameShips.get(0).SetLife(ennemyGameShips.get(0).GetLife()-1);
                break;
            case "CT":
                ennemyGameShips.get(1).SetLife(ennemyGameShips.get(1).GetLife()-1);
                break;
            case "C":
                ennemyGameShips.get(2).SetLife(ennemyGameShips.get(2).GetLife()-1);
                break;
            case "S":
                ennemyGameShips.get(3).SetLife(ennemyGameShips.get(3).GetLife()-1);
                break;
            case "T":
                ennemyGameShips.get(4).SetLife(ennemyGameShips.get(4).GetLife()-1);
                break;
            default:
                break;
        }

        ennemyShipsTotalLife -= 1;
    }

    public void ShotPlayerShip(String shipType, int row, int column) {
        switch (shipType) {
            case "AC":
                playerGameShips.get(0).SetLife(playerGameShips.get(0).GetLife()-1);
                break;
            case "CT":
                playerGameShips.get(1).SetLife(playerGameShips.get(1).GetLife()-1);
                break;
            case "C":
                playerGameShips.get(2).SetLife(playerGameShips.get(2).GetLife()-1);
                break;
            case "S":
                playerGameShips.get(3).SetLife(playerGameShips.get(3).GetLife()-1);
                break;
            case "T":
                playerGameShips.get(4).SetLife(playerGameShips.get(4).GetLife()-1);
                break;
            default:
                break;
        }

        playerShipsTotalLife -= 1;
    }
}
