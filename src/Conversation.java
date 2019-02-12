import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Conversation implements Visitable{
    private String conversationID;
    private ConversationState state;
    private Map<String,Object> scopedItems;
    private long timeOutTime;
    private long timeOutPeriod;
    private String parentId;
    private LinkedList<String> nestedIds;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public Conversation(String id){
        this(id, 20*60*1000);
    }

    public Conversation(String id,long timeOutPeriod){
        this.conversationID=id;
        this.state = new ConversationShortRunningState(this);
        this.scopedItems = new HashMap<>();
        this.timeOutPeriod = timeOutPeriod;
        this.parentId = null;
        this.nestedIds = new LinkedList<>();
        setTimeOut();
    }

    void setParent(String parentId){
        this.parentId = parentId;
    }

    void addNested(String id){
        this.nestedIds.add(id);
    }

    void setTimeOut(){
        this.timeOutTime = System.currentTimeMillis() + timeOutPeriod;
    }
    long getTimeOut(){
        return this.timeOutTime;
    }
    void setValue(String name, Object obj){
        this.scopedItems.put(name,obj);
    }
    Object getReference(String name)throws ConversationException{
        if(this.scopedItems.containsKey(name)){
            return this.scopedItems.get(name);
        }else{
            throw new ConversationException("No value with given name in this scope");
        }
    }
    void setState(String type){
        if(type.equals("longRunning")){
            this.state = new ConversationLongRunningState(this);
        }else if(type.equals("shortRunning")){
            this.state = new ConversationShortRunningState(this);
        }
    }

    String getStateAsString(){
        return this.state.toString();
    }

    String getId(){
        return this.conversationID;
    }
    void begin() throws ConversationException{
        this.state.beginAction();
    }
    void end() throws ConversationException{
        for (String id: this.nestedIds) {
            try {
                ConversationManager.getInstance().getConversation(id).end();
            } catch (ConversationException e) {
                e.printStackTrace();
            }
            //nie wiem czy to powinno zostać...
            //this.nestedIds.remove(id);
        }

        this.state.endAction();
    }
    void endRequest(){
        for (String id: this.nestedIds){
            try {
                ConversationManager.getInstance().getConversation(id).endRequest();
            } catch (ConversationException e) {
                e.printStackTrace();
            }
        }
        this.state.endOfRequestAction();

//    System.out.println("manual conv delete");
//    ConversationManager.getInstance().removeConversation(this.conversationID);
//        else
//            System.out.println("short - nie usuwam");
//        if(this.state.toString().equals("shortRunning")){
//            ConversationManager.getInstance().removeConversation(this.conversationID);
//        }
    }
}