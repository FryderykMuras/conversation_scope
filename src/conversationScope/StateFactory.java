package conversationScope;

class StateFactory {
    private static ConversationState LongRunningState;
    private static ConversationState ShortRunningState;

    static ConversationState getShortRunningState() {
        if(ShortRunningState == null) {
            ShortRunningState = new ConversationShortRunningState();
        }
        return ShortRunningState;
    }
    static ConversationState getLongRunningState() {
        if(LongRunningState == null) {
            LongRunningState = new ConversationLongRunningState();
        }
        return LongRunningState;
    }
}
