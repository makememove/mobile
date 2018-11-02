package makememove.ml.makememove.datahandler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import makememove.ml.makememove.user.Sport;
import makememove.ml.makememove.user.SportList;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataHandler {

    private Retrofit retrofit ;
    private RetrofitAPI api;
    private Encryptor encryptor;

    private static DataHandler single_instance = null;

    private DataHandler()
    {
        retrofit = new Retrofit.Builder().baseUrl(RetrofitAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(RetrofitAPI.class);
        encryptor = new Encryptor();
    }

    public static DataHandler getInstance()
    {
        if (single_instance == null)
            single_instance = new DataHandler();

        return single_instance;
    }

    public static void setToken(Response<AuthTokenpack > response){
        User.getInstance().setToken(response.body().getToken());
        System.out.printf("setToken Token: "+User.getInstance().getToken()+"\n");
        TokenHandler ts = new TokenHandler();
        ts.saveToken();
    }

    public void login(String email, String username, String password, retrofit2.Callback callback){
        Call<AuthTokenpack> call=api.login(new AuthInputpack(username,email,encryptor.encode(password)));
        call.enqueue(callback);
    }

    public void signup(String email, String username, String password, retrofit2.Callback callback){
        Call<AuthTokenpack> call=api.signup(new AuthInputpack(username,email,encryptor.encode(password)));
        call.enqueue(callback);
    }

    public void setUserData(retrofit2.Callback callback){
        Call<UserPack> call = api.getUserDetails(User.getInstance().getToken());
        call.enqueue(callback);
    }

    public void getAllSports(String token, retrofit2.Callback callback){
        Call<SportList> call = api.getAllSports(token);
        call.enqueue(callback);
    }

    public void addPreferredSport(String token, int pos, retrofit2.Callback callback){
        Call<AuthInputpack> call = api.addPreferredUserSport(token,pos);
        call.enqueue(callback);
    }

    public void getUserPreferredSports(String token, Callback callback){
        Call<SportList> call = api.getUserPreferredSports(token);
        call.enqueue(callback);
    }
}
