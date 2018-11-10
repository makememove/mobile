package makememove.ml.makememove.dpsystem.api;

import java.util.List;

import makememove.ml.makememove.dpsystem.documents.AuthInputDocument;
import makememove.ml.makememove.dpsystem.documents.EventListDocument;
import makememove.ml.makememove.dpsystem.documents.TokenDocument;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.UserDocument;
import makememove.ml.makememove.dpsystem.documents.SportListDocument;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/*
Retrofit API for manage users
 */
public interface RetrofitAPI {
    String BASE_URL ="https://makememove-dev.herokuapp.com/api/";

    @Headers("Content-type: application/json")
    @POST("auth/login")
    Call<TokenDocument> login(@Body AuthInputDocument body);

    @Headers("Content-type: application/json")
    @POST("auth/register")
    Call<TokenDocument> signup(@Body AuthInputDocument body);

    @Headers("Content-type: application/json")
    @GET("users/me")
    Call<UserDocument> getUserDetails(@Header("Authorization") String token);

    @Headers("Content-type: application/json")
    @GET("sports/all")
    Call<SportListDocument> getAllSports(@Header("Authorization") String token);

    @Headers("Content-type: application/json")
    @GET("sports")
    Call<SportListDocument> getUserPreferredSports(@Header("Authorization") String token);

    @Headers("Content-type: application/json")
    @POST("sports/follow/{sportID}")
    Call<AuthInputDocument> postPreferredUserSport(@Header("Authorization") String token, @Path("sportID") int sportID);

    @Headers("Content-type: application/json")
    @POST("sports/unfollow/{sportID}")
    Call<AuthInputDocument> unpostPreferredUserSport(@Header("Authorization") String token, @Path("sportID") int sportID);

    @Headers("Content-type: application/json")
    @GET("events")
    Call<EventListDocument> getSportEvents(@Header("Authorization") String token, @Query("sportId") int sportID);
}
