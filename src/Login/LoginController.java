package Login;

import App.Main;
import Models.GameBoard.GameBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController
{
    @FXML
    private TextField pseudoTextField;

    private GameBoard gameBoard;

    public static Stage gameStage;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        if (pseudoTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pseudo incorrect");
            alert.setContentText("Veuillez saisir un pseudo avant de lancer une partie.");
        }
        else {
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
