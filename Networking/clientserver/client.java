import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {
    Socket socket;
    BufferedReader input;
    BufferedReader fromServer;
    PrintWriter toServer;

    public static void main(String[] args){
        InetAddress serverIP;
        int Port = 12890;
        try {
            serverIP = InetAddress.getLocalHost();
            Socket socket = new Socket(serverIP, Port);
            client client = new client(socket);
            client.startClient();
        } catch (UnknownHostException uhe){
            System.err.println("Unknown host\n" + uhe);
        } catch (IOException ioe){
            System.err.println("Error opening socket");
        }
    }

    public client(Socket socket){
        this.socket = socket;
    }

    private String readData(){
        //from server
        try {
            return fromServer.readLine();
        } catch (IOException e){
            System.err.println("Error reading from server\n" + e);
            return null;
        }
    }

    public void startClient() throws IOException {
        input = new BufferedReader(new InputStreamReader(System.in));
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer = new PrintWriter(socket.getOutputStream(), true);

        while(true){
            System.out.println(this.readData());
            String clientInput = input.readLine();
            String throwaway;
            if(clientInput == null) {
                System.err.println("null received, exiting...");
                socket.close();
                break;
            } else if(clientInput.equals("DONE")) {
                toServer.println(clientInput);    //send the 'DONE' command to server so it can close gracefully
                break;
            }  else if(clientInput.equals(System.lineSeparator())){
                throwaway = clientInput;
            } else{
                toServer.println(clientInput);
            }

        }
    }
}
