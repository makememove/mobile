package makememove.ml.makememove;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    String BASE_URL ="https://makememove-dev.herokuapp.com/api/";

    @GET("events")
    Call<List<Event>> getEvent();
}
