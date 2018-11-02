package makememove.ml.makememove.datahandler;

import java.util.ArrayList;
import java.util.List;

import makememove.ml.makememove.eventsystem.EventDocument;
import makememove.ml.makememove.user.Sport;
import makememove.ml.makememove.user.SportList;
import makememove.ml.makememove.user.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    @GET("users/me")
    Call<UserPack> getUserDetails(@Header("Authorization") String token);

    @Headers("Content-type: application/json")
    @GET("sports/all")
    Call<SportList> getAllSports(@Header("Authorization") String token);

    @Headers("Content-type: application/json")
    @GET("sports")
    Call<SportList> getUserPreferredSports(@Header("Authorization") String token);

    @Headers("Content-type: application/json")
    @POST("sports/follow/{sportID}")
    Call<AuthInputpack> addPreferredUserSport(@Header("Authorization") String token, @Path("sportID") int sportID);

    @Headers("Content-type: application/json")
    @GET("events?sportID={ID}")
    Call<List<EventDocument>> getSportEvents(@Header("Authorization") String token, @Path("ID") int sportID);
}
