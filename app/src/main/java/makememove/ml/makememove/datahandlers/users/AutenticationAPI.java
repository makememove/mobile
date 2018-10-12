package makememove.ml.makememove.datahandlers.users;




import makememove.ml.makememove.autentication.Inputpackage;
import makememove.ml.makememove.autentication.Resultpackage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/*
Retrofit API for manage users
 */
public interface AutenticationAPI {
    String BASE_URL ="https://makememove-dev.herokuapp.com/api/";

    @Headers("Content-type: application/json")
    @POST("auth/login")
    Call<Resultpackage> login(@Body Inputpackage body);

    @Headers("Content-type: application/json")
    @POST("auth/register")
    Call<Resultpackage> register(@Body Inputpackage body);
}
