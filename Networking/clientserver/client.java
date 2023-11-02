import java.io.*;
import java.net.Socket;

public class client {
    String serverIP = "localhost";
    int Port = 12890;

    public void writeData(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
        String input = reader.readLine();
        do{
            toServer.println(input);
        } while(!reader.readLine().equals("DONE"));

    }

    public void readData(){

    }

    public String getIP(){
        return serverIP;
    }

    public int getPort(){
        return Port;
    }

    public static void main(String[] args){
        client client = new client();
        try {
            Socket socket = new Socket(client.getIP(), client.getPort());
            client.writeData(socket);
        } catch (IOException ioe){
            System.err.println("Error opening socket");
        }

    }

}
