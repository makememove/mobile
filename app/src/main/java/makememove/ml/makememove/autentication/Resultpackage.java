package makememove.ml.makememove.autentication;

public class Resultpackage {
    private String error;
    private String token;


    public Resultpackage(String error, String token) {
        this.error = error;
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
