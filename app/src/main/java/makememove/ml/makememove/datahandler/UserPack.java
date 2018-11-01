package makememove.ml.makememove.datahandler;

import makememove.ml.makememove.user.User;

public class UserPack {
    private User user;

    public UserPack(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
