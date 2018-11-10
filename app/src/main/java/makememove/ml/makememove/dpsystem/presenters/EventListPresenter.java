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
        Log.d("A sportid: ",Integer.toString(sportID));
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<EventListDocument> call, Response<EventListDocument> response) {
        if(response.isSuccessful()){
            document.setData(response.body());
            for (EventDocument doc:response.body().getEvents()) {
                Log.d("Az esemény neve: ",doc.getTitle());
            }
            //Log.d("onResponse eventlist","successful");
        }
        else
            Log.d("onResponse","failure");
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.d("onFailure","failure");
        System.out.printf("Failure occured in PostPresenter class!");
    }
}
