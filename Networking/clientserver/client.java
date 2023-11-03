import java.io.*;
import java.net.Socket;

public class client {
    String serverIP = "localhost";
    int Port = 12890;

    public static void main(String[] args){
        client client = new client();
        try {
            Socket socket = new Socket(client.getIP(), client.getPort());
            client.writeData(socket);
            socket.close();
        } catch (IOException ioe){
            System.err.println("Error opening socket");
        }

    }

    public void writeData(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);

        while(true){
            String input = reader.readLine();
            if(input.equals("DONE")){
                toServer.println(input);    //send the 'DONE' command to server so it can close gracefully
                reader.close();
                toServer.close();
                break;
            } else{
                toServer.println(input);
            }
        }
    }

    public void readData(){

    }

    public String getIP(){
        return serverIP;
    }

    public int getPort(){
        return Port;
    }
}
