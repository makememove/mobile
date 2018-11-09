package makememove.ml.makememove.dpsystem.documents;

public class TokenDocument {
    private String token;


    public TokenDocument(String error, String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
