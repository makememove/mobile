package makememove.ml.makememove.autentication;

public interface Registers {
    public void registrate(String email, String username, String password);
    public void makeAutoLoginConditions(String email, String username, String password);
}
