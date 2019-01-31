public class ConversationLongRunningState implements ConversationState {
    private Conversation conv;
    ConversationLongRunningState(Conversation conv){
        this.conv = conv;
    }

    @Override
    public void beginAction() throws ConversationException{
        throw new ConversationException("Long-running conversation already active");
    }

    @Override
    public void endAction() throws ConversationException {
        conv.setState("shortRunning");
    }

    @Override
    public String toString() {
        return "longRunning";
    }
}
