import java.util.Map;

public class TimeoutController implements Runnable,Visitor{
    private ConversationManager manager;
    private Map<String,Conversation> conversations;
    private long currentTime;
    private boolean doStop = false;
    TimeoutController(ConversationManager m){
        this.manager = m;
        currentTime = System.currentTimeMillis();
    }

    synchronized void doStop() {
        this.doStop = true;
    }

    private synchronized boolean keepRunning() {
        return !this.doStop;
    }

    @Override
    public void visit(Conversation conv) {
        if((conv.getTimeOut()-System.currentTimeMillis()) < 0){
            System.out.println("Usuwanie konwersacji po timeout");
            this.manager.removeConversation(conv.getId());
        }
    }

    @Override
    public void run(){
        while(keepRunning()) {
            this.conversations=manager.getConversationsMap();
            // keep doing what this thread should do.
            System.out.println("Running");
            currentTime = System.currentTimeMillis();
            for (Map.Entry<String, Conversation> entry : conversations.entrySet()) {
                visit(entry.getValue());
            }

           try {
                Thread.sleep(1L * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
