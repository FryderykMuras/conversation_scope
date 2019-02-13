package conversationScope;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ConversationManager{
    private static ConversationManager INSTANCE;
    private Map<String,Conversation> conversations;
    private Thread timeoutController;

    private ConversationManager() {
        this.conversations = new ConcurrentHashMap<>();
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

    public synchronized Conversation createConversation(){
        String conversationId = UUID.randomUUID().toString().replace("-", "");
        if(!this.conversations.containsKey(conversationId)){
            this.conversations.put(conversationId,new Conversation(conversationId));
        }
        return this.conversations.get(conversationId);
    }
    public synchronized Conversation createConversation(long timeOutPeriod){
        String conversationId = UUID.randomUUID().toString().replace("-", "");
        if(!this.conversations.containsKey(conversationId)){
            this.conversations.put(conversationId,new Conversation(conversationId,timeOutPeriod));
        }
        return this.conversations.get(conversationId);
    }

    synchronized Conversation createNestedConversation(String parentId) throws
            ConversationException{
        if((!this.conversations.containsKey(parentId)) || this.conversations.get(parentId).getStateAsString().equals("shortRunning")) {
            throw new ConversationException("No conversation with given ID or conversation with given ID is short-running");
        }
            Conversation nested = this.createConversation();
            nested.setParent(parentId);
        return nested;
    }

    public Map<String, Conversation> getConversationsMap() {
        return conversations;
    }

    synchronized void removeConversation(String id){
        conversations.remove(id);
    }
}