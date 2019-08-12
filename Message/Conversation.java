package Message;

import java.util.ArrayList;

public class Conversation {
    String ConversationName;
    String IP;
    ArrayList <Message> Messages = new ArrayList<>();
    public Conversation(String ConvoName, String IPAddress){
        this.ConversationName = ConvoName;
        this.IP = IPAddress;
    }

}
