package makememove.ml.makememove.user;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import makememove.ml.makememove.dpsystem.documents.FriendDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Friend;

public class User {
    private String token;
    private int id;
    private int experience;
    private int level;
    private GENDER gender;
    private String picture;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Date birthday;
    private float popularity;
    private UserType userType;
    private String userString;
    private int type;
    private List<Friend> friends;

/////Getter/Setters////////
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public int getExperience() {
        return experience;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public GENDER getGender() {
        return gender;
    }
    public void setGender(GENDER gender) {
        this.gender = gender;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public float getPopularity() {
        return popularity;
    }
    public void setPopularity(float popularity) { this.popularity = popularity; }
    public UserType getUserType() { return userType; }
    public void setUserType(UserType userType) { this.userType = userType; }
    public void setData(User user){ }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    //////////////////////////////////

    private static User INSTANCE = new User();

    private User() {

    }

    public static void setEveryThing(User otherUser){
        String token = INSTANCE.getToken();
        String string = INSTANCE.getUserString();
        UserType userType = INSTANCE.getUserType();
        INSTANCE = otherUser;
        INSTANCE.setUserString(string);
        INSTANCE.setToken(token);
        INSTANCE.setUserType(userType);

        INSTANCE.setLevel(0);
        INSTANCE.setExperience(0);
    }

    public static User getInstance() {
        return INSTANCE;
    }

    public String getUserString() {
        return userString;
    }

    public void setUserString(String userString) {
        this.userString = userString;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}


