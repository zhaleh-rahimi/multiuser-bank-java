package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by DOTIN SCHOOL 4 on 9/17/2016.
 */
public class ThreadedServer {

    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Connection Socket Created");
            while (true) {
                try {
                    System.out.println("Waiting for Connection");
                    socket = serverSocket.accept();
                    System.out.println("sth arrived");
                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }
                new MyServerSocket(socket).start();

            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: 8080 ");
            System.exit(1);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Could not close port: 8080 ");
                System.exit(1);
            }
        }
    }
}
