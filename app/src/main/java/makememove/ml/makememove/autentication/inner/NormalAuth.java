package makememove.ml.makememove.autentication.inner;

import makememove.ml.makememove.datahandler.DataHandler;
import makememove.ml.makememove.datahandler.TokenHandler;


public class NormalAuth implements AuthwithInner {
    private DataHandler dh=DataHandler.getInstance();

    @Override
    public void login(String email, String username, String password) {

        dh.login(email,username,password);
        makeAutoLoginConditions();
        //TODO User adatainak lekérése login után
    }

    @Override
    public void signup(String email, String username, String password) {
        dh.signup(email,username,password);
        makeAutoLoginConditions();

        //TODO User adatainak lekérése regisztrálás után
    }


    private void makeAutoLoginConditions() {
        TokenHandler ts = new TokenHandler();
        ts.saveToken();
    }
}
