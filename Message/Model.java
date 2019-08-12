package Message;

import java.util.Map;

public class Model {
    Conversation CurrentConversation;
    public ConversationIndex setCurrentConversation(ConversationIndex conversations, Conversation con){
        conversations.Conversations.put(CurrentConversation.ConversationName, CurrentConversation);
        CurrentConversation = con;
        return conversations;
    }

    public ConversationIndex removeCurrentConversation(ConversationIndex conversations){
        conversations.Conversations.remove(CurrentConversation.ConversationName);
        for(Map.Entry<String, Conversation> entry : conversations.Conversations.entrySet()){
            System.out.println(entry);
        }
        CurrentConversation = null;
        return conversations;
    }
}
