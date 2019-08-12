package Message;

public class Message {
    String Content;
    String IPOrigin;
    String TimeStamp;
    public Message(String Content, String IPOrigin, String TimeStamp){
        this.Content = Content;
        this.IPOrigin = IPOrigin;
        this.TimeStamp = TimeStamp;
    }
}
