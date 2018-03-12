package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    public static Stage loginStage;

    @Override
    public void start(Stage loginStage) throws Exception
    {
        this.loginStage = loginStage;

        Parent root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
        loginStage.setTitle("Battleship - Login");
        loginStage.setScene(new Scene(root, 888, 531));
        loginStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
