package makememove.ml.makememove.autentication;

import java.util.logging.Level;
import java.util.logging.Logger;

import makememove.ml.makememove.datahandlers.users.AutenticationAPI;
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

        Call<Resultpackage> call=api.register(new Inputpackage(username,email,password));
        call.enqueue(new Callback<Resultpackage>() {
            @Override
            public void onResponse(Call<Resultpackage> call, Response<Resultpackage> response) {
                logger.log(Level.INFO,response.body().getToken());

            }

            @Override
            public void onFailure(Call<Resultpackage> call, Throwable t) {

            }
        });
    }

    @Override
    public void makeAutoLoginConditions(String email, String username, String password) {

    }
}
