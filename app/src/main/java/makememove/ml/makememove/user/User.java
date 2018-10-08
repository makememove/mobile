package makememove.ml.makememove.user;

import java.util.Date;

public class User {
    protected String token;
    protected int experience;
    protected int level;
    protected GENDER gender;
    protected String picture;
    protected String firstName;
    protected String lastName;
    protected String userName;
    protected String email;
    protected Date birthday;
    protected float popularity;

    private static  User INSTANCE = null;

    User() {}

    public void setInstance(User instance) {
        INSTANCE = instance;
    }

    public static User getInstance() {
        return INSTANCE;
    }
}


