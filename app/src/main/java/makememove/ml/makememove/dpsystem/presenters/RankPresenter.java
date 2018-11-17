package makememove.ml.makememove.dpsystem.presenters;

import android.util.Log;

import java.io.IOException;

import makememove.ml.makememove.dpsystem.documents.Document;
import makememove.ml.makememove.dpsystem.documents.EventListDocument;
import makememove.ml.makememove.dpsystem.documents.RankDocument;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankPresenter extends Presenter implements Callback<RankDocument> {
    public RankPresenter(){super();}
    public RankPresenter(RankDocument document){
        super();
        this.document = document;
    }

    public void getRankList(String token, int sportID,int limit){
        Call call = api.getRankList(token,sportID, limit);
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<RankDocument> call, Response<RankDocument> response) {
        if(response.isSuccessful()){
            document.setData(response.body());
            Log.d("Success","Success");
        }
        else{
            try {
                Log.d("Error","Error "+response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }
}
