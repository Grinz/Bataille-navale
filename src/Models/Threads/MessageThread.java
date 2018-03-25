package Models.Threads;

import Chat.ChatController;

import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

import static Login.Home.LoginController.gameBoard;
import static Login.Home.LoginController.server;

public class MessageThread extends Thread
{
    private volatile boolean isRunning = true;
    private ChatController controller;

    private ObjectInputStream inputStream = null;

    public MessageThread(String name, ChatController controller) {
        super(name);
        this.controller = controller;
    }

    public void run() {
        while (isRunning) {
            if (inputStream == null) {
                try {
                    inputStream = server.GetInputStream();
                    String result = inputStream.readUTF();

                    controller.AddMessageInScrollPane(gameBoard.GetPlayerPseudo() + " : " + result);
                    System.out.println(gameBoard.GetPlayerPseudo() + " : " + result);
                }
                catch (EOFException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    inputStream = null;
                    isRunning = false;
                }
            }
        }
    }
}
