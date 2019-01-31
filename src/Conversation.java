import java.util.HashMap;
import java.util.Map;

public class Conversation implements Visitable{
    private String conversationID;
    private ConversationState state;
    private Map<String,Object> scopedItems;
    private long timeOutTime;
    private long timeOutPeriod;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public Conversation(String id){
        this.conversationID=id;
        this.state = new ConversationShortRunningState(this);
        this.scopedItems = new HashMap<>();
        this.timeOutPeriod = 20*60*1000;
        setTimeOut();
    }

    public Conversation(String id,long timeOutPeriod){
        this.conversationID=id;
        this.state = new ConversationShortRunningState(this);
        this.scopedItems = new HashMap<>();
        this.timeOutPeriod = timeOutPeriod;
        setTimeOut();
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
    String getId(){
        return this.conversationID;
    }
    void begin() throws ConversationException{
        this.state.beginAction();
    }
    void end() throws ConversationException{
        this.state.endAction();
    }
    void endRequest(){
        if(this.state.toString().equals("shortRunning")){
            ConversationManager.getInstance().removeConversation(this.conversationID);
        }
    }
}