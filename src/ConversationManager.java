import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConversationManager{
    private static ConversationManager INSTANCE;
    private Map<String,Conversation> conversations;
    private Thread timeoutController;

    private ConversationManager() {
        this.conversations = new HashMap<>();
        this.timeoutController = new Thread(new TimeoutController(this));
        timeoutController.start();
        System.out.println("new instance");

    }

    public static ConversationManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ConversationManager();
        }
        //System.out.println("Singletone");
        return INSTANCE;
    }

    public synchronized Conversation getConversation(String conversationId) throws ConversationException{
        if(this.conversations.containsKey(conversationId)){
            this.conversations.get(conversationId).setTimeOut();
            return this.conversations.get(conversationId);
        }
        throw new ConversationException("No conversation with given ID");
    }
    public synchronized Conversation getConversation(){
        String conversationId = UUID.randomUUID().toString().replace("-", "");
        if(!this.conversations.containsKey(conversationId)){
            this.conversations.put(conversationId,new Conversation(conversationId));
        }
        return this.conversations.get(conversationId);
    }
    public synchronized Conversation getConversation(long timeOutPeriod){
        String conversationId = UUID.randomUUID().toString().replace("-", "");
        if(!this.conversations.containsKey(conversationId)){
            this.conversations.put(conversationId,new Conversation(conversationId,timeOutPeriod));
        }
        return this.conversations.get(conversationId);
    }

    public synchronized Conversation createNestedConversation(String parentId, long timeOutPeriod) throws
            ConversationException{
        if(this.conversations.get(parentId).getStateAsString().equals("shortRunning"))
            throw new ConversationException("No conversation with given ID");

        String conversationId = UUID.randomUUID().toString().replace("-", "");
        if(!this.conversations.containsKey(conversationId)){
            Conversation nested = new Conversation(conversationId,timeOutPeriod);
            nested.setParent(parentId);
            this.conversations.put(conversationId,nested);
            this.conversations.get(parentId).addNested(conversationId);
        }
        return this.conversations.get(conversationId);
    }

    Map<String, Conversation> getConversationsMap() {
        return conversations;
    }

    synchronized void removeConversation(String id){
        conversations.remove(id);
    }
}