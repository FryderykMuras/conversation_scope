interface ConversationState {
    void beginAction(Conversation conv) throws ConversationException;
    void endAction(Conversation conv) throws ConversationException;
    void endOfRequestAction(Conversation conv);
    String toString();
}
