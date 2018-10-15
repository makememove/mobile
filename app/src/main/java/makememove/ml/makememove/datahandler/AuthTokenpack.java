package makememove.ml.makememove.datahandler;

public class AuthTokenpack {
    private String token;


    public AuthTokenpack(String error, String token) {

        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
