public class ConversationShortRunningState implements ConversationState{
    private Conversation conv;
    ConversationShortRunningState(Conversation conv){
        this.conv = conv;
    }

    @Override
    public void beginAction() throws ConversationException{
        this.conv.setState("longRunning");
    }

    @Override
    public void endAction() throws ConversationException {
        throw new ConversationException("Short-running conversation already active");
    }

    @Override
    public String toString() {
        return "shortRunning";
    }

    @Override
    public void endOfRequestAction(){
        ConversationManager.getInstance().removeConversation(this.conv.getId());
        System.out.println("ShortRunningConversation ended");
    }
}
