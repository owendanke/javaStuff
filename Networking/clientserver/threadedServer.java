import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

//public class threadedServer implements Runnable {
public class threadedServer extends Thread{
    int PORT = 12890;
    InetAddress serverIP; //= InetAddress.getByName("127.0.0.1");
    static int MAXTHREADS = 4; //use up to 4 threads for clients
    ServerSocket socket;
    Socket clientSocket;
    String Username;
    String data;
    PrintWriter writer;
    ArrayList<threadedServer> serverThreads;

    public threadedServer(Socket socket, InetAddress addr, int port){
        this.clientSocket = socket;
        this.PORT = port;
        this.serverIP = addr;
    }

    public threadedServer(Socket csocket, ServerSocket sSocket, InetAddress addr, ArrayList<threadedServer> serverThreads){
        this.clientSocket = csocket;
        this.socket = sSocket;
        this.serverIP = addr;
        this.serverThreads = serverThreads;
    }

    private void writeToClient(String output) throws IOException{
        writer.println(output);
        //toClient.close();
    }

    private void writeAll(String output) throws IOException{
        //PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
        for(threadedServer server : serverThreads){
            server.writer.println(output);
        }
    }

    private void getUsername(BufferedReader reader){
        try{
            writeToClient("Username: ");
            Username = reader.readLine();
        } catch (IOException e){
            System.err.println(e);
        }
    }

    public String getAddress(){
        return serverIP.toString();
    }

    @Override
    public void run() {
        //create a new server object which opens and accepts a client's connection
        try {
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            getUsername(fromClient);
            System.out.println(Username + " has connected");
            this.writeAll(Username + " has connected");

                while(true) {
                    data = fromClient.readLine();
                    if(data == null) {
                        System.err.println("CLIENT ERROR: null value received. Closing...");
                        fromClient.close();
                        this.close();
                        break;
                    } else if (data.equals("DONE")) {
                        System.out.println("Client disconnected");
                        this.writeToClient("Disconnected");
                        this.writeAll(Username + " has left");
                        fromClient.close();
                        this.close();
                        break;

                    }
                    else{
                        System.out.println(Username + ": " + data);
                        //write to sender
                        this.writeToClient(Username + ": " + data);
                        //write to others
                        //this.writeAll(Username + ": " + data);
                    }

                }
            } catch (SocketException se){
                System.err.println("Client socket exception, did the client disconnect?\n" + se);
            } catch (IOException e) {
                System.err.println("Error reading data from client\n" + e);
        }
    }

    public void close(){
        try {
            clientSocket.close();
            socket.close();
            System.out.println("Closed sockets");
        } catch (IOException ioe){
            System.err.println("Couldn't close socket");
        }
    }

}
