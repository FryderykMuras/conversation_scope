public class ConversationLongRunningState implements ConversationState {

    @Override
    public void beginAction(Conversation conv) throws ConversationException{
        throw new ConversationException("Long-running conversation already active");
    }

    @Override
    public void endAction(Conversation conv) throws ConversationException {
        conv.setState(StateFactory.getShortRunningState());
    }

    @Override
    public String toString() {
        return "longRunning";
    }
    @Override
    public void endOfRequestAction(Conversation conv){
        ;
    }
}
