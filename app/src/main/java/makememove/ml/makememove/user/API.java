package makememove.ml.makememove.user;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface API {
    String BASE_URL ="https://makememove-dev.herokuapp.com/api/";

    @GET("/user/me")
    Call<User> getUserDetails(@Body String token);
}
