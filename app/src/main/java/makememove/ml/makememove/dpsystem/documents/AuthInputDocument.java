package makememove.ml.makememove.dpsystem.documents;

public class AuthInputDocument extends Document{
    private String userName;
    private String email;
    private String password;

    private Integer id = null;

    public AuthInputDocument(String userName, String email, String password) {
        super();
        this.userName=userName;
        this.email = email;
        this.password = password;
    }
    public AuthInputDocument(){
        super();
    }

    @Override
    public void setData(Document document) {
        this.id = ((AuthInputDocument) document).getId();
        sendNotification();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
