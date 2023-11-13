import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class serverMain {

    public static void main(String[] args) throws UnknownHostException {
        ArrayList<threadedServer> serverThreads = new ArrayList<>();
        InetAddress serverIP = InetAddress.getByName("127.0.0.1");
        //threadedServer server = new threadedServer(serverIP);
        //System.out.println("Server running on: " + server.getAddress());
        //server.connectionListen();

        try {
            ServerSocket sSocket = new ServerSocket(12890);
            while(true) {
                Socket cSocket = sSocket.accept();
                threadedServer server = new threadedServer(cSocket, sSocket, serverIP, serverThreads);
                serverThreads.add(server);
                server.start();
                if(System.in.toString().equals("STOP")){
                    server.close();
                    break;
                }
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }

    }

}
