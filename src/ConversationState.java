interface ConversationState {
    void beginAction() throws ConversationException;
    void endAction() throws ConversationException;
    void endOfRequestAction();
    String toString();
}
