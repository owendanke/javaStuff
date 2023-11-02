import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class threadedServer implements Runnable {
    int PORT = 12890;
    static int MAXTHREADS = 4; //use up to 4 threads for clients
    ServerSocket socket;
    Socket clientSocket;
    String Username;
    String data;

    public threadedServer(Socket clientSocket, String username){
        this.clientSocket = clientSocket;
        this.Username = username;
    }

    public threadedServer(){

    }

    public void startServer(){
        try {

            //currently just do 2 clients for now

            Thread srv1 = new Thread(new threadedServer());
            Thread srv2 = new Thread(new threadedServer());

            srv1.start();
            System.out.println("Started client 1 thread");
            srv2.start();
            System.out.println("Started client 2 thread");

            /*

            for(int i=0, i < MAXTHREADS

             */
        } catch (IllegalArgumentException IAE){
            System.err.println("Specified port outside of range (0-65535)");
        } //catch (IOException ioe){
          //  System.err.println("Exception with sockets");
        //}
    }

    public void writeData(String data){
        //write to outputStream
    }

    public String readData() throws IOException {
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return fromServer.readLine();
    }

    public String getData(){
        return data;
    }

    public String getUsername() {
        return Username;
    }

    @Override
    public void run() {
        //create a new server object which opens and accepts a client's connection
        while(true){
            try {
                data = readData();
                if(!data.equals("DONE")){
                    System.out.println(data);
                }
                else {
                    close();
                    break;
                }
            } catch (IOException e) {
                System.err.println("Error reading data from client");
            }

        }
    }

    public void close(){
        try {
            socket.close();
        } catch (IOException ioe){
            System.err.println("Couldn't close socket");
        }
    }

}
