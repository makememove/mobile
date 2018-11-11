package makememove.ml.makememove.dpsystem.documents;

import makememove.ml.makememove.user.User;

public class UserDocument extends Document {
    private User user;

    public UserDocument(){ super();}
    public UserDocument(User user) {
        super();
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setData(Document document) {
        this.user = ((UserDocument) document).getUser();
        sendNotification();
    }
}
