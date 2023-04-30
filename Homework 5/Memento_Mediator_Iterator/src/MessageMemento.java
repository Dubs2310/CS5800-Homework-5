public class MessageMemento {
    private String timestamp;
    private String messageContent;

    public MessageMemento(String timestamp, String messageContent) {
        this.timestamp = timestamp;
        this.messageContent = messageContent;
    }

    public String getTimestamp() { return this.timestamp; }
    public String getMessageContent() { return this.messageContent; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof MessageMemento memento)) return false;
        return memento.getTimestamp().equals(timestamp) && memento.getMessageContent().equals(messageContent);
    }
}