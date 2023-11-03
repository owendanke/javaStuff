import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class serverMain {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress serverIP = InetAddress.getByName("127.0.0.1");
        //threadedServer server = new threadedServer(serverIP);
        //System.out.println("Server running on: " + server.getAddress());
        //server.connectionListen();

        try {
            ServerSocket sSocket = new ServerSocket(12890);
            while(true) {
                Socket cSocket = sSocket.accept();
                threadedServer server = new threadedServer(cSocket, sSocket, serverIP);
                server.start();
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }

    }

}
