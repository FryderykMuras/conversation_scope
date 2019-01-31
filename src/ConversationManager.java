import java.util.HashMap;
import java.util.Map;

public class ConversationManager{
    private static ConversationManager INSTANCE;
    private Map<Integer,Conversation> conversations;

    private ConversationManager() {
        this.conversations = new HashMap<Integer,Conversation>();
        System.out.println("new instance");
    }

    public static ConversationManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ConversationManager();
        }
        System.out.println("Singletone");
        return INSTANCE;
    }

    public Conversation getConversation(Integer conversationId){
        if(!this.conversations.containsKey(conversationId)){
            this.conversations.put(conversationId,new Conversation(conversationId));
        }
        return this.conversations.get(conversationId);
    }



}