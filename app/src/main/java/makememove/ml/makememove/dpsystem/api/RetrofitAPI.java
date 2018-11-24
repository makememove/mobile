package makememove.ml.makememove.dpsystem.api;

import java.util.Date;
import java.util.List;

import makememove.ml.makememove.dpsystem.documents.AuthInputDocument;
import makememove.ml.makememove.dpsystem.documents.EventDocumentContainer;
import makememove.ml.makememove.dpsystem.documents.EventListDocument;
import makememove.ml.makememove.dpsystem.documents.FriendDocument;
import makememove.ml.makememove.dpsystem.documents.MemberDocument;
import makememove.ml.makememove.dpsystem.documents.RankDocument;
import makememove.ml.makememove.dpsystem.documents.NotificationDocument;
import makememove.ml.makememove.dpsystem.documents.TeamDocument;
import makememove.ml.makememove.dpsystem.documents.TokenDocument;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.UserDocument;
import makememove.ml.makememove.dpsystem.documents.SportListDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.FinishedRank;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Team;
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
    Call<UserDocument>  getUserDetails(@Header("Authorization") String token);

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

    @Headers("Content-type: application/json")
    @POST("events/create")
    Call<AuthInputDocument> createEvent(@Header("Authorization") String token, @Body EventDocument body);

    @Headers("Content-type: application/json")
    @POST("users/friends/requests/deny/{userId}")
    Call<AuthInputDocument> denyFriendRequest(@Header("Authorization") String token, @Path("userId") int id);

    @Headers("Content-type: application/json")
    @POST("users/friends/requests/accept/{userId}")
    Call<AuthInputDocument> acceptFriendRequest(@Header("Authorization") String token, @Path("userId") int id);

    @Headers("Content-type: application/json")
    @GET("users/friends/requests")
    Call<FriendDocument> getRecievedFriendRequests(@Header("Authorization") String token);

    @Headers("Content-type: application/json")
    @GET("users/friends/requests/sent")
    Call<FriendDocument> getSentFriendsRequests(@Header("Authorization") String token);

    @Headers("Content-type: application/json")
    @POST("users/friends/delete/{userId}")
    Call<AuthInputDocument> deleteFriend(@Header("Authorization") String token, @Path("userId") int id);

    @Headers("Content-type: application/json")
    @GET("sports/ranklist/{sportId}")
    Call<RankDocument> getRankList(@Header("Authorization") String token, @Path("sportId") int sportID, @Query("limit") int limit);

    @Headers("Content-type: application/json")
    @GET("users/notifications")
    Call<NotificationDocument> getNotifications(@Header("Authorization") String token);

    @Headers("Content-type: application/json")
    @POST("users/notifications/delete/{notificationId}")
    Call<EventListDocument> deleteNotification(@Header("Authorization") String token, @Path("notificationId") int notificationId);

    @Headers("Content-type: application/json")
    @GET("events/{eventId}")
    Call<EventDocumentContainer> getEvent(@Header("Authorization") String token, @Path("eventId") int eventId);


    @Headers("Content-type: application/json")
    @GET("events/{eventId}")
    Call<TeamDocument> getTeams(@Header("Authorization") String token, @Path("eventId") int eventId);

    @Headers("Content-type: application/json")
    @POST("teams/create")
    Call<AuthInputDocument> createTeam(@Header("Authorization") String token, @Body Team team);

    @Headers("Content-type: application/json")
    @POST("users/friends/request/{userId}")
    Call<AuthInputDocument> sendFriendRequest(@Header("Authorization") String token, @Path("userId") int userId);

    @Headers("Content-type: application/json")
    @GET("teams/{teamId}")
    Call<MemberDocument> getTeamMembers(@Header("Authorization") String token, @Path("teamId") int teamId);

    @Headers("Content-type: application/json")
    @POST("teams/join/{teamID}")
    Call<AuthInputDocument> joinTeam(@Header("Authorization") String token, @Path("teamID") int userId);

    @Headers("Content-type: application/json")
    @POST("teams/leave/{teamID}")
    Call<AuthInputDocument> leaveTeam(@Header("Authorization") String token, @Path("teamID") int userId);

    @Headers("Content-type: application/json")
    @GET("events")
    Call<EventListDocument> getmyevents(@Header("Authorization") String token, @Query("creatorId") int userId);

    @Headers("Content-type: application/json")
    @GET("events/mine")
    Call<EventListDocument> getunfinishedevents(@Header("Authorization") String token);

    @Headers("Content-type: application/json")
    @GET("events/mine")
    Call<EventListDocument> getfinishedevents(@Header("Authorization") String token,@Query("closed") int closed);

    @Headers("Content-type: application/json")
    @POST("users/me/edit")
    Call<AuthInputDocument> modifyProfile(@Header("Authorization") String token, @Query("firstName") String firstName, @Query("lastName") String lastName, @Query("gender") String gender, @Query("birthday") String birthday);

    @Headers("Content-type: application/json")
    @POST("events/edit/{eventId}")
    Call<AuthInputDocument> modifyEvent(@Header("Authorization") String token,@Path("eventId") int eventId, @Body EventDocument doc);

    @Headers("Content-type: application/json")
    @POST("events/close/{eventId}")
    Call<AuthInputDocument> closeEvent(@Header("Authorization") String token, @Path("eventId") int eventId, @Body FinishedRank ranks);

    @Headers("Content-type: application/json")
    @GET("events")
    Call<EventListDocument> geteventswithfilter(@Header("Authorization") String token,@Query("location") String location,@Query("title") String title,@Query("lowestSkillPoint") Integer lowestSkillPoint,@Query("highestSkillPoint") Integer highestSkillPoint,@Query("public") Integer visibility,@Query("closed") Integer closed,@Query("date") String date);

}
