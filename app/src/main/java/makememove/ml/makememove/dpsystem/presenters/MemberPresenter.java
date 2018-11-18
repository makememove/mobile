package makememove.ml.makememove.dpsystem.presenters;

import android.util.Log;

import java.io.IOException;

import makememove.ml.makememove.dpsystem.documents.FriendDocument;
import makememove.ml.makememove.dpsystem.documents.MemberDocument;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberPresenter extends Presenter implements Callback<MemberDocument> {
    public MemberPresenter(){
        super();
    }

    public MemberPresenter(MemberDocument doc){
        super();
        this.document = doc;
    }

    public void setDocument(FriendDocument doc){
        this.document = doc;
    }

    public void getTeamMembers(String token, int teamId){
        Call call = api.getTeamMembers(token, teamId);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<MemberDocument> call, Response<MemberDocument> response) {
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
