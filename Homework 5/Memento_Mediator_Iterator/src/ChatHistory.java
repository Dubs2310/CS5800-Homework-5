import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class ChatHistory implements IterableByUser {
    private User user;
    private List<Message> messages;
    private Stack<MessageMemento> messagesSent;

    public ChatHistory(User user) {
        this.user = user;
        this.messages = new ArrayList<>();
        this.messagesSent = new Stack<>();
    }

    public User getUser() { return user; }
    public List<Message> getMessages() { return messages; }

    public void addMessage(Message message, boolean isSender) {
        messages.add(message);
        if (isSender) messagesSent.add(message.createSnapshot());
    }
    
    public Message getLastMessageSent() {
        if (!messagesSent.empty())
            for (Message message : messages)
                if (message.createSnapshot().equals(messagesSent.peek()))
                    return message;
        return null;
    }

    public Message getLastMessage() {
        if (messages.size() == 0) return null;
        return messages.get(messages.size() - 1);
    }

    public void deleteMessage(Message message) {
        if (messages.size() == 0) return;
        int index = messages.lastIndexOf(message);
        messages.set(
                index,
                new Message(message.getSender(), message.getRecipients(), message.getTimestamp(), "[__MESSAGE_DELETED__]")
        );
    }

    public void undoLastMessageSent() {
        if (messages.size() == 0) return;
        deleteMessage(getLastMessageSent());
        messagesSent.pop();
    }

    public void viewCompleteChatHistory() {
        for (Message message : messages)
            System.out.println(
                "[" + message.getTimestamp() + "] " +
                (messagesSent.contains(message.createSnapshot()) ? "You" : message.getSender().getName()) + ": " +
                message.getMessageContent()
            );
        System.out.println();
    }

    @Override
    public Iterator iterator(User userToSearchWith) {
        return new SearchMessagesByUserIterator(userToSearchWith, this);
    }
}