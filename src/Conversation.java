import java.util.HashMap;
import java.util.Map;

public class Conversation{
    Integer conversationID;
    private Map<String,Object> scopedItems;

    public Conversation(Integer id){
        this.conversationID=id;
        this.scopedItems = new HashMap<>();
    }
    void setValue(String name, Object obj){
        this.scopedItems.put(name,obj);
    }
    Object getReference(String name){
        return this.scopedItems.get(name);
    }
}