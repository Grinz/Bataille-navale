package Chat;

import Models.Threads.MessageThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import static Login.Home.LoginController.server;

public class ChatController implements Initializable
{
    @FXML
    private ScrollPane messagesScrollPane;

    @FXML
    private TextField messageTextField;

    private MessageThread messageThread;
    private ObjectOutputStream outputStream = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        messagesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        messagesScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }

    @FXML
    protected void handleSendMessageButtonAction(ActionEvent event) throws Exception {
        if (!messageTextField.getText().isEmpty()) {
            try {
                outputStream = server.GetOutputStream();
                outputStream.writeUTF(messageTextField.getText());
                outputStream.flush();

                messageThread = new MessageThread("Message thread", this);
                messageThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void AddMessageInScrollPane(String message) {

    }
}
