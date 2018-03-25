package Login.Home;

import App.Main;
import Models.Connectivity.Server;
import Models.GameBoard.GameBoard;
import Models.Threads.ConnectionThread;
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

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    @FXML
    private TextField pseudoTextField;
    @FXML
    private RadioButton AIModeRadioButton;
    @FXML
    private RadioButton playerModeRadioButton;
    @FXML
    private Label connectionStatusLabel;

    public static Server server;
    private ConnectionThread connectionThread;

    public static boolean IsAIMode;
    public static boolean IsLANMode;
    public static GameBoard gameBoard = new GameBoard();

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
                initializeGameUI();
            }
            else if (playerModeRadioButton.isSelected()) {
                IsLANMode = true;

                connectionStatusLabel.setText("En attente de connexion d'un autre joueur...");
                connectionStatusLabel.setVisible(true);

                server = new Server();
                server.SetLoginController(this);

                if (!server.IsConnected())
                    server.InitializeServer(22222, "localhost");

                connectionThread = new ConnectionThread("Battleship connection thread");
                connectionThread.start();
            }
        }
    }

    public void initializeGameUI() throws Exception {
        gameBoard.SetPlayerPseudo(pseudoTextField.getText());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Game/Game.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        Stage gameStage = new Stage();
        gameStage.setScene(new Scene(root));
        gameStage.show();

        if (IsLANMode) {
            fxmlLoader = new FXMLLoader(getClass().getResource("/Chat/Chat.fxml"));
            root = (Parent) fxmlLoader.load();

            Stage chatStage = new Stage();
            chatStage.setScene(new Scene(root));
            chatStage.show();
        }

        Main.loginStage.close();
    }
}
