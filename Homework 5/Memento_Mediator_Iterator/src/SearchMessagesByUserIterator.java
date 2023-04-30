import java.util.Iterator;
import java.util.List;

public class SearchMessagesByUserIterator implements Iterator<Message> {
    private int index;
    private int size;
    private User user;
    private List<Message> messages;
    private User userToSearchFor;

    public SearchMessagesByUserIterator(User userToSearchFor, ChatHistory history) {
        this.userToSearchFor = userToSearchFor;
        this.user = history.getUser();
        this.messages = history.getMessages();
        this.index = 0;
        this.size = messages.size();
    }

    @Override
    public boolean hasNext() {
        Message message = null;
        while (index < size) {
            message = messages.get(index);
            if (
                (message.getSender().equals(user) && message.getRecipients().contains(userToSearchFor)) ||
                message.getSender().equals(userToSearchFor)
            )
                return true;
            index++;
        }
        return false;
    }

    @Override
    public Message next() {
        if (hasNext())
            return messages.get(index++);
        return null;
    }

    @Override
    public void remove() {
        Iterator.super.remove();
    }
}
