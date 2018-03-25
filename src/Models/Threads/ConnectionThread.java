package Models.Threads;

import static Login.Home.LoginController.server;

public class ConnectionThread extends Thread
{
    public ConnectionThread(String name) {
        super(name);
    }

    public void run() {
        while (true) {
            if (!server.GetIsConnectionAccepted() && !server.GetLoop()) {
                System.out.println("Connection thread started.");
                server.ListenForServerRequest();
            }
        }
    }
}
