package makememove.ml.makememove.datahandler;

import java.util.List;

import makememove.ml.makememove.eventsystem.EventDocument;
import makememove.ml.makememove.user.Sport;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


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
    @GET("/users/me")
    Call<User> getUserDetails(@Body String token);

    @Headers("Content-type: application/json")
    @GET("/sports/all")
    Call<List<Sport>> getAllSports(@Body String token);

    @Headers("Content-type: application/json")
    @GET("/sports")
    Call<List<Sport>> getUserPreferredSports(@Body String token);

    @Headers("Content-type: application/json")
    @POST("/sports/follow/{sportID}")
    Call<AuthTokenpack> addPreferredUserSport(@Body String token, @Path("sportID") int sportID);

    @Headers("Content-type: application/json")
    @GET("/events?sportID={ID}")
    Call<List<EventDocument>> getSportEvents(@Body String token, @Path("ID") int sportID);
}
