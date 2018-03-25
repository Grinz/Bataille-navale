package Models.Connectivity;

import Login.Home.LoginController;
import javafx.application.Platform;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static Login.Home.LoginController.gameBoard;

public class Server {
    private ServerSocket serverSocket;
    private String IP = "localhost";
    private int port = 22222;

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private boolean loop = true;
    private boolean isConnectionAccepted = false;

    private LoginController loginController;

    public Server() {
    }

    public boolean GetIsConnectionAccepted() {
        return isConnectionAccepted;
    }

    public boolean GetLoop() {
        return loop;
    }

    public ObjectOutputStream GetOutputStream() {
        return outputStream;
    }

    public ObjectInputStream GetInputStream() {
        return inputStream;
    }

    public void InitializeServer(int port, String IP) {
        try {
            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(IP));
        } catch (Exception e) {
            e.printStackTrace();
        }

        gameBoard.SetIsYourTurn(true);
        loop = false;
    }

    public boolean IsConnected() {
        try {
            socket = new Socket(IP, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
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
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
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
