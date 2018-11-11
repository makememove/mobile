package makememove.ml.makememove.dpsystem.presenters;

import android.util.Log;

import java.io.IOException;

import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.EventListDocument;
import makememove.ml.makememove.dpsystem.documents.FriendDocument;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendPresenter extends Presenter implements Callback<FriendDocument> {
    public FriendPresenter(){
        super();
    }

    public FriendPresenter(FriendDocument doc){
        super();
        this.document = doc;
    }

    public void setDocument(FriendDocument doc){
        this.document = doc;
    }

    public void getRecievedFriendRequests(String token){
        Call call = api.getRecievedFriendRequests(token);
        call.enqueue(this);
    }

    public void getSentFriendsRequests(String token){
        Call call = api.getSentFriendsRequests(token);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<FriendDocument> call, Response<FriendDocument> response) {
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
