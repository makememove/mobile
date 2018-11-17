package makememove.ml.makememove.dpsystem.presenters;

import android.util.Log;

import java.io.IOException;

import makememove.ml.makememove.dpsystem.documents.EventDocument;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventPresenter extends Presenter implements Callback<EventDocument> {
    public EventPresenter(){
        super();
    }

    public EventPresenter(EventDocument doc){
        super();
        this.document = doc;
    }

    public void setDocument(EventDocument doc){
        this.document = doc;
    }

    public void getEvent(String token,int eventid){
        Call call = api.getEvent(token,eventid);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<EventDocument> call, Response<EventDocument> response) {
        if(response.isSuccessful()){
            document.setData(response.body());
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
