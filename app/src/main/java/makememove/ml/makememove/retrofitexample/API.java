package makememove.ml.makememove.retrofitexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {
    String BASE_URL ="https://makememove-dev.herokuapp.com/api/";

    @GET("events")
    Call<List<Event>> getEvent();

    @GET("events/{id}")
    Call<Description> getDescription(@Path("id") int id);
}
