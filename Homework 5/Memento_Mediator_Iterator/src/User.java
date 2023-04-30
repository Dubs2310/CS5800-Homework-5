import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class User implements IterableByUser {
    private String name;
    private ChatServer server;

    public User(String name, ChatServer server) {
        this.name = name;
        this.server = server;
    }

    public String getName() { return this.name; }
    public ChatServer getServer() { return server; }

    public void sendMessage(List<User> recipients, String messageContent) {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        Message message = new Message(this, recipients, timestamp, messageContent);
        server.sendMessage(message);
    }

    public void receiveMessage(Message message) {
        server.updateChatHistory(this, message);
    }

    public void blockUser(User toBlock) {
        server.blockUser(this, toBlock);
    }

    public void unblockUser(User toUnblock) {
        server.unblockUser(this, toUnblock);
    }

    public void undoLastMessageSent() {
        server.undoLastMessageSent(this); 
    }

    public void viewCompleteChatHistory() {
        server.viewCompleteChatHistory(this);
    }

    @Override
    public Iterator iterator(User userToSearchWith) {
        return server.getUserChatHistory(this).iterator(userToSearchWith);
    }

    public void viewChatHistoryWithUser(User user) {
        System.out.println("*********** " + name + "'s Chat History with " + user.getName() + " ***********");
        SearchMessagesByUserIterator myMessages = (SearchMessagesByUserIterator) iterator(user);
        while (myMessages.hasNext()) {
            Message message = myMessages.next();
            String senderName = message.getSender().getName();
            System.out.println(
                "[" + message.getTimestamp() + "] " +
                (senderName.equals(name) ? "You" : senderName) + ": " +
                message.getMessageContent()
            );
        }
        System.out.println();
    }
}