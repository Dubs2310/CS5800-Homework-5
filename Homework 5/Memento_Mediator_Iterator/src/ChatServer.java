import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {
    private Map<User, ChatHistory> chatHistories;
    private Map<User, List<User>> blockedUsers;

    public ChatServer() {
        chatHistories = new HashMap<>();
        blockedUsers = new HashMap<>();
    }

    public void registerUser(User user) {
        chatHistories.put(user, new ChatHistory(user));
        blockedUsers.put(user, new ArrayList<>());
    }

    public void unregisterUser(User user) {
        chatHistories.remove(user);
        blockedUsers.remove(user);
    }

    public void sendMessage(Message message) {
        User sender = message.getSender();
        chatHistories.get(sender).addMessage(message, true);
        
        // only recipients that haven't blocked the user receive the message
        List<User> recipients = message.getRecipients();
        for (User user: recipients)
            if (!blockedUsers.get(user).contains(sender))
                user.receiveMessage(message);
            else
                user.receiveMessage(new Message(message.getSender(), message.getRecipients(), message.getTimestamp(), "[__BLOCKED_MESSAGE__]"));
    }

    public void updateChatHistory(User user, Message message) {
        chatHistories.get(user).addMessage(message, false);
    }

    public void blockUser(User blocking, User beingBlocked) {
        blockedUsers.get(blocking).add(beingBlocked);
    }

    public void unblockUser(User unblocking, User beingUnblocked) {
        blockedUsers.get(unblocking).remove(beingUnblocked);
    }

    public void undoLastMessageSent(User user) {
        Message lastMessageSent = chatHistories.get(user).getLastMessageSent();
        chatHistories.get(user).undoLastMessageSent();

        for (User u: chatHistories.keySet())
            if (u != user) chatHistories.get(u).deleteMessage(lastMessageSent);
    }

    public void viewCompleteChatHistory(User user) {
        System.out.println("*********** " + user.getName() + "'s Complete Chat History ***********");
        chatHistories.get(user).viewCompleteChatHistory();
    }

    public ChatHistory getUserChatHistory(User user) {
        return chatHistories.get(user);
    }
}