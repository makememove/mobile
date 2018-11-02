package makememove.ml.makememove.user;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

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
      //  String token = INSTANCE.getToken();
      //  INSTANCE = otherUser;
     //   INSTANCE.setToken(token);
        INSTANCE.setEmail(otherUser.getEmail());
    }

    public static User getInstance() {
        return INSTANCE;
    }


    /*
    public static void setInstance(User user){
        INSTANCE = user;
    }
*/

}


