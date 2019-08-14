package Message;

import java.util.ArrayList;

public class Conversation {
    String ConversationName;
    String IP;
    ArrayList <Message> Messages = new ArrayList<>();
    public Conversation(String ConversationName, String IP){
        this.ConversationName = ConversationName;
        this.IP = IP;
    }

}
