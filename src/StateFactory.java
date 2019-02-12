public class StateFactory {
    private static StateFactory INSTANCE;
    private static ConversationState LongRunningState;
    private static ConversationState ShortRunningState;

    public static StateFactory getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StateFactory();
        }
        return INSTANCE;
    }
    public static ConversationState getShortRunningState() {
        if(ShortRunningState == null) {
            ShortRunningState = new ConversationShortRunningState();
        }
        return ShortRunningState;
    }
    public static ConversationState getLongRunningState() {
        if(LongRunningState == null) {
            LongRunningState = new ConversationLongRunningState();
        }
        return LongRunningState;
    }
}
