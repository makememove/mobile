package makememove.ml.makememove.datahandler;

import java.util.logging.Level;
import java.util.logging.Logger;

import makememove.ml.makememove.autentication.AuthInputpack;
import makememove.ml.makememove.autentication.AuthTokenpack;
import makememove.ml.makememove.user.Normal;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataHandler {

    private final Logger logger;
    private Retrofit retrofit ;
    private RetrofitAPI api;
    private Encryptor encryptor;

    private static DataHandler single_instance = null;

    private DataHandler()
    {
        logger=Logger.getLogger("mylogger");
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


    public void login(String email, String username, String password){
        Call<AuthTokenpack> call=api.login(new AuthInputpack(username,email,encryptor.encode(password)));
        call.enqueue(new Callback<AuthTokenpack>() {
            @Override
            public void onResponse(Call<AuthTokenpack> call, Response<AuthTokenpack> response) {
                setDefaultUser(response);
            }

            @Override
            public void onFailure(Call<AuthTokenpack> call, Throwable t) {

            }
        });
    }

    public void signup(String email, String username, String password){
        Call<AuthTokenpack> call=api.signup(new AuthInputpack(username,email,encryptor.encode(password)));
        call.enqueue(new Callback<AuthTokenpack>() {
            @Override
            public void onResponse(Call<AuthTokenpack> call, Response<AuthTokenpack> response) {
                setDefaultUser(response);
            }

            @Override
            public void onFailure(Call<AuthTokenpack> call, Throwable t) {

            }
        });
    }
        private void setDefaultUser(Response<AuthTokenpack> response){
            logger.log(Level.INFO,response.body().getToken());
            User.getInstance().setToken(response.body().getToken());
        }
}
