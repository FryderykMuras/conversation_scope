import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConversationManager{
    private static ConversationManager INSTANCE;
    private Map<String,Conversation> conversations;

    private ConversationManager() {
        this.conversations = new HashMap<>();
        System.out.println("new instance");
    }

    public static ConversationManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ConversationManager();
        }
        System.out.println("Singletone");
        return INSTANCE;
    }

    public Conversation getConversation(String conversationId) throws NoConversationException{
        if(!this.conversations.containsKey(conversationId)){
            throw new NoConversationException();
        }
        return this.conversations.get(conversationId);
    }
    public Conversation getConversation(){
        String conversationId = UUID.randomUUID().toString().replace("-", "");
        if(!this.conversations.containsKey(conversationId)){
            this.conversations.put(conversationId,new Conversation(conversationId));
        }
        return this.conversations.get(conversationId);
    }

    void removeConversation(String id){
        conversations.remove(id);
    }


}