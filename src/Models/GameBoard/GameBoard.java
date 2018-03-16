package Models.GameBoard;

import Models.Ships.*;

import java.util.Arrays;
import java.util.List;

public class GameBoard {
    private String playerPseudo;

    private boolean isYourTurn;

    private int playerShipsTotalLife = 17;
    private int AIShipsTotalLife = 17;

    private List<Ship> playerGameShips = Arrays.asList(new AircraftCarrier(5, 0, 0, "", 5), new CounterTorpedo(3, 0, 0, "", 3),
            new Cruiser(4, 0, 0, "",4), new Submarine(3, 0, 0,"", 3), new Torpedo(2, 0, 0,"", 2));

    private List<Ship> AIGameShips = Arrays.asList(new AircraftCarrier(5, 0,0, "",5), new CounterTorpedo(3, 0, 0, "",3),
            new Cruiser(4, 0, 0, "", 4), new Submarine(3, 0, 0, "",3), new Torpedo(2, 0, 0, "",2));

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

    public void SetAIShipsTotalLife(int totalLife) {
        this.AIShipsTotalLife = totalLife;
    }

    public int GetAIShipsTotalLife() {
        return AIShipsTotalLife;
    }

    public List<Ship> GetPlayerGameShips() {
        return playerGameShips;
    }

    public List<Ship> GetAIGameShips() {
        return AIGameShips;
    }

    public void ShotEnnemyShip(String shipType, int row, int column) {
        switch (shipType) {
            case "AC":
                AIGameShips.get(0).SetLife(AIGameShips.get(0).GetLife()-1);
                break;
            case "CT":
                AIGameShips.get(1).SetLife(AIGameShips.get(1).GetLife()-1);
                break;
            case "C":
                AIGameShips.get(2).SetLife(AIGameShips.get(2).GetLife()-1);
                break;
            case "S":
                AIGameShips.get(3).SetLife(AIGameShips.get(3).GetLife()-1);
                break;
            case "T":
                AIGameShips.get(4).SetLife(AIGameShips.get(4).GetLife()-1);
                break;
            default:
                break;
        }

        AIShipsTotalLife -= 1;
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
