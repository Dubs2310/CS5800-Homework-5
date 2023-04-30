import java.util.List;

public class Message {
    private User sender;
    private List<User> recipients;
    private String timestamp;
    private String messageContent;

    public Message(User sender, List<User> recipients, String timestamp, String messageContent) {
        this.sender = sender;
        this.recipients = recipients;
        this.timestamp = timestamp;
        this.messageContent = messageContent;
    }

    public User getSender() { return this.sender; }
    public List<User> getRecipients() { return this.recipients; }
    public String getTimestamp() { return this.timestamp; }
    public String getMessageContent() { return this.messageContent; }

    public MessageMemento createSnapshot() { return new MessageMemento(this.timestamp, this.messageContent); }
}