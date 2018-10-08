package makememove.ml.makememove.autentication;

public interface Registers {
    public boolean registrate(String email, String username, String password);
    public boolean makeAutoLoginConditions(String email, String username, String password);
}
