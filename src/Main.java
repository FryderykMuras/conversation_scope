public class Main {
    static ConversationManager a;
    public static void main(String[] args){
        ConversationManager.getInstance().getConversation(1).setValue("name", new StringBuffer("aaaa"));
        ConversationManager.getInstance().getConversation(2).setValue("name", "bbbb");

        StringBuffer nameA=(StringBuffer)ConversationManager.getInstance().getConversation(1).getReference("name");
        String nameB=(String)ConversationManager.getInstance().getConversation(2).getReference("name");

        nameA.append(" cccc");

        System.out.println(nameA);
        System.out.println(nameB);

        StringBuffer nameC=(StringBuffer)ConversationManager.getInstance().getConversation(1).getReference("name");
        System.out.println(nameC);
    }
}
