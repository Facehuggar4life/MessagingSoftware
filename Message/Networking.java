package Message;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Networking extends Thread{
    Socket clientSocket;
    Socket incomingSocket;
    ServerSocket serverSocket;
    PrintWriter clientOut;
    BufferedReader serverIn;
    Controller con;
    public Networking(Controller con)  {
        try {
            serverSocket = new ServerSocket(1, 0);//makes server socket
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.con = con;
        this.start();//starts the server accept thread
    }

    public void connect(String IP) {
        if(clientSocket!=null){//closes the previous connection if the current socket is not null
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        clientSocket = new Socket();//initializes new socket
        InetSocketAddress address;
        try {
            address = new InetSocketAddress(IP, 1);
            clientSocket.connect(address, 10000);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "The specified IP address could not be found", "error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "We could not connect to that host", "error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "The output writer could not be initialized", "error", JOptionPane.ERROR_MESSAGE);
        }
        //Will later be used to detirmine if a message should be added to a backlog or send directly
    }


    public void sendMessage(String message){
        clientOut.println(message);//Sends string to the output stream
    }

    public String getUserIP(){
        try {
            return InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void run() {//Thread that runs to accept incoming connections and that feeds input to the general program
        super.run();
        while(true) {
            try {
                incomingSocket = serverSocket.accept();//accepts input and assigns it to socket
                serverIn = new BufferedReader(new InputStreamReader(incomingSocket.getInputStream()));//initializes BufferedReader from socket
            } catch (IOException e) {
                e.printStackTrace();
            }
            String inIP = incomingSocket.getInetAddress().toString();//gets IP of incoming connection
            Boolean isCurrentConnection = false;
            try {
                String currentConnectedIP = clientSocket.getInetAddress().toString();
                isCurrentConnection = currentConnectedIP.equals(inIP);
            }
            catch(NullPointerException e){
                e.printStackTrace();
            }
            if(!inIP.equals("/127.0.0.1")&&!isCurrentConnection) {//if to prevent user from having to make new contact if connecting to localhost
                //or if the incoming connection is from the client they've just connected to
                int option = JOptionPane.showConfirmDialog(null, "You have received an incoming connection from " +inIP +". Would you like to accept?", "Incoming Connection", JOptionPane.YES_NO_OPTION);
                //Prompts user to accept or decline connection then acts accordingly
                if (option == JOptionPane.OK_OPTION) {
                    con.promptUserForIncoming(inIP.substring(1));//removes initial '/' before prompting user to add contacts name
                    networkInputLoop();//While connected checks for incoming messages
                    JOptionPane.showMessageDialog(null, "You have been disconnected from " + inIP);
                } else if (option == JOptionPane.NO_OPTION) {
                    try {
                        incomingSocket.close();//Closes the connection if the user denies the connection
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "The connection was successfully closed");
                }
            }
            else{//case for localhost or if the incoming connection is a connection the user initiated
                networkInputLoop();
                JOptionPane.showMessageDialog(null, "You have been disconnected from " + inIP);
            }
        }
    }

    private void networkInputLoop(){
        String input;
        try {
            while ((input = serverIn.readLine()) != null) {
                Thread.sleep(100);//Adds delay to ensure c.myModel.CurrentConversation.Messages ArrayList isn't written to concurrently
                con.receiveMessage(input, incomingSocket.getInetAddress().toString());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

