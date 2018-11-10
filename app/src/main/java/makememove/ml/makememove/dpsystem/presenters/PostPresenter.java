package makememove.ml.makememove.dpsystem.presenters;

import android.util.Log;

import java.io.IOException;

import makememove.ml.makememove.dpsystem.documents.Document;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostPresenter extends Presenter implements Callback {

    public PostPresenter(){
        super();
    }
    public PostPresenter(Document doc){
        this.document = doc;
    }

    public void setDocument(Document doc){
        this.document = doc;
    }

    public void postPreferredSport(String token, int pos){
        Call call = api.postPreferredUserSport(token,pos);
        call.enqueue(this);
    }

    public void unpostPreferredSport(String token, int pos){
        Call call = api.unpostPreferredUserSport(token,pos);
        call.enqueue(this);
    }
    public void postEvent(String token, EventDocument doc){
        Call call = api.createEvent(token,doc);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call call, Response response) {
        if(response.isSuccessful()){
            Log.d("Successful","Successful");
        }
        else {
            try {
                Log.d("Not Successful", "Not Successful" + response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        System.out.printf("Failure occured in PostPresenter class!");
    }

}
