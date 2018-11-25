package makememove.ml.makememove.dpsystem.presenters;

import android.util.Log;

import java.io.IOException;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.eventfragments.EventDetailsFragment;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.EventDocumentContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static makememove.ml.makememove.activities.UserActivity.fragmentManager;

public class EventPresenter extends Presenter implements Callback<EventDocumentContainer> {
    public EventPresenter(){
        super();
    }

    public EventPresenter(EventDocumentContainer doc){
        super();
        this.document = doc;
    }

    public void setDocument(EventDocumentContainer doc){
        this.document = doc;
    }

    public void getEvent(String token,int eventid){
        Log.d("token","token "+token);
        Call call = api.getEvent(token,eventid);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<EventDocumentContainer> call, Response<EventDocumentContainer> response) {
        if(response.isSuccessful()){
            document.setData(response.body());
            Log.d("sikeres","sikeres222 ");

        }
        else {
            try {
                Log.d("failure","failure"+response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.d("onFailure","onFailure");
    }
}
