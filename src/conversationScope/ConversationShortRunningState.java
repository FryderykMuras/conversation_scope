package conversationScope;

class ConversationShortRunningState implements ConversationState{
    @Override
    public void beginAction(Conversation conv) throws ConversationException{
        conv.setState(StateFactory.getLongRunningState());
    }

    @Override
    public void endAction(Conversation conv) throws ConversationException {
        throw new ConversationException("Short-running conversation already active");
    }

    @Override
    public String toString() {
        return "shortRunning";
    }

    @Override
    public void endOfRequestAction(Conversation conv){
        ConversationManager.getInstance().removeConversation(conv.getId());
        System.out.println("ShortRunningConversation ended");
    }
}
