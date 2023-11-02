import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class threadedServer implements Runnable {
    int PORT = 12890;
    int MAXTHREADS;
    Socket socket;
    String Username;
    String data;
    DataInputStream inputStream;
    DataOutputStream outputStream;

    public threadedServer(Socket socket, String username){
        this.socket = socket;
        this.Username = username;
    }

    public void setup() {
        try {
            ServerSocket serverSkt = new ServerSocket(PORT);
        } catch (IOException ioe){
            System.err.println("Error opening port");
        } catch (IllegalArgumentException IAE){
            System.err.println("Specified port outside of range (0-65535)");
        }
    }

    public void start(){
        try {
            ServerSocket srvSkt = new ServerSocket(PORT);
            Socket client1 = srvSkt.accept();
            Socket client2 = srvSkt.accept();

            Thread srv1 = new Thread(new threadedServer(client1, "client1"));
            Thread srv2 = new Thread(new threadedServer(client2, "client2"));


            srv1.start();
            System.out.println("Started client 1 thread");
            srv2.start();
            System.out.println("Started client 2 thread");
        } catch (IllegalArgumentException IAE){
            System.err.println("Specified port outside of range (0-65535)");
        } catch (IOException ioe){
            System.err.println("Exception with sockets");
        }
    }

    public void writeData(String data){
        //write to outputStream
    }

    public void readData(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));



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


    }

    public static void main(String[] args){



    }

}
