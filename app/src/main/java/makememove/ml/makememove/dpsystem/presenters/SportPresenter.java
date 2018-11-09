package makememove.ml.makememove.dpsystem.presenters;

import makememove.ml.makememove.dpsystem.documents.SportListDocument;
import retrofit2.Call;
import retrofit2.Callback;

public class SportPresenter extends Presenter {
    public SportPresenter(){
        super();
    }

    public void getAllSports(String token, retrofit2.Callback callback){
        Call<SportListDocument> call = api.getAllSports(token);
        call.enqueue(callback);
    }

    public void getUserPreferredSports(String token, Callback callback){
        Call<SportListDocument> call = api.getUserPreferredSports(token);
        call.enqueue(callback);
    }
}
