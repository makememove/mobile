package makememove.ml.makememove.dpsystem.documents;

public class TokenDocument {
    private String token;
    private int type;


    public TokenDocument(String error, String token) {
        super();
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
