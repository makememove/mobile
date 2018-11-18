package makememove.ml.makememove.dpsystem.presenters;

import android.util.Log;

import makememove.ml.makememove.dpsystem.documents.Document;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.EventListDocument;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListPresenter extends Presenter implements Callback<EventListDocument> {
    public EventListPresenter(){
        super();
    }

    public EventListPresenter(EventListDocument doc){
        super();
        this.document = doc;
    }

    public void getSportEvents(String token, int sportID){
        Call call = api.getSportEvents(token,sportID);
        call.enqueue(this);
    }

    public void getMyEvents(String token, int userid){
        Call call = api.getmyevents(token,userid);
        call.enqueue(this);
    }

    public void getUnfinEvents(String token){
        Call call = api.getunfinishedevents(token);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<EventListDocument> call, Response<EventListDocument> response) {
        if(response.isSuccessful()){
            document.setData(response.body());
            Log.d("válasz:","siker");
        }else Log.d("válasz:","nem siker");
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.d("válasz:","failure");
    }
}
