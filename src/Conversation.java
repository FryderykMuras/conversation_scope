import java.util.HashMap;
import java.util.Map;

public class Conversation{
    private String conversationID;
    Boolean conversationLongRunning;
    private Map<String,Object> scopedItems;

    public Conversation(String id){
        this.conversationID=id;
        conversationLongRunning = false;
        this.scopedItems = new HashMap<>();
    }
    void setValue(String name, Object obj){
        this.scopedItems.put(name,obj);
    }
    Object getReference(String name){
        return this.scopedItems.get(name);
    }
    String getId(){
        return this.conversationID;
    }
    void begin(){
        this.conversationLongRunning = true;
    }
    void end(){
        this.conversationLongRunning = false;
    }
    void endRequest(){
        if(!conversationLongRunning){
            ConversationManager.getInstance().removeConversation(this.conversationID);
        }
    }
}