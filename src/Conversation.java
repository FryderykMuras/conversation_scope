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
        this.state = StateFactory.getShortRunningState();
        this.scopedItems = new HashMap<>();
        this.timeOutPeriod = timeOutPeriod;
        this.parentId = null;
        setTimeOut();
    }

    public Conversation AddNestedConversation() throws ConversationException{
        if(this.nestedIds==null){
            this.nestedIds = new LinkedList<>();
        }
        Conversation conv = ConversationManager.getInstance().createNestedConversation(this.conversationID);
        this.nestedIds.add(conv.getId());
        return conv;
    }

    public Conversation AddNestedConversation(long TimeOutPeriod) throws ConversationException{
        Conversation conv = this.AddNestedConversation();
        conv.setTimeOutPeriod(TimeOutPeriod);
        conv.setTimeOut();
        return conv;
    }


    void setParent(String parentId){
        this.parentId = parentId;
    }

    void setTimeOut(){
        if(this.parentId != null){
            try {
                ConversationManager.getInstance().getConversation(parentId).setTimeOut();
            } catch (ConversationException e){
                e.printStackTrace();
            }
        }
        this.timeOutTime = System.currentTimeMillis() + timeOutPeriod;
    }
    void setTimeOutPeriod(long timeOutPeriod){
        this.timeOutPeriod = timeOutPeriod;
    }
    long getTimeOut(){
        return this.timeOutTime;
    }
    void setValue(String name, Object obj){
        this.scopedItems.put(name,obj);
    }
    Object getValue(String name)throws ConversationException{
        if(this.scopedItems.containsKey(name)){
            return this.scopedItems.get(name);
        }else{
            throw new ConversationException("No value with given name in this scope");
        }
    }
    void setState(ConversationState state){
        this.state = state;
    }

    String getStateAsString(){
        return this.state.toString();
    }

    String getId(){
        return this.conversationID;
    }
    public Conversation begin() throws ConversationException{
        this.state.beginAction(this);
        return this;
    }
    public Conversation end() throws ConversationException{
        if(this.nestedIds !=null) {
            for (String id : this.nestedIds) {
                try {
                    ConversationManager.getInstance().getConversation(id).end();
                } catch (ConversationException e) {
                    e.printStackTrace();
                }
                //nie wiem czy to powinno zostać...
                //this.nestedIds.remove(id);
            }
        }

        this.state.endAction(this);
        return this;
    }
    Conversation endRequest(){
        if(this.nestedIds !=null) {
            for (String id : this.nestedIds) {
                try {
                    ConversationManager.getInstance().getConversation(id).endRequest();
                } catch (ConversationException e) {
                    e.printStackTrace();
                }
            }
        }
        this.state.endOfRequestAction(this);
        return this;

//    System.out.println("manual conv delete");
//    ConversationManager.getInstance().removeConversation(this.conversationID);
//        else
//            System.out.println("short - nie usuwam");
//        if(this.state.toString().equals("shortRunning")){
//            ConversationManager.getInstance().removeConversation(this.conversationID);
//        }
    }
}