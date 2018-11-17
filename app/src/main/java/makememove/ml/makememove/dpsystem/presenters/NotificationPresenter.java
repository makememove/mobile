package makememove.ml.makememove.dpsystem.presenters;


import android.util.Log;

import java.io.IOException;

import makememove.ml.makememove.dpsystem.documents.NotificationDocument;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter extends Presenter implements Callback<NotificationDocument> {

    public NotificationPresenter(NotificationDocument doc) {
        super();
        this.document = doc;
    }

    public void setDocument(NotificationDocument doc){
        this.document = doc;
    }

    public void getNotifications(String token){
        Call call = api.getNotifications(token);
        call.enqueue(this);
    }



    @Override
    public void onResponse(Call<NotificationDocument> call, Response<NotificationDocument> response) {
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
    public void onFailure(Call<NotificationDocument> call, Throwable t) {

    }
}
