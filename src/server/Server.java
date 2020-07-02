package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

// Please start Server class first and than ClientWithGui class

public class Server extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for client...");
                try (ServerSocket serverSocket = new ServerSocket(8000);
                     Socket server = serverSocket.accept()) 
                {
                    System.out.println("Connected with client");
                    DataInputStream in = new DataInputStream(server.getInputStream());

                    serverSocket.setSoTimeout(10000);

                    Integer x = in.readInt();
                    System.out.println("First number : " + x);
                    Integer y = in.readInt();
                    System.out.println("Second number : " + y);
                    Integer sum = (x + y);
                    System.out.println("Sum : " + sum);
                    System.out.println("----------------------------------------");

                    DataOutputStream out = new DataOutputStream(server.getOutputStream());
                    out.writeInt(sum);
                }
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!" + s.getMessage());
                break;
            } catch (IOException e) {
                System.out.println("Error: ServerIO connection" + e.getMessage());
                break;
            }
        }
    }

    public static void main(String[] args) {

        Thread t = new Server();
        t.start();
    }
}
