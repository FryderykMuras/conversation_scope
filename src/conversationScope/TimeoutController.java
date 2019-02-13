package conversationScope;

import java.util.Map;

class TimeoutController implements Runnable,Visitor{
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
    public synchronized void visit(Conversation conv) {
        if(conv.getStateAsString().equals("longRunning") && (conv.getTimeOut()-System.currentTimeMillis()) < 0){
            System.out.println("Usuwanie konwersacji po timeout");
            try {
                conv.end().endRequest();
            } catch (ConversationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run(){
        while(keepRunning()) {
            this.conversations=manager.getConversationsMap();
            System.out.println("Running");
            currentTime = System.currentTimeMillis();
            for (Map.Entry<String, Conversation> entry : conversations.entrySet()) {
                entry.getValue().accept(this);
            }

           try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
