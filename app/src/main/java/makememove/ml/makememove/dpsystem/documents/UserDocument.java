package makememove.ml.makememove.dpsystem.documents;

import makememove.ml.makememove.user.User;

public class UserDocument {
    private User user;

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
}
