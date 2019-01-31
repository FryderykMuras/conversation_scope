public class ConversationManager{
    private static ConversationManager INSTANCE;

    private ConversationManager() {
    }

    public static ConversationManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ConversationManager();
        }

        return INSTANCE;
    }

}