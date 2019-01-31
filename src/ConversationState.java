interface ConversationState {
    void beginAction() throws ConversationException;
    void endAction() throws ConversationException;
    String toString();
}
