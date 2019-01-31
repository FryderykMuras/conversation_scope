import java.util.UUID;

public class Main {
    public static void main(String[] args)throws NoConversationException{
        //ConversationManager.getInstance().getConversation(1).setValue("name", new StringBuffer("aaaa"));
        //ConversationManager.getInstance().getConversation(2).setValue("name", "bbbb");

        //StringBuffer nameA=(StringBuffer)ConversationManager.getInstance().getConversation(1).getReference("name");
        //String nameB=(String)ConversationManager.getInstance().getConversation(2).getReference("name");
        //ConversationManager.getInstance().getConversation(1).begin();
        //ConversationManager.getInstance().getConversation(1).endRequest();
        //nameA.append(" cccc");
        String id = testSet("1");
        Conversation conv = ConversationManager.getInstance().getConversation(id);
        System.out.println(conv.getReference("name1"));
        System.out.println(conv.getReference("name2"));
        conv.end();
        conv.endRequest();

        Conversation conv1 = ConversationManager.getInstance().getConversation(id);
        System.out.println(conv1.getReference("name1"));
        System.out.println(conv1.getReference("name2"));
        //StringBuffer nameC=(StringBuffer)ConversationManager.getInstance().getConversation(1).getReference("name");
        //System.out.println(nameC);

        final String uuid = UUID.randomUUID().toString().replace("-", "");
        System.out.println("uuid = " + uuid);
    }
    public static String testSet(String id){
        Conversation conv = ConversationManager.getInstance().getConversation();
        conv.setValue("name1", new StringBuffer("aaaa"));
        conv.setValue("name2", new StringBuffer("bbbb"));
        conv.begin();
        String ret = conv.getId();
        conv.endRequest();

        return ret;
    }
}
