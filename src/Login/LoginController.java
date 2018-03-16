package Login;

import App.Main;
import Models.Connectivity.Server;
import Models.GameBoard.GameBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, Runnable
{
    @FXML
    private TextField pseudoTextField;
    @FXML
    private RadioButton AIModeRadioButton;
    @FXML
    private RadioButton playerModeRadioButton;
    @FXML
    private Label connectionStatusLabel;

    private GameBoard gameBoard;
    private Server server;
    private Thread threadServer;

    public static boolean IsAIMode;
    public static boolean IsLANMode;

    public static Stage gameStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup toggleGroup = new ToggleGroup();
        AIModeRadioButton.setToggleGroup(toggleGroup);
        playerModeRadioButton.setToggleGroup(toggleGroup);
    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws Exception {
        if (pseudoTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pseudo incorrect");
            alert.setContentText("Veuillez saisir un pseudo avant de lancer une partie.");
            alert.showAndWait();
        }
        else {
            if (AIModeRadioButton.isSelected()) {
                IsAIMode = true;
            }
            else if (playerModeRadioButton.isSelected()) {
                IsLANMode = true;

                connectionStatusLabel.setText("En attente de connexion d'un autre joueur...");
                connectionStatusLabel.setVisible(true);

                server = new Server();

                if (!server.IsConnected())
                    server.InitializeServer(22222, "localhost");

                threadServer = new Thread(this, "Battleship connection");
                threadServer.start();
            }

            if (IsAIMode) {
                gameBoard = new GameBoard();
                gameBoard.SetPlayerPseudo(pseudoTextField.getText());

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Game/Game.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage gameStage = new Stage();
                this.gameStage = gameStage;
                gameStage.setScene(new Scene(root));

                gameStage.show();
                Main.loginStage.close();
            }
        }
    }

    public void run() {
        while (true) {
            if (!server.GetIsConnectionAccepted() && !server.GetLoop()) {
                System.out.println("Thread");
                server.ListenForServerRequest();
            }
        }
    }
}
