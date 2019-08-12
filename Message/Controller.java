package Message;

import javax.swing.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

public class Controller {
    View myView;
    Model myModel;
    Networking net = new Networking(this);
    ConversationIndex conversations = new ConversationIndex();
    private JComboBox<String> contacts;
    private JButton sendMessage;
    private JButton addContact;
    private JButton deleteContact;
    private JButton reconnect;
    private JTextField userMessage;

    public Controller(View view, Model mod){
        this.myModel = mod;
        this.myView = view;
        contacts = myView.contactSelection;
        sendMessage = myView.sendMessage;
        addContact = myView.addContact;
        deleteContact = myView.deleteContact;
        reconnect = myView.reconnectToServer;
        userMessage = myView.userInMessage;
    }

    public void initiateController(){
        addContact.addActionListener(e -> promptUser());
        contacts.addActionListener(e-> setContact());
        sendMessage.addActionListener(e -> sendMessage());
        deleteContact.addActionListener(e -> removeContact());
        reconnect.addActionListener(e -> reconnectToServer());
    }

    //UI input

    public void promptUserForIncoming(String IP){//Makes a window to add a contact where the IP value is already set and should not be changed
        JTextField contactName = new JTextField();
        JTextField IPAddress = new JTextField();
        IPAddress.setEditable(false);
        IPAddress.setText(IP);
        Object[] UI = {"Name: ", contactName,
                "IP: ", IPAddress};
        int option = JOptionPane.showConfirmDialog(null, UI, "Add Contact", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            addContact(contactName.getText(), IPAddress.getText());//feeds input to addContact method
        }
    }

    public void promptUser(){//Makes a window to add a contact
        JTextField contactName = new JTextField();
        JTextField IPAddress = new JTextField();
        Object[] UI = {"Name: ", contactName,
                "IP: ", IPAddress};
        int option = JOptionPane.showConfirmDialog(null, UI, "Add Contact", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            addContact(contactName.getText(), IPAddress.getText());//feeds input to addContact method
        }
    }

    //contact management

    public void addContact(String userName, String IP){
        Conversation newConversation = new Conversation(userName, IP);
        System.out.println(userName + "END");
        System.out.println(IP + "END");
        conversations.Conversations.put(userName, newConversation);
        if(contacts.getItemCount()>0) {
            //if the JCB has 0 items adding an items will trigger the listener attached to the JCB
            //As well as the call to set the current item, so this if only sets the JCB if the JCB contains an item
            contacts.addItem(newConversation.ConversationName);
            contacts.setSelectedItem(newConversation.ConversationName);
        }
        else{
            contacts.addItem(newConversation.ConversationName);
        }
    }

    public void removeContact(){
        if(contacts.getSelectedItem() != null) {
            contacts.removeItemAt(contacts.getSelectedIndex());
            conversations = myModel.removeCurrentConversation(conversations);
        }
    }

    public void setContact(){
        if(contacts.getSelectedItem()!=null){
            int Index = contacts.getSelectedIndex();
            Conversation conversation = conversations.Conversations.get(contacts.getItemAt(Index));
            //gets conversation object from the Conversation has with the value returned from the JCB as the Key
            myModel.CurrentConversation = conversation;//Sets the model's conversation object
            conversations = myModel.setCurrentConversation(conversations, conversation);//Updates the conversations object
            updateMessages();//updates the messageDisplay JTextArea
            net.connect(myModel.CurrentConversation.IP);//Connects to the client in question
        }
    }

    public void reconnectToServer(){
        System.out.println("Reconnect");
        if(myModel.CurrentConversation == null){
            JOptionPane.showMessageDialog(null,  "Choose a conversation before trying to reconnect","Cannot reconnect", JOptionPane.ERROR_MESSAGE);
        }
        else{
            net.connect(myModel.CurrentConversation.IP);//calls connect method again
        }
    }

    //Message handling

    public void sendMessage(){
        if(myModel.CurrentConversation == null){
            JOptionPane.showMessageDialog(null,  "Choose a conversation before trying to send a message","Cannot send", JOptionPane.ERROR_MESSAGE);
        }
        else if(!net.clientSocket.isConnected()){
            System.out.println("Backlog");
        }
        else {
            net.sendMessage(userMessage.getText());
            Message outgoingMessage = generateMessage(userMessage.getText(), net.getUserIP());
            userMessage.setText("");
            myModel.CurrentConversation.Messages.add(outgoingMessage);
            updateMessages();
        }
    }

    public void receiveMessage(String message, String IP){
        if(myModel.CurrentConversation!=null) {//Extra protection to make sure messages are written to a null Conversation
            //Probably unnecessary, but I'll do some testing to make sure there aren't edge cases where this is relevant
                Message incomingMessage = generateMessage(message, IP);//calls method to generate a timestamped Message object
                myModel.CurrentConversation.Messages.add(incomingMessage);//Adds Message to the current Conversation
                updateMessages();//update the screen in accordance with the Messages ArrayList
        }

    }

    //Formatting

    public void updateMessages(){
        try {
            myView.messageDisplay.setText(generateMessageString(myModel.CurrentConversation.Messages));
            //Sets messageDisplay text to correctly formatted text derived from Messages ArrayList
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public Message generateMessage(String message, String IP){
        Date timeStamper = new Date();
        String timeStamp = timeStamper.toString();
        System.out.println(IP);
        System.out.println(message);
        System.out.println(timeStamp);
        Message incomingMessage = new Message(message, IP, timeStamp);
        return incomingMessage;
    }

    private String generateMessageString(ArrayList<Message> messageHistory) throws UnknownHostException {
        String MessageString = "";
        for(Message m: messageHistory){//Loops through all messages in the current Conversation and Formats the conversation before adding it to a string
            if((m.IPOrigin).equals(net.getUserIP())){
                //if the IP attached to the message is equivalent to your IP it is assumed to be sent by you and formatted appropriately
                MessageString = MessageString + "At "+ m.TimeStamp +" You: " + m.Content + "\n";
            }
            else{
                MessageString = MessageString + "At " +m.TimeStamp + " " + myModel.CurrentConversation.ConversationName +": "  + m.Content + "\n";
            }
        }
        return MessageString;
    }
}
