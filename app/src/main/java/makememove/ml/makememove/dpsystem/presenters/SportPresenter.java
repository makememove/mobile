package makememove.ml.makememove.dpsystem.presenters;

import android.util.Log;

import makememove.ml.makememove.activities.fragments.UserMainFragment;
import makememove.ml.makememove.dpsystem.documents.SportListDocument;
import makememove.ml.makememove.user.Sport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportPresenter extends Presenter implements Callback<SportListDocument> {
    public SportPresenter(SportListDocument document){
        super();
        this.document = document;
    }


    public void getAllSports(String token){
        Call<SportListDocument> call = api.getAllSports(token);
        call.enqueue(this);
    }

    public void getUserPreferredSports(String token){
        Call<SportListDocument> call = api.getUserPreferredSports(token);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<SportListDocument> call, Response<SportListDocument> response) {
        if(response.isSuccessful()){
            synchronized(response) {
                document.setData(response.body());
            }
        }
        else
            Log.d("onNotSuccessful","NotSuccessful");
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.d("onFailure","failure");
        System.out.printf("Failure occured in getSports() method!");
    }
}
