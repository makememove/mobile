package makememove.ml.makememove.autentication.inner;

import javax.security.auth.callback.Callback;

import retrofit2.Retrofit;

public interface AuthwithInner {
    public void login (String email, String username, String password, retrofit2.Callback callback) ;
    public void signup(String email, String username, String password,retrofit2.Callback callback);
    public void setUserData(retrofit2.Callback callback);
}
