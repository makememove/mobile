package makememove.ml.makememove.dpsystem.documents;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.adapters.UserItem;

public class FriendDocument extends Document{
    private List<UserItem> requests;
    private List<UserItem> sent;

    public FriendDocument(){
        super();
        requests = new ArrayList<UserItem>();
        sent = new ArrayList<UserItem>();
    }

    @Override
    public void setData(Document document){
        requests = ((FriendDocument) document).getRequests();
        sent = ((FriendDocument) document).getSent();
        sendNotification();
    }

    public List<UserItem> getRequests() {
        return requests;
    }

    public void setRequests(List<UserItem> requests) {
        this.requests = requests;
    }

    public List<UserItem> getSent() {
        return sent;
    }

    public void setSent(List<UserItem> sent) {
        this.sent = sent;
    }
}
