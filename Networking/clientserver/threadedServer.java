import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

//public class threadedServer implements Runnable {
public class threadedServer extends Thread{
    int PORT = 12890;
    InetAddress serverIP; //= InetAddress.getByName("127.0.0.1");
    static int MAXTHREADS = 4; //use up to 4 threads for clients
    ServerSocket socket;
    Socket clientSocket;
    String Username;
    String data;

    public threadedServer(Socket socket, InetAddress addr, int port){
        this.clientSocket = socket;
        this.PORT = port;
        this.serverIP = addr;
    }

    public threadedServer(Socket csocket, ServerSocket ssocket, InetAddress addr){
        this.clientSocket = csocket;
        this.socket = ssocket;
        this.serverIP = addr;
    }

    public void connectionListen() {
        System.out.println("Waiting for connections ...");
        while (true) {
            try {
                Socket client = socket.accept();

                /*
                new Thread(() -> {
                    System.out.println("Client accepted");
                    try {
                        readData(client);
                        System.out.println(data);
                    } catch (IOException ioe){
                        System.err.println("IOException reading client data.");
                    }
                }).start();
                */

            } catch (IOException ioe){
                System.err.println("IO error with socket connection.");
            }
        }
    }

    public void writeToClient(String data){
        //write to outputStream
    }

    public String getAddress(){
        return serverIP.toString();
    }

    @Override
    public void run() {
        //create a new server object which opens and accepts a client's connection
        try {
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while(true) {
                    data = fromClient.readLine();
                    if(data == null) {
                        System.err.println("CLIENT ERROR: null value received");
                        fromClient.close();
                        this.close();
                        break;
                    } else if (data.equals("DONE")) {
                        fromClient.close();
                        this.close();
                        break;
                    }
                    else{
                        System.out.println(data);
                    }

                }
            } catch (IOException e) {
                System.err.println("Error reading data from client");
            }

        }

    public void close(){
        try {
            socket.close();
            clientSocket.close();
        } catch (IOException ioe){
            System.err.println("Couldn't close socket");
        }
    }

}
