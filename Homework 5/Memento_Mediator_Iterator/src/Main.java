import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void dialogueSoFar(User john, User sam, User dean) {
        john.viewCompleteChatHistory();
        dean.viewCompleteChatHistory();
        sam.viewCompleteChatHistory();
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();

        User john = new User("John", server);
        User sam = new User("Sam", server);
        User dean = new User("Dean", server);

        server.registerUser(john);
        server.registerUser(sam);
        server.registerUser(dean);

        System.out.println("----------------------------------------------------- ACT 1: THE BLOCK -----------------------------------------------------\n");

        john.sendMessage(
            new ArrayList<>(List.of(sam)),
            "Sam, monster hunting is the family business. Do it for your mother."
        );
        sam.blockUser(john);
        john.sendMessage(
            new ArrayList<>(List.of(sam)),
            "Did you really just block me?!"
        );
        john.sendMessage(
            new ArrayList<>(List.of(dean)),
            "Dean, tell Sam to get his head out of his a** and unblock me! I'm not done with him!"
        );
        dialogueSoFar(john, sam, dean);

        System.out.println("----------------------------------------------------- ACT 2: THE UNBLOCK AND APOLOGY -----------------------------------------------------\n");

        dean.sendMessage(
            new ArrayList<>(Arrays.asList(john, sam)),
            "Hey Sammy, just unblock your old man. He's after me now... 😒"
        );
        sam.unblockUser(john);
        john.sendMessage(
            new ArrayList<>(Arrays.asList(sam, dean)),
            "Okay Sam, I'm sorry..."
        );

        dialogueSoFar(john, sam, dean);

        System.out.println("----------------------------------------------------- ACT 3: THE UNDO -----------------------------------------------------\n");

        john.undoLastMessageSent();
        sam.sendMessage(
            new ArrayList<>(Arrays.asList(john, dean)),
            "Woah, did you just apologize?! 😳"
        );
        john.sendMessage(
            new ArrayList<>(Arrays.asList(sam, dean)),
            "I did, but looks like you two don't deserve it. Thank your elder brother for his smarta** comment"
        );
        dialogueSoFar(john, sam, dean);

        System.out.println("----------------------------------------------------- TESTING ITERATOR -----------------------------------------------------\n");
        john.viewChatHistoryWithUser(sam);
        john.viewChatHistoryWithUser(dean);
    }
}