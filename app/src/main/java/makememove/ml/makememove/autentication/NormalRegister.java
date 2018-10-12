package makememove.ml.makememove.autentication;

import java.util.logging.Level;
import java.util.logging.Logger;

import makememove.ml.makememove.datahandlers.users.AutenticationAPI;
import makememove.ml.makememove.datahandlers.users.Encryptor;
import makememove.ml.makememove.datahandlers.users.TokenSaver;
import makememove.ml.makememove.user.Normal;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NormalRegister implements Registers {


    @Override
    public void registrate(String email, String username, String password) {
        final Logger logger=Logger.getLogger("mylogger");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AutenticationAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        AutenticationAPI api = retrofit.create(AutenticationAPI.class);
        Encryptor encryptor = new Encryptor();
        Call<Resultpackage> call=api.register(new Inputpackage(username,email,encryptor.encode(password)));
        call.enqueue(new Callback<Resultpackage>() {
            @Override
            public void onResponse(Call<Resultpackage> call, Response<Resultpackage> response) {
                logger.log(Level.INFO,response.body().getToken());
                User.getInstance().setInstance(new Normal());
                User.getInstance().setToken(response.body().getToken());
                makeAutoLoginConditions();
            }

            @Override
            public void onFailure(Call<Resultpackage> call, Throwable t) {

            }
        });
        //TODO User adatainak lekérése regisztrálás után
    }

    @Override
    public void makeAutoLoginConditions() {
        TokenSaver ts = new TokenSaver();
        ts.saveToken();
    }
}
