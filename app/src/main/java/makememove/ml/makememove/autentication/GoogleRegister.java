package makememove.ml.makememove.autentication;

public class GoogleRegister implements Registers {

    @Override
    public boolean registrate(String email, String username, String password) {
        return false;
    }

    @Override
    public boolean makeAutoLoginConditions(String email, String username, String password) {
        return false;
    }
}
