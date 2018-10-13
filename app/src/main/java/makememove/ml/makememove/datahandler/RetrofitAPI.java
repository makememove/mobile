package makememove.ml.makememove.datahandler;

import makememove.ml.makememove.autentication.AuthInputpack;
import makememove.ml.makememove.autentication.AuthTokenpack;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/*
Retrofit API for manage users
 */
public interface RetrofitAPI {
    String BASE_URL ="https://makememove-dev.herokuapp.com/api/";

    @Headers("Content-type: application/json")
    @POST("auth/login")
    Call<AuthTokenpack> login(@Body AuthInputpack body);

    @Headers("Content-type: application/json")
    @POST("auth/register")
    Call<AuthTokenpack> signup(@Body AuthInputpack body);

    @Headers("Content-type: application/json")
    @GET("/user/me")
    Call<User> getUserDetails(@Body String token);
}
