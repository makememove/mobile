package makememove.ml.makememove.dpsystem.presenters;

import android.util.Log;

import java.io.IOException;

import makememove.ml.makememove.dpsystem.documents.RankDocument;
import makememove.ml.makememove.dpsystem.documents.TeamDocument;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamPresenter extends Presenter implements Callback<TeamDocument> {
    public TeamPresenter(){super();}
    public TeamPresenter(TeamDocument document){
        super();
        this.document = document;
    }

    public void getTeams(String token, int sportID){
        Call call = api.getTeams(token,sportID);
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<TeamDocument> call, Response<TeamDocument> response) {
        if(response.isSuccessful()){
            document.setData(response.body());
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
