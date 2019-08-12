package Message;

import java.util.HashMap;

public class ConversationIndex {
    public HashMap <String, Conversation> Conversations = new HashMap<>();

    public void addConversation(String Name, String IP){
        Conversation conversation = new Conversation(Name, IP);
        Conversations.put(Name, conversation);
    }

    public Conversation getConversation(String Name){
        return Conversations.get(Name);
    }
}
