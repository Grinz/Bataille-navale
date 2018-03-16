package Models.Connectivity;

import Login.LoginController;
import Models.GameBoard.GameBoard;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private String IP = "localhost";
    private int port = 22222;

    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    private boolean loop = true;
    private boolean isConnectionAccepted = false;

    private LoginController loginController;
    private GameBoard gameBoard;

    public Server() {
    }

    public boolean GetIsConnectionAccepted() {
        return isConnectionAccepted;
    }

    public boolean GetLoop() {
        return loop;
    }

    public void InitializeServer(int port, String IP) {
        try {
            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(IP));
        } catch (Exception e) {
            e.printStackTrace();
        }

        gameBoard = new GameBoard();
        gameBoard.SetIsYourTurn(true);

        loop = false;
    }

    public boolean IsConnected() {
        try {
            socket = new Socket(IP, port);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            isConnectionAccepted = true;

            loginController.initializeGameUI();
            System.out.println("Successfully connected to the server.");
        }
        catch (IOException e) {
            System.out.println("Unable to connect to the address: " + IP + ":" + port + " | Starting a server");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public void ListenForServerRequest() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            System.out.println("Socket accepted !");
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            isConnectionAccepted = true;

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        loginController.initializeGameUI();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SetLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
