package makememove.ml.makememove.dpsystem.presenters;

import makememove.ml.makememove.activities.fragments.UserMainFragment;
import makememove.ml.makememove.dpsystem.documents.SportListDocument;
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
            SportListDocument sportok = response.body();
            document.setData(sportok);
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        System.out.printf("Failure occured in getSports() method!");
    }
}
