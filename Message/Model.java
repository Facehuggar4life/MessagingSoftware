package Message;

public class Model {
    Conversation CurrentConversation;
    public ConversationIndex setCurrentConversation(ConversationIndex conversations, Conversation con){
        conversations.Conversations.put(CurrentConversation.ConversationName, CurrentConversation);
        CurrentConversation = con;
        return conversations;
    }

    public ConversationIndex removeCurrentConversation(ConversationIndex conversations){
        conversations.Conversations.remove(CurrentConversation.ConversationName);//Gets rid of current conversation in conversation.Conversations Hashmap
        CurrentConversation = null;//Sets the current conversation to null
        return conversations;
    }
}
