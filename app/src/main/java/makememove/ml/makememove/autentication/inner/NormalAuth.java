package makememove.ml.makememove.autentication.inner;

import javax.security.auth.callback.Callback;

import makememove.ml.makememove.datahandler.DataHandler;
import makememove.ml.makememove.datahandler.TokenHandler;


public class NormalAuth implements AuthwithInner {
    private DataHandler dh=DataHandler.getInstance();

    @Override
    public void login(String email, String username, String password,retrofit2.Callback callback) {
        dh.login(email,username,password,callback);
    }

    @Override
    public void signup(String email, String username, String password, retrofit2.Callback callback) {
        dh.signup(email,username,password,callback);
    }

    @Override
    public void setUserData(retrofit2.Callback callback) {
        dh.setUserData(callback);
    }
}
